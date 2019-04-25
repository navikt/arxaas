
# HTTPS Configuration

To enable https in ARXaaS, please apply one of the following procedures
The values following the '='s are for guidance,
please change them to match user specific settings

## Option 1(recommended): Pass keystore with certificate(s) from host machine to docker container upon running docker image
#### NB: If your command application appears to stall after running this command, make sure to look for prompts from Docker concerning credential input)
#### NB2: The -d option is not required. It runs the docker container as a daemon process, allowing for it to run in the background without occupying the shell.
```bash
docker run -d -v <absolute source path to keystore on host machine>/<keystore file name>:<relative path from root directory in docker container to destination>/<keystore file name> -p 8080:8080 <docker image name> --server.ssl.key-store-type=<keystore type> --server.ssl.key-store=classpath:<keystore file name> --server.ssl.key-store-password=<keystore password> --server.ssl.key-alias=<name/alias of certificate in keystore>
```

### Working example 
```bash
docker run -d -v C:/Users/vijo/git/ARXaaS/arxaas-keystore.p12:/app/arxaas-keystore.p12 -p 8080:8080 arxaas/aaas:test --server.ssl.key-store-type=PKCS12 --server.ssl.key-store=classpath:arxaas-keystore.p12 --server.ssl.key-store-password=password --server.ssl.key-alias=arxaas-https
```
## Option 2 and 3 require a keystore file containing a certificate inside the Spring project's src/main/resources folder. Option 4 and 5  have the same requisites as option 2 and 3, plus compilation to jar / Docker image. Following is a guide for that, can be skipped if said requisite is satisfied.
1. Create your keystore and certificate, you will be prompted to set a password for the keystore:
```bash
keytool -genkeypair -keystore <keystore file name> -storetype PKCS12 -alias <name of new certificate> -keyalg RSA -keysize 2048 -validity 360
```
2. OPTIONAL: Add more certificates to keystore
```bash
keytool -genkey -alias <name of new certificate> -keystore <path to keystore> -storetype PKCS12 -keyalg RSA -storepass <keystore password> -validity 730 -keysize 2048
```
3. IMPORTANT: Place your keystore file in the spring project's /src/main/resources folder, this is necessary for spring to be able to find the certificate on the classpath with our settings
4. Link the keystore to the server's SSL settings by appending its name to the classpath in server.ssl.key-store
5. Set server.ssl.key-store-type to match your keystore file's format. PKCS12 is the recommended industry standard
6. Set server.ssl.key-store-password to your key-store password
7. Set server.ssl.key-alias to the name of the desired certificate in your key-store

#### OPTIONAL: Verify that your certificates are correctly stored inside keystore
```bash
keytool -list -v -keystore <keystore file>
```

## Option 2: Run server with hard coded SSL configuration. Configuration should look like the following, change values after '='s to match user specific settings
#### 1. Uncomment the following settings from Spring server application.properties in /src/main/resources
```bash
server.ssl.key-store=classpath:<keystore file name>
server.ssl.key-store-type=<keystore type>
server.ssl.key-store-password=<keystore password>
server.ssl.key-alias=<name/alias of certificate in keystore>
```
#### 2. The Spring server can now be run with HTTPS support enabled
##### Docker: 
Build image
```bash
docker build -t <image name> <path to Dockerfile>
```
Run container
```bash
docker run -p 8080:8080 <image name>
```
##### Jar: 
Compile the project
```bash
mvn clean install 
```
Run jar (after compiling it should be located inside the project's target folder) 
```bash
java -jar <path to jar>
```

## Option 3: Run server with dynamic HTTPS configuration from Spring project (keystore file inside Spring project's src/main/resources folder required)
```bash
mvn spring-boot:run -Dserver.ssl.key-store-type=<keystore type> -Dserver.ssl.key-store=classpath:<keystore file name> -Dserver.ssl.key-store-password=<keystore password> -Dserver.ssl.key-alias=<name/alias of certificate in keystore>
```

## Option 4: Run server with dynamic HTTPS configuration from jar (keystore file inside Spring project's src/main/resources folder required)
```bash
java -jar aaas-0.1.1-RELEASE.jar --server.ssl.key-store-type=<keystore type> --server.ssl.key-store=classpath:<keystore file name> --server.ssl.key-store-password=<keystore password> --server.ssl.key-alias=<name/alias of certificate in keystore>
```

## Option 5: Run server with dynamic HTTPS configuration from Docker image (keystore file inside Spring project's src/main/resources folder required)
```bash
docker run -p 8080:8080 -d arxaas/aaas --server.ssl.key-store-type=<keystore type> --server.ssl.key-store=classpath:<keystore file name> --server.ssl.key-store-password=<keystore password> --server.ssl.key-alias=<name/alias of certificate in keystore>
```
