FROM openjdk:21
WORKDIR /tmp
EXPOSE 8081
COPY target/pindan_user.jar app.jar
ENTRYPOINT exec java -Duser.timezone=Asia/Shanghai -Dspring.profiles.active=druid -jar app.jar