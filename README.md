# IMPORTANTE ....
tener descargado la dll mssql-jdbc_auth-9.2.1.x64.dll desde 
https://learn.microsoft.com/en-us/sql/connect/jdbc/release-notes-for-the-jdbc-driver?view=sql-server-ver16


https://go.microsoft.com/fwlink/?linkid=2155948

copiar la dll en 
C:\Windows\System32 
y en 
C:\Program Files\Java\jdk-22\bin

verificar el firewall de windows en el los  1433 y 1434 
varificar el path de la variable de entorno .. agregar a path general C:\Program Files\Java\jdk-22\bin y tambien -->
nombre de variable = java.library.path 
valor = C:\Program Files\Java\jdk-22\bin

# Mi Maven SQL Server 

This project demonstrates how to connect to a SQL Server database using Java and Maven, and execute a simple query.

## Configuration

Update the `src/main/resources/application.properties` file with your database connection details.

## probar

abrir con  IntelliJ IDEA 2024.1.2 (Community Edition) pararse en App.java y dale Play


## Compiler 
mvn clean package

## Ejecutar
java -jar .\target\my-maven-sqlserver-project-1.0-SNAPSHOT.jar
Servidor escuchando en el puerto 8081

## probar 
curl http://localhost:8081/DameLaHora


## Docker 
## verificar si hace falta # RUN echo "192.168.1.10 DESKTOP-GJNC2BB" >> /etc/hosts

docker build -t "java-maven" .

docker run -d -p 8081:8081 --name java-maven1 java-maven

curl localhost:8081/

curl localhost:8081/DameLaHora
