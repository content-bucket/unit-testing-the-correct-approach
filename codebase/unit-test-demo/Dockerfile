FROM amazoncorretto:21 AS builder
WORKDIR /app
COPY gradle gradle
COPY build.gradle.kts build.gradle.kts
COPY gradlew gradlew
COPY settings.gradle.kts settings.gradle.kts
COPY spotless.xml spotless.xml
RUN ./gradlew check --dry-run
COPY src src
RUN ./gradlew clean build

FROM amazoncorretto:21
WORKDIR /app
COPY --from=builder /app/build/libs/unit-test-demo-0.0.1.jar app.jar
ENTRYPOINT ["java"]
CMD ["-XX:MaxRAMPercentage=80.0", "-jar", "app.jar"]
