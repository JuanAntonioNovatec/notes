

This is just an example project of the use of SpringBoot on the background and a couple of frontend technologies to use the API builded with it


# Backend


All is under the  src folder in the root source 
Move there and execute `./gradlew bootRun`


This demo project uses MySQL as database engine  and the best way to have one running is using docker and to do it 
with the corrext user and database config us better use docker executing docker with:

`docker-compose up`

With the database running we can run any front version that we want, or both to compare the differences (why not?) 

# Frontend 

## A version (Angular)

Move to  `/angular-front/app` and then run   `ng serve`



## B version (React)

Basically try to replicate the same behaviour as in Angular but using react framework

Move to  `/react-app/app` and then run   `npm start`




# Testing

You can test the API by different ways 

## Simplest test about test endpoint:

To check if API  is running is as simple as execute a CURL comand like this:

```
curl http://localhost:8080/api/test
Hello, World!
```

or use some tool as Insomnia or Postman (as you prefer)
