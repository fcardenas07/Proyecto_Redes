# Usa una imagen base de OpenJDK 11 con Alpine
FROM openjdk:18-alpine

# Establece el directorio de trabajo en /app
WORKDIR /app

# Copia el JAR compilado al contenedor
COPY ProyectoRedeswDB-1.0-SNAPSHOT.jar /app

EXPOSE 5050

# Ejecuta la aplicación
CMD ["java", "-jar", "ProyectoRedeswDB-1.0-SNAPSHOT.jar"]
