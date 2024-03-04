## facebook-feed-demo
This project tackles a classic system design challenge for creatin  a basic version of Facebook or Twitter feeds(timeline).  
It consists of two main applications: PostWriter for post management and PostReader for getting the feed

### PostWriter  
* Spring Boot: with classic Spring Web MVC 
* Liquibase
* Postgresql 
* Kafka

### PostReader
* Spring Boot: with WebFlux
* Kafka

### Usage
Retrieve a user's feed with `GET /api/pw/feed/{userName}`  
Example: `http://localhost:8080/api/feed/morty`.


