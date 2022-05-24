package com.iamgusto.users;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iamgusto.users.data.WriteException;
import com.iamgusto.users.model.base.serviceprovider.ResourceType;
import com.iamgusto.users.service.Provisioner;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;

@QuarkusMain
public class ScimApplication implements QuarkusApplication {

  @Inject
  Provisioner provisioner;
  @Inject
  ObjectMapper objectMapper;
  private final TypeReference<List<ResourceType>> ResourceTypeRef = new TypeReference<>() {
  };

  @Override
  public int run(String... args) {
    Collection<ResourceType> resources;
    if (args.length == 0) {
      resources = Collections.emptyList();
    } else {
      try (InputStream resourceAsStream = new FileInputStream(args[0])) {
        resources = (objectMapper.readValue(resourceAsStream, ResourceTypeRef));
      } catch (IOException e) {
        e.printStackTrace();
        return -1;
      }
    }
    try {
      provisioner.provision(resources);
    } catch (WriteException e) {
      e.printStackTrace();
      return -1;
    }
    System.out.println("resources = " + resources);
    Quarkus.waitForExit();
    return 0;
  }
}
