How to configure authentication
===

ACEM can use the following authentications schemes:

* CAS (the default)
* Manual (the login/passwords are retrieved from the ACEM database)

Configuring CAS
--

You need to edit ACEM-web-jsf-servlet/src/main/resources/properties/config.properties with, for example :

    cas.service.host=http://www.my-domain.edu/ACEM

where "http://www.my-domain.edu/ACEM" is the address that the users can use to reach ACEM.

You need to specify your CAS server with, for example :

    cas.server.host=http://cas.my-domain.edu

Switching from CAS to Manual
--

Modifying the configuration to authenticate the users using the database requires the following steps:

1. Edit ACEM-web-jsf-servlet/src/main/resources/properties/config.properties like this:

    security.mode=manual

    authentication.provider=daoAuthProvider

2. Edit ACEM-web-jsf-servlet/src/main/resources/properties/applicationContext-security.xml to be like:

    &lt;import resource="security/applicationContext-security-manual.xml" /&gt;

    &lt;!-- 
	 &lt;import resource="security/applicationContext-security-cas.xml" /&gt;
    --&gt;
