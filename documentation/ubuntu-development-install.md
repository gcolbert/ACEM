ACEM development with Ubuntu
============================

Install Neo4j
-------------
- Download it at http://www.neo4j.org/download
- Launch Neo4j
- You should be able to access http://localhost:7474

Install OpenJDK 7
-----------------
- sudo apt-get install openjdk-7-jdk

Install Maven 2
---------------

- sudo apt-get install maven2

Install Eclipse for Java EE
---------------------------

- Download Eclipse IDE for Java EE developers at http://www.eclipse.org/downloads/

- Put the tar.gz inside a folder in your $HOME directory (e.g. in ~/ACEM-project)

- tar -xzf eclipse-jee-kepler-SR1-linux-gtk-x86_64.tar.gz 

- there's currently a bug in Ubuntu not displaying Eclipse menu.
Workaround : create a file "eclipse-with-menus.sh" inside the "eclipse" folder.

        #!/bin/bash
        env UBUNTU_MENUPROXY=0 ~/ACEM-project/eclipse/eclipse

where ~/ACEM-project/eclipse/eclipse is the path to launch the eclipse executable.

- chmod +x ~/ACEM-project/eclipse/eclipse-with-menus.sh

Create workspace folder
-----------------------

- mkdir ~/ACEM-project/workspace-ACEM

Download sources from GitHub.com
--------------------------------

- sudo apt-get install git

- cd ~/ACEM-project/workspace-ACEM

- git clone https://github.com/gcolbert/ACEM

It should download sources into ~/ACEM-project/workspace-ACEM/ACEM

Configuring Eclipse
-------------------

- Launch Eclipse with ~/ACEM-project/eclipse/eclipse-with-menus.sh

- Choose ~/ACEM-project/workspace-ACEM as workspace

- File / Import... / Maven / Existing Maven projects

- Set root directory to: ~/ACEM-project/workspace-ACEM/ACEM

- Click Finish

Building the project using Maven
--------------------------------

* From the shell :

- cd ~/ACEM-project/workspace-ACEM/ACEM

- mvn clean install

Note : you can skip tests with "mvn clean install -DskipTests"

* From Eclipse :

- Right-click on ACEM project and do Run As / Maven install

Running the server using Maven and Jetty
----------------------------------------

* From the shell :

- cd ACEM-web-jsf-servlet

- mvn jetty:run

Wait for "[INFO] Started Jetty Server"

- Point your browser to http://localhost:8080

Running the server using Eclipse and Tomcat
-------------------------------------------

- Download and install latest Tomcat from http://tomcat.apache.org

- Go to Eclipse, click the Servers tab, and click the link to add a new server

- Choose the directory where you extracted Tomcat's distribution archive

- Once the server is installed, double-click on it and choose :
"Use Tomcat installation (takes control of Tomcat installation"
in the "Server Locations" panel. Change the deploy-path from "wtpwebapps"
to "webapps". Save the file.

- Now right-click on the server add choose "Add and remove".
Add the ACEM-web-jsf-servlet project to Tomcat.

- Make sure that Neo4j is running or the application won't start. Click the start icon.
