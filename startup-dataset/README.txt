This folder contains some data to setup ACEM for a demo :

- UEB-default-uploaded-content : set of various illustrations matching the data that gets loaded in the database
when we push the button in "Administration/Database" (this corresponds to the initDatabaseServiceImpl.java class).

IMPORTANT : you have to create a file named "ACEM-web-jsf-servlet/src/main/resources/properties/config.properties"
with at least the entries "tmp.path" and "images.path" which should point to writable folders for your server.
