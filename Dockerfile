FROM java:8
COPY build/libs/revature-max-0.0.1.jar .
EXPOSE 80
CMD java -jar revature-max-0.0.1.jar