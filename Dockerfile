FROM openjdk:17-jdk-slim AS build

# Copy Maven wrapper files
COPY pom.xml mvnw ./
COPY .mvn .mvn

# Ensure the Maven wrapper script is executable
RUN chmod +x ./mvnw

# Resolve dependencies
RUN ./mvnw dependency:resolve

# Copy source code
COPY src src

# Package the application
RUN ./mvnw package -DskipTests

# Create final image
FROM openjdk:17-jdk-slim
WORKDIR /demo
COPY --from=build target/*.jar demo.jar
ENTRYPOINT ["java", "-jar", "demo.jar"]