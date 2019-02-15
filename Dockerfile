FROM openjdk:8
ADD target/futupeople.jar /srv/futupeople.jar
EXPOSE 8000
ENV PORT 8000
CMD ["java", "-jar", "/srv/futupeople.jar"]
