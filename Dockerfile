FROM maven:3.6.1
RUN mkdir /code
WORKDIR /code
ADD . /code/
RUN mvn package