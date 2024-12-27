# Payara micro server
#   DockerHub:      https://hub.docker.com/r/payara/micro
#   Documentation:  https://docs.payara.fish/community/docs/6.2024.12/Overview.html
FROM payara/micro:6.2024.12-jdk21

# PostgreSQL JDBC driver
#   Download URL:   https://jdbc.postgresql.org/download/
ADD /docker/postgresql-42.7.4.jar /opt/payara/libs/postgresql.jar

USER root
COPY target/task-manager.war $DEPLOY_DIR
RUN chmod 644 /opt/payara/libs/postgresql.jar
ENTRYPOINT ["java", "-jar", "/opt/payara/payara-micro.jar", "--addLibs", "/opt/payara/libs/postgresql.jar","--deploy", "/opt/payara/deployments/task-manager.war"]