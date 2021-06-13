FROM adoptopenjdk/openjdk11:ubi
ARG JAR_FILE=target/*.jar
ENV BOT_NAME=Volod878_bot
ENV BOT_TOKEN=1624069870:AAGu-CCKajYF0hway8Q6WtALHbC3YAxUR0g
ENV BOT_DB_USERNAME=vtb_db_user
ENV BOT_DB_PASSWORD=vtb_db_password
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.datasource.password=${BOT_DB_PASSWORD}", "-Dbot.username=${BOT_NAME}", "-Dbot.token=${BOT_TOKEN}", "-Dspring.datasource.username=${BOT_DB_USERNAME}", "-jar", "app.jar"]