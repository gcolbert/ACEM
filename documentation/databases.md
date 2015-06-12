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

Relational database
--
####Building
No configuration is required, simply use:

    mvn install

which is equivalent to:

    mvn install -Dspring.profiles.active=relational-database

####Running with Tomcat
No configuration is required.

If you don't give the "spring.profiles.active" environment variable, Tomcat will search in "ACEM-web-jsf-servlet/src/main/webapp/WEB-INF/web.xml" and use the parameter `spring.profiles.default`. The default values are "auth-manual,relational-database", which explains why no configuration is required for a relational database.

Graph database
--
####Building
To successfully build the project for use with the graph database, you **must** define the property "spring.profiles.active" and pass it the "graph-database" value, which you do with:

    mvn install -Dspring.profiles.active=graph-database

####Changing the default database for build process

If you want to set the graph database as default, then you have to modify two files:

1. ACEM-dal-common/src/main/resources/META-INF/ACEM-dal-common-context.xml
2. ACEM-dal-common/src/test/resources/dal-common-test-context.xml

In each file, remove "default" from `<beans profile="relational-database, default">` and add it to the second conditional import, that is, you should have `<beans profile="relational-database">` and `<beans profile="graph-database, default">`.

####Running with Tomcat
Prior to running Tomcat, you must edit "ACEM-web-jsf-servlet/src/main/webapp/WEB-INF/web.xml" and comment out the filter "OpenEntityManagerInViewFilter", e.g. :

	<!--
	<filter>
		<filter-name>OpenEntityManagerInViewFilter</filter-name>
		<filter-class>org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter</filter-class>
	</filter>
	<filter-mapping>
		<filter-name>OpenEntityManagerInViewFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
	-->

Leaving this filter enabled will result in the following exception when Tomcat starts up:

    No qualifying bean of type [javax.persistence.EntityManagerFactory] is defined

Once the "OpenEntityManagerInViewFilter" is commented out, you have to set Tomcat's environment variable "spring.profiles.active" to contain the value "graph-database". This tells the application server to import the Data Access Layer configuration from the module "ACEM-dal-graph-database" and not from "ACEM-dal-relational-database".
 
####Changing the default database for run time

If you want to set the graph database as default for Tomcat, you have to edit "ACEM-web-jsf-servlet/src/main/webapp/WEB-INF/web.xml" and modify the parameter named "spring.profiles.default" to contain "graph-database" instead of "relational-database".
