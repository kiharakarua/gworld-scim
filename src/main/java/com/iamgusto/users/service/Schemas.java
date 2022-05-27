package com.iamgusto.users.service;

import com.iamgusto.users.data.SchemaRepo;
import com.iamgusto.users.model.Attribute;
import com.iamgusto.users.data.Schema;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class Schemas {

  @Inject
  SchemaRepo schemaRepo;

  public Optional<Schema> get(URI uri) {
    return schemaRepo.findByIdOptional(uri.toString());
  }

  public Page<Schema> get(int start, int count) {
    long total = schemaRepo.findAll().count();
    List<Schema> list = schemaRepo.findAll().range(start, start + count - 1).list();
    return new Page<>(count, total, start, list);
  }
}
