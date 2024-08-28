FROM maven:3.6.3-jdk-11

WORKDIR /usr/retro_backend

COPY pom.xml ./

RUN mvn verify --fail-never

COPY ./ ./

CMD [ "mvn", "spring-boot:run" ]