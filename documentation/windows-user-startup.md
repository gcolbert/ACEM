How to install ACEM on Windows 7
===

We suppose that you want to install ACEM in a "D:\ACEM" folder, but it can be any writable folder.

Java 8 JDK
---

You need the Java 8 JDK on your computer to run ACEM:

1. Download and install Java SE 8 JDK from http://java.oracle.com
2. Follow the installation instructions.

Setup JAVA environment variables
---

The environment variables JAVA_HOME and JRE_HOME must be set for Tomcat 7 to run:

1. Open the Start Menu;
2. Search for "User accounts" (in french "Comptes d'utilisateur");
3. Click on the left column on "Change my environment variables";
4. Select "New";
5. Set a variable named "JAVA_HOME" to "C:\Program Files\Java\jdk1.8.0_40" (or the exact version you have);
6. Set a variable named "JRE_HOME" to "C:\Program Files\Java\jre1.8.0.40" (or the exact version you have).
7. Click OK.

Install Tomcat 7
---

1. Download latest Tomcat 7 from http://tomcat.apache.org (choose the binary distribution "64-bit Windows zip");
2. Unzip the file in D:\ACEM, it should create a folder named "apache-tomcat-7.0.59" (or the exact version you have);
3. (Optional) Delete the Tomcat ZIP.

Install ACEM web archive in Tomcat
---

1. Download "ACEM.war" from GitHub;
2. Put the file in D:\ACEM\apache-tomcat-7.0.59\webapps (adjust the path accordingly to your choices).

Install Neo4j 2.1.7
---

1. Download Neo4j Community in ZIP (not EXE) for Windows 64 bit at http://neo4j.com/download/other-releases/
2. Unzip the file in D:\ACEM, it should create a folder named "neo4j-community-2.1.7";
3. (Optional) Delete the Neo4j ZIP.

(Optional) Install default database
---

Optionally, you can initialize the database with data suitable for a demo.

1. Go to https://github.com/gcolbert/ACEM/blob/master/startup-dataset/UEB-init-database-fr/ACEM-neo4j-2.1.7-default-data-folder.zip;
2. Click on the "Raw" button;
3. Save the file in D:\ACEM\neo4j-community-2.1.7;
4. Delete the "D:\ACEM\neo4j-community-2.1.7\data" folder;
5. Unzip the file "ACEM-neo4j-2.1.7-default-data-folder.zip".

If you choose to initialize the database, then it is recommended that you install the images, too.

1. Go to https://github.com/gcolbert/ACEM/blob/master/startup-dataset/UEB-default-uploaded-content/ACEM-data.zip;
2. Click on the "Raw" button;
3. Save the file in D:\
4. Unzip the file "D:\ACEM-data.zip".

If you want to install these images somewhere else, you will need to create a "config.properties" file in D:\ACEM\ACEM-web-jsf-servlet\src\main\resources\properties to override the following values:

    images.path=D:/ACEM-data/uploaded-content/images/
    tmp.path=D:/ACEM-data/upload-tmp/

The folders must be writable by the Tomcat process.

How to run ACEM in Windows 7
===

1. Open the file explorer (Windows key + E)
2. Go to the folder where you installed the Neo4J database, presumably "D:\ACEM\neo4j-community-2.1.7"
3. Go to folder "bin"
4. Double click on "Neo4j", two empty black windows open, one should close automatically after a few seconds and the other one should remain opened (leave this window open until you have finished using ACEM)
5. Go to the folder where you installed Apache Tomcat 7, presumably "D:\ACEM\apache-tomcat-7.0.59"
6. Go to folder "bin"
7. Double click on "startup" and wait until you see "Startup time: XXXX ms" (takes around 40 seconds)
8. Open a web browser (e.g. Firefox) and go to http://localhost:8080/ACEM

It should redirect you to the CAS authentication window. If you prefer manual accounts, please read [Configure authentication](configure-authentication.md).
