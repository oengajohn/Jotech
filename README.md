# Your Project
 In the project generated
  addjust the following configs
     - in `src/test/resources/META-INF/persistence.xml` and `src/main/resources/META-INF/persistence.xml`
          configure your database name, database username and database password
      - in `src/test/resources/arquillian.xml`  change [location of wildfly] to the location of wildfly for testing
      - in package `io.jotech.control.rest.JaxRSActivation.java` change the
         url = "http://localhost:8080/Jotech", to your deployment context path
       
`mvn clean package`
`docker build -t jotech:1.0 .`
`docker run -it --name jotechserver1 --link mysql-container -p 8080:8080 jotech:1.0`
