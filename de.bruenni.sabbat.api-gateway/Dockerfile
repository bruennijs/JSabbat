FROM java:8
VOLUME /tmp
ADD build/libs/sabbat.api-gateway.jar /sabbat.api-gateway.jar
RUN bash -c 'touch /sabbat.api-gateway.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/sabbat.api-gateway.jar"]