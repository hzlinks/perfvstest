FROM eclipse-temurin:21.0.9_10-jre-alpine-3.22
RUN mkdir /opt/app
COPY target/sdemo-0.0.1-SNAPSHOT.jar /opt/app/japp.jar
COPY perf.db /root/perf.db
CMD ["java", "-Xms256M", "-Xmx256M","-jar", "/opt/app/japp.jar"]