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

### Genearte the jar or the maven site
If you need to generate the jar you can do it with maven
```
   mvn clean intall
``` 
For the maven site
```
   mvn clean intall site
``` 
