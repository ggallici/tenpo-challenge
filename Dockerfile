# get base image
FROM amazoncorretto:17

# set app dir
WORKDIR app/

# copy files
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY src ./src

# setup environment
ENV SPRING_PROFILES_ACTIVE=prod

# download dependencies
RUN ./mvnw dependency:resolve

# run app
ENTRYPOINT ./mvnw spring-boot:run
