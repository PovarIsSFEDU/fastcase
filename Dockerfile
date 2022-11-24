FROM eclipse-temurin:17-alpine as builder
ADD . /src
WORKDIR /src
RUN chmod +x mvnw
RUN ./mvnw clean package -am -DskipTests -T 1C

FROM alpine:3.15
LABEL maintainer="Pavel 'P0var' Lukash plukash@webdev-group.ru"
RUN apk --no-cache add openjdk17-jdk
COPY --from=builder /src/target/fastcase-0.0.1.jar app.jar

EXPOSE 3000
ENTRYPOINT ["echo","App builded"]