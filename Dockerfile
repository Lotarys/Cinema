FROM gradle:8.7-jdk21 AS builder

WORKDIR /app
COPY build.gradle .
COPY src/ src/

RUN gradle clean build -x test \
    -Dorg.gradle.java.home=/opt/java/openjdk \
    -Dorg.gradle.jvmargs="-Xmx2048m"

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar cinema.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "cinema.jar"]