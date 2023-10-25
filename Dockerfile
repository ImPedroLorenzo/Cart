FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY target/cart-0.1.jar /app/cart.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "cart.jar"]
