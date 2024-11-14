FROM openjdk:17 AS build

# Maven'ı ve proje dosyalarını kopyala
COPY pom.xml mvnw ./
RUN chmod +x mvnw
COPY .mvn .mvn

# Bağımlılıkları çöz
RUN ./mvnw dependency:resolve

# Kaynak kodunu kopyala ve paketi oluştur
COPY src src
RUN ./mvnw package -DskipTests  # Test aşamasını atla

FROM openjdk:17
WORKDIR /akyabutik
COPY --from=build target/*.jar akyabutik.jar
ENTRYPOINT ["java", "-jar", "akyabutik.jar"]