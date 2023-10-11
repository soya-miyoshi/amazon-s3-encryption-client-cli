## WHAT
This is an example application that runs [amazon-s3-encryption-client-java](https://github.com/aws/amazon-s3-encryption-client-java).  

## PREREQUISITE
- mvn 
- Java 8 or later

## INSTALL AND EXECUTION
```
$ mvn install
$ mvn exec:java -DskipTests -o
```

## DEVELOPMENT  
Install [Language Support for Java(TM) by Red Hat](https://marketplace.visualstudio.com/items?itemName=redhat.java) for your VSCode.  
Your `setting.json` for VSCode should have the following formatter configuration.  
```
"java.format.settings.url": "https://raw.githubusercontent.com/google/styleguide/gh-pages/eclipse-java-google-style.xml",
"java.format.enabled": true,
"[java]": {
    "editor.formatOnSave": true
},
```


