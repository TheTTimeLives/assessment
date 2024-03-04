# build
FROM openjdk:17-slim as build
WORKDIR /app
COPY . .
RUN ./gradlew build

# package
FROM openjdk:17-slim
COPY --from=build /app/build/libs/programming-exercise-0.0.1-SNAPSHOT.jar /programming-exercise-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/programming-exercise-0.0.1-SNAPSHOT.jar"]
