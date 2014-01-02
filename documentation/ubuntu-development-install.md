UBUNTU CONFIGURATION
====================

INSTALL OPENJDK-7-JDK
---------------------
- sudo apt-get install openjdk-7-jdk

INSTALL MAVEN 2
---------------------

- sudo apt-get install maven2

INSTALL ECLIPSE FOR JAVA EE
---------------------

- Download Eclipse IDE for Java EE developers at http://www.eclipse.org/downloads/

- Put the tar.gz inside a folder in your $HOME directory (e.g. in ~/ACEM-project)

- tar -xzf eclipse-jee-kepler-SR1-linux-gtk-x86_64.tar.gz 

- there's currently a bug in Ubuntu not displaying Eclipse menu.
Workaround : create a file "eclipse-with-menus.sh" inside the "eclipse" folder.

        #!/bin/bash
        env UBUNTU_MENUPROXY=0 ~/ACEM-project/eclipse/eclipse

where ~/ACEM-project/eclipse/eclipse is the path to launch the eclipse executable.

- chmod +x ~/ACEM-project/eclipse/eclipse-with-menus.sh

CREATE WORKSPACE DIRECTORY FOR THE ACEM PROJECT
---------------------

- mkdir ~/ACEM-project/workspace-ACEM

DOWNLOAD SOURCES FROM GITHUB
---------------------

- sudo apt-get install git

- cd ~/ACEM-project/workspace-ACEM

- git clone https://github.com/gcolbert/ACEM

It should download sources into ~/ACEM-project/workspace-ACEM/ACEM

- Launch Eclipse with ~/ACEM-project/eclipse/eclipse-with-menus.sh

- Choose ~/ACEM-project/workspace-ACEM as workspace

- File / Import... / Maven / Existing Maven projects

- Set root directory to: ~/ACEM-project/workspace-ACEM/ACEM

- Click Finish

BUILDING THE PROJECT FROM THE COMMAND LINE
---------------------

- cd ~/ACEM-project/workspace-ACEM/ACEM

- mvn clean install

Note : you can skip tests with "mvn clean install -DskipTests"

RUNNING THE SERVER
---------------------

- cd ACEM-web-jsf-servlet

- mvn jetty:run

