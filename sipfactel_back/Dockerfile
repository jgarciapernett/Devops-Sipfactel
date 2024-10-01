FROM  gradle:6.0.1 as build


WORKDIR /usr/src/app

#Copiar el codigo fuente
COPY . .
#Crear las carpetas necesarias para la compilacion

#Compilar el codigo
RUN gradle bootJar

############
### prod ###
############
FROM openjdk:8-jdk-stretch

WORKDIR /usr/app
COPY --from=build /usr/src/app/core/build/libs /usr/app
EXPOSE 8080
CMD ["java", "-jar","core-0.0.1-SNAPSHOT.jar"]

#Es necesario hacer el mount de las librerias: mount --bind olddir newdir
#docker build -t almabacjava:latest -f /home/jhonnatangil/src/almaviva/titulosback/Dockerfile /home/jhonnatangil/src/almaviva/titulosback
#docker run -it -p 8282:9000 -e PERIFERIA_HOME=/var/log/ --rm almabacjava:latest