How to configure authentication
===

ACEM can use the following Spring profiles to configure authentication scheme :

* auth-manual : where the login/passwords are retrieved from the ACEM database (the default)
* auth-cas : where the authentication is done by a CAS server

Switching from Manual to CAS
--

You just have to pass the system property "spring.profiles.active" the value "auth-cas".
Deleting the system property, or not specifying it, will use the default value, which
is defined in "web.xml" by setting the correct value to property "spring.profiles.default".

Configuring CAS
--

You need to edit ACEM-web-jsf-servlet/src/main/resources/properties/config.properties with, for example :

    cas.service.host=http://www.my-domain.edu/ACEM

where "http://www.my-domain.edu/ACEM" is the address that the users can use to reach ACEM.

You need to specify your CAS server with, for example :

    cas.server.host=http://cas.my-domain.edu

