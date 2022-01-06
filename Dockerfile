FROM gradle:7.1 as builder
USER root
ENV APP_DIR /app
WORKDIR $APP_DIR
COPY . $APP_DIR
RUN gradle build -x test
USER guest

# -----------------------------------------------------------------------------

FROM openjdk:11-slim-buster
WORKDIR /app
COPY --from=builder /app/console/build/libs/console-*all.jar /app/console.jar
ENTRYPOINT ["java", "-jar", "/app/console.jar"]
