FROM openjdk
EXPOSE 8990
EXPOSE 8991
EXPOSE 8992
ADD ./dist/app.jar /srv/app.jar
ADD ./dist/jackson-core-2.8.3.jar /srv/jackson-core-2.8.3.jar
ADD ./dist/jackson-annotations-2.8.3.jar /srv/jackson-annotations-2.8.3.jar
ADD ./dist/jackson-databind-2.8.3.jar /srv/jackson-databind-2.8.3.jar
ADD ./dist/restlet.jar /srv/restlet.jar
ADD ./dist/restlet-json.jar /srv/restlet-json.jar
ADD ./dist/json.jar /srv/json.jar
ADD ./dist/java_websocket.jar /srv/java_websocket.jar
CMD java -cp /srv/java_websocket.jar:/srv/jackson-annotations-2.8.3.jar:/srv/jackson-databind-2.8.3.jar:/srv/jackson-core-2.8.3.jar:/srv/restlet.jar:/srv/restlet-json.jar:/srv/json.jar:/srv/app.jar com/deadlock/socket/Socket
