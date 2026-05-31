# --- ETAPA 1: Compilar o código Java com Maven ---
FROM maven:3.8.8-eclipse-temurin-17 AS builder

WORKDIR /app

# Copia o pom.xml e a pasta de código para dentro do container
COPY pom.xml .
COPY src ./src

# Executa o comando para buildar o projeto (gera o ROOT.war)
RUN mvn clean package -DskipTests

# --- ETAPA 2: Subir o Tomcat com o arquivo gerado ---
FROM tomcat:10.1-jdk17-corretto

# Limpa a pasta padrão do Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Copia o ROOT.war gerado lá na Etapa 1 direto para a pasta do Tomcat
COPY --from=builder /app/target/ROOT.war /usr/local/tomcat/webapps/

EXPOSE 8080

CMD ["catalina.sh", "run"]