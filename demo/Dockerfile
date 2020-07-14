FROM penjdk:8-jre-alpine-1.0
ADD target/demo-1.0.0.jar  /
ADD target/classes/bpmn/  /bpmn
EXPOSE 7080
ENTRYPOINT ["java", "-Xms64m", "-Xmx128m", "-XX:MetaspaceSize=128m", "-XX:MaxMetaspaceSize=256m", "-jar","-Duser.timezone=GMT+08","-Dspring.profiles.active=ess","demo-1.0.0.jar"]
