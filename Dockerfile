FROM adoptopenjdk/openjdk11:ubi
ARG JAR_FILE=target/*.jar
ENV BOT_NAME=sky_group_bot
ENV BOT_TOKEN=5136299461:AAEPTkVJJMVPNsp0gdCxMXlMlbOxcaBhsTY
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dbot.username=${BOT_NAME}", "-Dbot.token=${BOT_TOKEN}", "-jar", "/app.jar"]
