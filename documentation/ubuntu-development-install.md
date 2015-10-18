ACEM development with Ubuntu
============================

Install Neo4j
-------------
- Download it at http://www.neo4j.org/download
- Launch Neo4j
- You should be able to access http://localhost:7474

Initialize database (optional)
------------------------------
- If you want to start with an existing ACEM database, you can delete
Neo4J's "data" folder, and unzip the file located at :
[ACEM/startup-dataset/UEB-init-database-fr/ACEM-neo4j-2.1.7-default-data-folder.zip](https://github.com/gcolbert/ACEM/blob/master/startup-dataset/UEB-init-database-fr/ACEM-neo4j-2.1.7-default-data-folder.zip)

Install OpenJDK 7
-----------------
- sudo apt-get install openjdk-7-jdk

Install Maven
---------------

- sudo apt-get install maven

Install Eclipse for Java EE
---------------------------

- Download **Eclipse IDE for Java EE developers** at http://www.eclipse.org/downloads/

- Put the tar.gz inside a folder in your $HOME directory (e.g. in ~/ACEM-project)

- tar -xzf eclipse-jee-kepler-SR1-linux-gtk-x86_64.tar.gz 

Create workspace folder
-----------------------

- mkdir ~/ACEM-project/workspace-ACEM

Download sources from GitHub.com
--------------------------------

- sudo apt-get install git

- cd ~/ACEM-project/workspace-ACEM

- git clone https://github.com/gcolbert/ACEM

It should download sources into ~/ACEM-project/workspace-ACEM/ACEM

Configure Eclipse
-----------------

- Launch Eclipse

- Choose ~/ACEM-project/workspace-ACEM as workspace

- File / Import... / Maven / Existing Maven projects

- Set root directory to: ~/ACEM-project/workspace-ACEM/ACEM

- Click Finish

Create a config.properties file
-------------------------------

You must **create a "config.properties" file** in ~/ACEM-project/workspace-ACEM/ACEM/ACEM-web-jsf-servlet/src/main/resources/properties for the server to start properly.

You may want to adjust, at least, the paths in tmp.path and images.path whose default values are:

     images.path=D:/ACEM-data/uploaded-content/images/
     tmp.path=D:/ACEM-data/upload-tmp/

Build the project using Maven
-----------------------------

* From the shell :

- cd ~/ACEM-project/workspace-ACEM/ACEM

- mvn clean install

Note : you can skip tests with "mvn clean install -DskipTests"

* From Eclipse :

- Right-click on ACEM project and do Run As / Maven install

Run the server using Maven and Jetty
------------------------------------

* From the shell :

- cd ACEM-web-jsf-servlet

- mvn jetty:run

Wait for "[INFO] Started Jetty Server"

- Point your browser to http://localhost:8080

Run the server using Eclipse and Tomcat
---------------------------------------

- Download and install latest Tomcat from http://tomcat.apache.org

- Go to Eclipse, click the Servers tab, and click the link to add a new server

- Choose the directory where you extracted Tomcat's distribution archive

- Once the server is installed, double-click on it.

- In the "Server Locations" panel, choose "Use Tomcat installation (takes control of Tomcat installation)".

- Change the deploy-path from "wtpwebapps" to "webapps".

- In the "Timeouts" panel on the right, increase the "Start (in seconds)" value to 200. Do the same thing for the "Stop (in seconds)" value.

- Save the server configuration.

- Now, right-click on the server and choose "Add and remove".

- Pick the ACEM-web-jsf-servlet project so that it appears in the "Configured" column. Click "Finish".

- **Make sure that Neo4j is running, or the application won't start.**

- Click the start icon of the server.

- In a web browser, go to http://localhost:8080/ACEM
