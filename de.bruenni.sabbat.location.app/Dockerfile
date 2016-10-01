FROM isuper/java-oracle:jre_8
VOLUME /tmp
ADD build/libs/sabbat.location.app.jar /app.jar
RUN bash -c 'touch /sabbat.api-gateway.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]