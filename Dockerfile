FROM openjdk:17
WORKDIR /course-composite-service
COPY ./target/course-composite-service-1.0.0.jar course-composite-service-1.0.0.jar
EXPOSE 8080
CMD ["java", "-jar", "course-composite-service-1.0.0.jar"]
