FROM gradle:jdk17-alpine as builder
WORKDIR /app
ADD . .
RUN ./gradlew build --no-daemon

FROM eclipse-temurin:17.0.6_10-jre-alpine
COPY --from=builder /app/build/libs/backend-0.0.1-SNAPSHOT.jar .
ENTRYPOINT [ "java", "-jar", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:9000", "/backend-0.0.1-SNAPSHOT.jar" ]