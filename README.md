# Java_MidTermProejct_NoSpring

## Overview

The following points summarize the project:

* Implementation of an Online Book Shop with Spring Boot microservices
* Every service is connected through a single discovery server
* User registration with different roles
* Access each service through a single gateway
* Authentication and Authorization for all book-service and book-inventory
* Perform CRUD operation on databases

## Technology

The technologies and dependencies that we used are:

* JDK 17
* Spring Boot 2.x
* Spring Security
* Eureka Server and Client
* JSON Web Token (JWT)
* Spring Security
* Spring Data JPA
* MySQL
* Gradle

## Setup

Before initiating the project make sure you have the necessary dependencies like JDK, IDE, etc. Also for database, install XAMPP or MySQL workbench. Install Postman or Insomnia to access services. 

After installing these properties, download the project and load it in your IDE. For connecting database your database with the project, give your username and password in the *appliacation.properties* file that you used to configure your database. Then create an appropriate database on your local machine. 

Now run all the services one by one. Go to [localhost:8761]() in your browser to see if all the services are up.

## Guideline

Now that all the services are up, it's time to use the services. Here's a guideline on how to use each service:

<u>**api-gateway**</u>: In order to access any service, you have to go through the api-gateway. Here's an example:

<localhost:9090/authentication-service/user/login>

In this example, the "localhost:9090" indicates the URL of the api-gateway. Then "/authentication-service" indicates the service name. The last portion is the url of that service.

<u>**authentication-service**</u>: You must register and log in to access the endpoints. For registering, you have to go to this URL: [/user/register](). Then pass your credentials in the following format:

```
{
    "firstName" : "FirstName",
    "lastName" : "Lastname",
    "email" : "user@example.com",
    "password" : "password",
    "user" : "user123",
    "role" : "ADMIN"
}
```

In the role section, either use ADMIN or USER.

Afterwards, you have to log in to the service through this URL: [/user/login]() and give your credentials like the following:

```
{
    "email" : "zawad3@gmail.com",
    "password" : "12345"
}
```

You'll get a token after logging in. Store the token for further usage.

<u>**book-service**</u>: Here's a table that explains the functions of each endpoint and the role needed to access that endpoint:

|   Endpoints   |   Function   |   Role   | JSON Example   |
|   :-------   |   :----   |   :----   |   :-----   |
| /book-service/create| create book entity <br>with book name, <br>author, genre, price | ADMIN | {``` "bookName:" "Game of Thrones"``` <br> ``` "author" : "George R. R. Martin" ``` <br> ``` "genre" : "adventure" ``` <br> ``` "price" : 200 ``` <br> ``` "quantity" : 30 ```}|
| /book-service/update | update quantity <br> and price | ADMIN | {```"bookName" : "Game of Thrones"``` <br> ```"price" : 300```<br> ```"quantity" : 20```} |
| /book-service/delete | delete book entity | ADMIN | {``` "bookName" : "Game of Thrones" ```} |
| /book-service/book/all | fetch all the book <br> data from database | ADMIN,<br> USER | no json body required|
| /book-service/book/{id} | fetch book data by id | ADMIN, <br> USER| pass the book id in the URL <br> no json body required|
| /book-service/buy | orders book | USER | {```"bookName" : "Game of Thrones"``` <br> ``` "quantity" : 5 ```} |

## Contributors

| Name | Email |
| ---- | ----- |
| Mehedi Hasan Emo| hasan.emo@bjitacademy.com |
| Zawadul Islam | zawadul.islam@bjitacademy.com |
| Mohidul Islam | mohidul.islam@bjitacademy.com |
| Akash Chakraborty | akash.chakraborty@bjitacademy.com|


