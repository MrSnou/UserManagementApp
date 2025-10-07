# ===============================
# Etap 1 — budowanie WAR przez Maven
# ===============================
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Skopiuj pliki Maven
COPY pom.xml .
COPY src ./src

# Zbuduj aplikację (utworzy target/usermanagement.war)
RUN mvn clean package -DskipTests

# ===============================
# Etap 2 — uruchamianie w Tomcat
# ===============================
FROM tomcat:11.0-jdk21
WORKDIR /usr/local/tomcat

# Usuń domyślne aplikacje Tomcata
RUN rm -rf webapps/*

# Skopiuj wygenerowany plik WAR z etapu build
COPY --from=build /app/target/*.war webapps/ROOT.war

# Otwórz port
EXPOSE 8080

# Uruchom Tomcata
CMD ["catalina.sh", "run"]
