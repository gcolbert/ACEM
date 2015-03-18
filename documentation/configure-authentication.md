How to configure authentication
===

ACEM can use the following authentications schemes:

* CAS (the default)
* Manual (the login/passwords are retrieved from the ACEM database)

Switching from CAS to Manual
--

Modifying the default configuration to authenticate the users using the data access layer requires the following steps:

1. Edit ACEM-web-jsf-servlet/src/main/resources/properties/config.properties like this:

    security.mode=manual
    authentication.provider=daoAuthProvider
2. Edit ACEM-web-jsf-servlet/src/main/resources/properties/applicationContext-security.xml to be like:

    <import resource="security/applicationContext-security-manual.xml" />

    <!-- 
	 <import resource="security/applicationContext-security-cas.xml" />
    -->
