## WHAT
This is a CLI tool that wraps 
[amazon-s3-encryption-client-java](https://github.com/aws/amazon-s3-encryption-client-java).  
With this CLI, you can use your local public/private keys for client-side encryption to put/get objects to/from your Amazon S3 bucket.  

## PREREQUISITES
- mvn 
- Java 8 or later
- RSA Public key and Private Key in PEM format

You can generate an RSA key pair using `openssl` with the following commands:
```
$ openssl genpkey -algorithm RSA -out private_key.pem
$ openssl rsa -pubout -in private_key.pem -out public_key.pem
```

## INSTALLATION AND EXECUTION
You can download the JAR file from the Releases section and run it directly. For instructions on how to use the JAR file, please refer to the release notes.  

If you have downloaded the source code and want to run,  
to print the usage:  
```bash
$ mvn install 
$ mvn exec:java -DskipTests -Dexec.args="-h"
```

To upload:  
```bash
$ mvn exec:java -DskipTests \  
  -Dexec.args="--upload \  
  --bucket-name your-s3-bucket-name \  
  --object-key hello.txt \  
  --local-file-path ./hello.txt  \  
  -p ./public_key.pem -k ./private_key.pem"
```
Note: Private key is not necessary for upload, and Public key is not necessary for download.  

To run tests,  
1. Duplicate the .env.test.template files.  
2. Rename the duplicates to .env.test respectively.  
3. Populate the values.  
```bash
$ export $(cat .env.test | xargs -L 1) && mvn test
```

## DEVELOPMENT  
Install the [Language Support for Java(TM) by Red Hat](https://marketplace.visualstudio.com/items?itemName=redhat.java) extension for VSCode.  
Afterwards, open this project as a workspace from the file named `amazon-s3-encryption-client-java-example` located in the `.vscode` directory.  
Once done, your Java code will be automatically formatted upon saving.  
