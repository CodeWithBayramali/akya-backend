FROM openjdk:17 AS build

# Maven'ı ve proje dosyalarını kopyala
COPY pom.xml mvnw ./
RUN chmod +x mvnw
COPY .mvn .mvn

# Bağımlılıkları çöz
RUN ./mvnw dependency:resolve

# Source code copy and Create package
COPY src src
RUN ./mvnw package -DskipTests  # Skip Test

FROM openjdk:17
WORKDIR /akyabutik
COPY --from=build target/*.jar akyabutik.jar
ENTRYPOINT ["java", "-jar", "akyabutik.jar"]