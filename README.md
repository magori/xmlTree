[![Coverage Status](https://coveralls.io/repos/github/magori/xmlTree/badge.svg?branch=master)](https://coveralls.io/github/magori/xmlTree?branch=master)
[![Build Status](https://travis-ci.org/magori/xmlTree.svg?branch=master)](https://travis-ci.org/magori/xmlTree)
[![BCH compliance](https://bettercodehub.com/edge/badge/magori/xmlTree?branch=master)](https://bettercodehub.com/magori/xmlTree)
[![Known Vulnerabilities](https://snyk.io/test/github/magori/xmlTree/badge.svg)](https://snyk.io/test/github/magori/xmlTree)
## XMLEditor

XMLEditor is an app that allows you to open XML files and to edit them. The edit action is currently limited. You can only change the text of an XML element.

XMLEditor has been written in Java. It relies on Maven to handle the dependencies. 


### Presentation of the repository

If you are a developer, you can start by downloading the repository and by opening it an IDE that handles Java and Maven.

In *example*, you will find an executable jar file **XMLEditor.jar**. It also contains two XML files that can be used to discover the software.
The other files and folders are part of the source code.

### Run application

```
    java -jar example/XMLEditor.jar
``` 

### Generate the jar or the maven site
If you need to generate the jar you can do it with maven
```
   mvn clean install or by the plugin jfx -> mvn jfx:native
``` 
For the maven site
```
   mvn clean install site
``` 

To launch the h2 web browser
```
   mvn exec:java@h2
   
   URL JDBC: jdbc:h2:dbh2
   Nom d'utilisateur: sa
   Mot de passe:   //vide(aucun mot de passe)
``` 
