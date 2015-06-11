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

Building the project for the relational database
--
No configuration is required, simply use:

    mvn install

which is equivalent to:

    mvn install -Dspring.profiles.active=relational-database

Building the project for the graph database
--
To successfully build the project for use with the graph database, you **must** define the property "spring.profiles.active" and pass it the "graph-database" value, which you do with:

    mvn install -Dspring.profiles.active=graph-database

###Changing the default database for build process

If you want to set the graph database as default, then you have to modify two files:

1. ACEM-dal-common/src/main/resources/META-INF/ACEM-dal-common-context.xml
2. ACEM-dal-common/src/test/resources/META-INF/dal-common-test-context.xml

In each file, remove "default" from `<beans profile="relational-database, default">` and add it to the second conditional import, that is, you should have `<beans profile="relational-database">` and `<beans profile="graph-database, default">`.

Running the project with Tomcat for the relational database
--
No configuration is required.

If you don't give the "spring.profiles.active" environment variable, Tomcat will read the value of "ACEM-web-jsf-servlet/src/main/webapp/WEB-INF/web.xml" content to see if it can find the parameter `spring.profiles.default`. The default values are "auth-manual,relational-database".

Running the project with Tomcat for the graph database
--
You must edit "ACEM-web-jsf-servlet/src/main/webapp/WEB-INF/web.xml" and put the filter "OpenEntityManagerInViewFilter" inside comments, e.g. :

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

