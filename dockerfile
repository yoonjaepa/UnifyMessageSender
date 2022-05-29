FROM openjdk:8-alpine

USER root

ENV JAVA_OPTS="-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1 -XshowSettings:vm"
ENV JAVA_OPTS="${JAVA_OPTS} -XX:+UseG1GC -XX:+UnlockDiagnosticVMOptions -XX:+G1SummarizeConcMark -XX:InitiatingHeapOccupancyPercent=35 -XX:G1ConcRefinementThreads=20"
ENV JAVA_OPTS="${JAVA_OPTS} -Djasypt.encryptor.password=rRhe8rkqShOeK1uywsTsem7m5kWEpPHq -Dspring.profiles.active=local"

ENV sourceFile target/*.jar
ENV targetFile app.jar

ADD $sourceFile $targetFile
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom  -jar $targetFile"]
