# Usa una imagen de OpenJDK 22 como base
FROM openjdk:22-jdk-slim

# Copia el archivo DLL necesario al contenedor
# COPY lib/mssql-jdbc_auth-9.2.1.x64.dll /usr/lib/jvm/java-22-openjdk-amd64/lib

# Instala Maven
RUN apt-get update && apt-get install -y maven nano curl iputils-ping

# Establece el directorio de trabajo en /app
WORKDIR /app

# Copia el archivo pom.xml y el código fuente
COPY src ./src
COPY pom.xml .

# Empaqueta la aplicación
RUN mvn package

# Expone el puerto 8081
EXPOSE 8081


#  Agrega la entrada al archivo hosts
# RUN echo "192.168.1.10 DESKTOP-GJNC2BB" >> /etc/hosts


# Ejecuta la aplicación
CMD ["java", "-jar", "target/my-maven-sqlserver-project-1.0-SNAPSHOT.jar"]
