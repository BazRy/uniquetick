FROM containers.repository.dev.wept.mes.corp.hmrc.gov.uk/openjdk/openjdk-11-rhel7:1.1
LABEL "Developer"="Barry Ryan"
WORKDIR /app
COPY target/challenge-0.0.1-SNAPSHOT.jar challenge-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","challenge-0.0.1-SNAPSHOT.jar"]