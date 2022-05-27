package com.iamgusto.users.data;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SchemaRepo implements PanacheRepositoryBase<Schema, String> {

}
