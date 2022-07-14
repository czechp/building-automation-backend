FROM openjdk:11
RUN apt-get update -y
RUN apt-get install -y maven
RUN apt-get install -y git
RUN mkdir /automation-backend
WORKDIR /automation-backend
COPY init.sh .
RUN chmod 777 init.sh
EXPOSE 8080:8080
ENTRYPOINT ["/automation-backend/init.sh"]
