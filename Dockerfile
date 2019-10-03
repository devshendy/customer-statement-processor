FROM maven:3.5.4-jdk-8-alpine

COPY . /app

WORKDIR /app

EXPOSE 9090

RUN ["mvn", "package"]

CMD ["mvn", "spring-boot:run"]
