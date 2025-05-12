


# Bakend


Todo está bajo la carpeta src `./gradlew bootRun`

Necesitamos tener corriendpo mysql y que mejor que hacerlo con Docker y para ello nada mejor que un docker-compose que automatiza el levantar la imagen con el usuario y contraseña que necesitamos.

Cómo?

`docker-compose up`

Una vez la base de datos corriendo

# Front 

## Version A (Angular)

Nos movemos a `/angular-front/app` y ahí ejecutamos `ng serve`

## Version B (React)

Nos movemos a `/react-app/app` y ahí ejecutamos `npm start`



# Testing

## Test endpoint:

Para comprobar que se ha levantado bien puedes testearlo con cualquiera de las versiones del front o hacien por terminal la petición más simple del api, e clásico hello world:

```
curl http://localhost:8080/hi-notes
Hello, World!
```
