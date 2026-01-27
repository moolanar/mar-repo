FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY . .
RUN javac *.java

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/*.class .
COPY --from=build /app/*.html .
CMD ["java",  "PureJavaServer"]
