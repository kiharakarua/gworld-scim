package com.iamgusto.users.data;

import static org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource.traversal;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iamgusto.users.model.BaseScimResource;
import com.iamgusto.users.model.ScimResource.Meta;
import com.iamgusto.users.utils.TimeUtils;
import com.lambdazen.bitsy.BitsyGraph;
import com.lambdazen.bitsy.wrapper.BitsyAutoReloadingGraph;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class GraphRepo {

  @Inject
  ObjectMapper objectMapper;

  @Inject
  @ConfigProperty(name = "bitsy.data.path", defaultValue = "")
  String path;
  private GraphTraversalSource g;
  private final TypeReference<List<Schema>> SchemaTypeRef = new TypeReference<>() {
  };

  @PostConstruct
  public void init() {
    Path p;
    if (path.isBlank()) {
      p = Paths.get(System.getenv("user.home"), "GWorld", "scim", "bitsydb");
    } else {
      p = Paths.get(path);
    }
    p.toFile().mkdirs();
    System.out.println("p.toString() = " + p.toString());
    final BitsyAutoReloadingGraph graph = new BitsyAutoReloadingGraph(new BitsyGraph(p));
    g = traversal().withEmbedded(graph);
  }

  public void storeSchemas(List<Schema> schemas) {
    if (schemas.isEmpty()) {
      return;
    }
    final Vertex schemaV = g.V()
        .has("schema", "_id", "urn:ietf:params:scim:schemas:core:2.0:Schema")
        .next();

    final Schema schema = updateMetaCreation(schemas.get(0));
    GraphTraversal<Vertex, Vertex> t = g.addV("schema").property(toMap(schema));
    t.addE("schemed").to(schemaV);
    for (int i = 1; i < schemas.size(); i++) {
      Schema sc = updateMetaCreation(schemas.get(i));
      GraphTraversal<Vertex, Vertex> t2 = t.addV("schema").property(toMap(sc));
      t2.addE("schemed").to(schemaV);
    }
    t.iterate();
  }

  public void createSchema(Schema schema) {
    final Vertex schemaV = g.V()
        .has("schema", "_id", "urn:ietf:params:scim:schemas:core:2.0:Schema")
        .next();

    schema = updateMetaCreation(schema);
    GraphTraversal<Vertex, Vertex> t = g.addV("schema").property(toMap(schema));
    t.addE("schemed").to(schemaV);
    t.iterate();
  }

  public void storeServiceSchemas() throws WriteException {
    try (InputStream resourceAsStream = Thread.currentThread().getContextClassLoader()
        .getResourceAsStream("service_provider_schemas.json");) {
      List<Schema> schemas = objectMapper.readValue(resourceAsStream, SchemaTypeRef);
      if (schemas.isEmpty()) {
        return;
      }
      Schema schema = schemas.get(0);
      GraphTraversal<Vertex, Vertex> property = g.addV("schema").property(toMap(schema));
      for (int i = 1; i < schemas.size(); i++) {
        Schema sc = schemas.get(i);
        property.addV("schema").property(toMap(sc));
      }
      property.iterate();
    } catch (IOException e) {
      throw new WriteException(e);
    }
  }

  private Map<Object, Object> toMap(BaseScimResource schema) {
    Map<Object, Object> map = new HashMap<>(
        objectMapper.convertValue(schema, new TypeReference<Map<String, Object>>() {
        }));
    map.replaceAll((s, o) -> {
      if (o == null) {
        return "";
      }
      return o;
    });
    return map;
  }

  public void markAsInit() {
    g.addV("app-config").property("init", true).iterate();
  }

  public boolean isInit() {
    return g.V().hasLabel("app-config").<Boolean>values("init").next();
  }

  public void storeResourceTypes(Set<ResourceType> resourceTypes) {
    if (resourceTypes.isEmpty()) {
      return;
    }
    final Vertex schemaV = g.V()
        .has("schema", "_id", "urn:ietf:params:scim:schemas:core:2.0:ResourceType").next();

    Iterator<ResourceType> iterator = resourceTypes.iterator();
    final ResourceType resourceType = updateMetaCreation(iterator.next());
    GraphTraversal<Vertex, Vertex> t = g.addV("schema").property(toMap(resourceType));
    t.addE("schemed").to(schemaV);
    while (iterator.hasNext()) {
      ResourceType sc = updateMetaCreation(iterator.next());
      GraphTraversal<Vertex, Vertex> t2 = t.addV("schema").property(toMap(sc));
      t2.addE("schemed").to(schemaV);
    }
    t.iterate();
  }

  private <T extends BaseScimResource> T updateMetaCreation(T resourceType) {
    Meta meta;
    if ((meta = resourceType.getMeta()) == null) {
      meta = new Meta("Schema", "/v2/Schemas/" + resourceType.getId());
    }
    meta.setCreated(TimeUtils.now());
    meta.setLastModifiedAt(TimeUtils.now());
    resourceType.setMeta(meta);
    return resourceType;
  }
}
