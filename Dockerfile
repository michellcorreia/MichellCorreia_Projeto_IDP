FROM openjdk
WORKDIR /app
COPY /target/Quotation-Management-0.0.1-SNAPSHOT.jar /app/quotation-management.jar
ENTRYPOINT ["java", "-jar", "quotation-management.jar"]