FROM gradle:jdk17 AS builder

WORKDIR /app

COPY build.gradle settings.gradle gradlew ./
COPY gradle/ gradle/

RUN gradle --no-daemon build --stacktrace

COPY src/ src/

RUN gradle --no-daemon bootJar

FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar ./app.jar

EXPOSE 9090

ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "app.jar"]
