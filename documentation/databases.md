Databases
==

ACEM can use two different kinds of databases :

- relational (e.g. MySQL), which corresponds to the "relational-database" Spring profile;
- graph (Neo4j), which corresponds to the "graph-database" Spring profile.

Relational database is the default.

Which database is the best
--
ACEM was initially developed with Spring Data Neo4j. Spring Data Neo4j uses one annotation (`@RelatedTo`) to do the object-node mapping, whereas object-relational mapping with Spring Data JPA requires four different annotations (`@OneToOne`, `@OneToMany`, `@ManyToOne`, `@ManyToMany`). Spring Data Neo4j allows to write repositories using Cypher (Neo4j request language), which is really much simpler than JPQL, and as such, far less error-prone.

Spring Data JPA is a Java standard and as such, could be preferred by your organization's administrators. This is the reason why we added support for relational databases.

Build process
--
The build process is independent from the database you want to use at run time. Maven will always compile both modules "ACEM-dal-relational-database" and "ACEM-dal-graph-database", and run all the unit tests against these two modules.

However, we had to choose a default database for running the tests of the upper layers modules (ACEM-web-jsf-servlet and ACEM-domain-services), and we chose the "ACEM-dal-relational-database" module. As such, using the command:

    mvn install

is equivalent to:

    mvn install -Dspring.profiles.active=relational-database

If you want to run the tests of the upper layers modules against the graph database, use:

    mvn install -Dspring.profiles.active=graph-database

As stated above, the Spring profile chosen at build time has **no impact** on the fact that you will be able to run ACEM with the database of your choice.

####Changing the default database for build process

If you want to run the tests of the upper layers (ACEM-web-jsf-servlet and ACEM-domain-services) with the graph database permanently without specifying the "spring.profiles.active" environment variable, you can do so by modifying two files:

1. ACEM-dal-common/src/main/resources/META-INF/ACEM-dal-common-context.xml
2. ACEM-dal-common/src/test/resources/dal-common-test-context.xml

In each file, remove "default" from `<beans profile="relational-database, default">` and add it to the second conditional import, that is, you should have `<beans profile="relational-database">` and `<beans profile="graph-database, default">`.

Running the server with a relational database
--
Edit `ACEM-web-jsf-servlet/src/main/resources/properties/config.properties` with your database and set the variables according to your configuration. You can use `ACEM-web-jsf-servlet/src/main/resources/properties/default.properties` as a model.

If you don't set the "spring.profiles.active" environment variable, Tomcat will search in "ACEM-web-jsf-servlet/src/main/webapp/WEB-INF/web.xml" and use the parameter `spring.profiles.default` to know what database he should use. The default profiles are "auth-manual,relational-database", so you don't need to specify the "relational-database" profile.

Running the server with a graph database
--
Edit `ACEM-web-jsf-servlet/src/main/resources/properties/config.properties` with your database and set the variables according to your configuration. You can use `ACEM-web-jsf-servlet/src/main/resources/properties/default.properties` as a model.

If you want to set the graph database as default, you have to set Tomcat's environment variable "spring.profiles.active" to contain the value "graph-database". This tells the application server to import the Data Access Layer configuration from the module "ACEM-dal-graph-database" and not from "ACEM-dal-relational-database".

####Changing the default database for run time
If you want to set the graph database as default for Tomcat, you have to edit "ACEM-web-jsf-servlet/src/main/webapp/WEB-INF/web.xml" and modify the parameter named "spring.profiles.default": it should contain "graph-database" instead of "relational-database".
