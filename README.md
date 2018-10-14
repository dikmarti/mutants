Configurar BD

db.default.url="jdbc:sqlite:${PLAY_APPS_ROOT}\\db\sqlite"
Cambiar en la url por la direccion del archivo de bd

Run App

Ir al direcotrio mutant y ejecutar: sbt "run 8080"

[<img src="https://img.shields.io/travis/playframework/play-java-starter-example.svg"/>](https://travis-ci.org/playframework/play-java-starter-example)

# play-mutant-challenge

This is a starter application that shows how Play works.  

## Running

```
sbt "run 8085"
```

And then go to http://localhost:8085 to see the running web application.

## Controllers

- AsyncController.java:

  Shows if an ADN belongs to mutant.


