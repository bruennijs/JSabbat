#/bin/bash
java $(echo $JVM_XMX) -Djava.security.egd=file:/dev/./urandom -jar /app.jar