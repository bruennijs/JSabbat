#FROM java:8
FROM isuper/java-oracle:jre_8
VOLUME /tmp
ADD build/libs/sabbat.api-gateway.jar /app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]