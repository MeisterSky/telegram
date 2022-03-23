FROM azul/zulu-openjdk:ubi
ARG JAR_FILE=target/*.jar
ENV BOT_NAME=sky_group_bot
ENV BOT_TOKEN=5136299461:AAEPTkVJJMVPNsp0gdCxMXlMlbOxcaBhsTY
ENV BOT_DB_USERNAME=telegram_bot_db_user
ENV BOT_DB_PASSWORD=telegram_bot_db_password
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.datasource.password=${BOT_DB_PASSWORD}", "-Dbot.username=${BOT_NAME}", "-Dbot.token=${BOT_TOKEN}", "-Dspring.datasource.username=${BOT_DB_USERNAME}", "-jar", "app.jar"]
