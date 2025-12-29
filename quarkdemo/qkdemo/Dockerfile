FROM eclipse-temurin:21.0.9_10-jre-alpine-3.22

RUN mkdir /opt/app

COPY --chown=185 target/quarkus-app/lib/ /opt/app/lib/
COPY --chown=185 target/quarkus-app/*.jar /opt/app/
COPY --chown=185 target/quarkus-app/app/ /opt/app/app/
COPY --chown=185 target/quarkus-app/quarkus/ /opt/app/quarkus/

ENV APP_HOME=/opt/app
COPY perf.db /opt/app/perf.db

CMD ["java", "-Xms256M", "-Xmx256M","-jar", "/opt/app/quarkus-run.jar"]