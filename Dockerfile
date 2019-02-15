FROM openjdk:8
ADD target/futupeople.jar /srv/futupeople.jar
EXPOSE 3000
CMD ["java", "-jar", "/srv/futupeople.jar"]
