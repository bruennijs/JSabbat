FROM isuper/java-oracle:jre_8
VOLUME /tmp
ADD build/libs/sabbat.location.app.jar /app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-D$(echo $JVM_XMX)", "-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]