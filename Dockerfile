FROM maven:3.9.8-eclipse-temurin-21 AS builder

COPY ./src/ /root/src
COPY ./pom.xml /root/
COPY ./checkstyle.xml /root/
WORKDIR /root
RUN mvn clean package
RUN java -Djarmode=layertools -jar /root/target/backend-0.0.1-SNAPSHOT.jar list
RUN java -Djarmode=layertools -jar /root/target/backend-0.0.1-SNAPSHOT.jar extract
RUN ls -l /root

FROM openjdk:21-jdk-slim

ENV TZ=UTC
ENV DB_IP=host.docker.internal
ENV DB_PORT=5433
ENV DB_USER=postgres
ENV DB_PASSWORD=postgres
ENV DB_DBNAME=lic

COPY --from=builder /root/dependencies/ ./
COPY --from=builder /root/snapshot-dependencies/ ./

RUN sleep 10
COPY --from=builder /root/spring-boot-loader/ ./
COPY --from=builder /root/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher","-XX:+UseContainerSupport -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1 -Xms512m -Xmx512m -XX:+UseG1GC -XX:+UseSerialGC -Xss512k -XX:MaxRAM=72m"]
