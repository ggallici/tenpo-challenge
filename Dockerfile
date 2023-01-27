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

# download dependencies & compile
RUN ./mvnw clean package && cp target/*.jar app.jar

# run app
ENTRYPOINT java -jar app.jar
