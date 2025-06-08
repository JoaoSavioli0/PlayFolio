# Usa imagem oficial do Java 17 com Alpine (leve e boa pro Render)
FROM openjdk:17-jdk-alpine

# Define a pasta de trabalho
WORKDIR /app

# Copia o jar gerado para dentro do container
COPY target/playfolio-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 5000

# Comando que será executado no container
ENTRYPOINT ["java", "-jar", "app.jar"]