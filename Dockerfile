FROM openjdk:8-jdk-alpine
RUN mkdir -p /app
WORKDIR /app
COPY ./target/*.jar app.jar
ENTRYPOINT ["java", "-Dserver.port=8181", "-Dspring.datasource.url=jdbc:mysql://user:password@db:3306/db", \
	"-Dspring.kafka.consumer.bootstrap-servers=localhost:9092", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar"]
EXPOSE 8181
