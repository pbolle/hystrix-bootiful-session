FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD sessionApp.jar sessionApp.jar
RUN sh -c 'touch /sessionApp.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Xdebug","-Xrunjdwp:server=y,transport=dt_socket,address=4000,suspend=n","-jar","/sessionApp.jar"]