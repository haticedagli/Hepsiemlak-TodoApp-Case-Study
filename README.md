# To Do Application

This application is a personal task management application developed to enable users to track and focus on the tasks they need to do.

## Running 

```
docker-compose up
```

## 🔗 Links
[![linkedin](https://img.shields.io/badge/linkedin-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://tr.linkedin.com/in/hatice-dagli)
[![twitter](https://img.shields.io/badge/twitter-1DA1F2?style=for-the-badge&logo=twitter&logoColor=white)](https://twitter.com/HaticeDaglidev)


## Tech Stack

*Server:* Java, Spring, Spring Boot, JPA, Hibernate, Junit, Lombok

*Database:* Couchbase

*CI/CD:* Docker


## Authors

- [@haticedagli](https://www.github.com/haticedagli)


## Diagrams

![Database ER diagram (crow's foot)](https://user-images.githubusercontent.com/62377943/190837599-97420f41-09e9-4933-a9a8-57e36218268d.png)

## Screenshots

![screencapture-localhost-8080-swagger-ui-index-html-2022-09-17-05_36_28](https://user-images.githubusercontent.com/62377943/190837607-30a74e88-c4c5-4806-9a57-3d0d566335c7.png)

<img width="1473" alt="Ekran Resmi 2022-09-17 06 05 22" src="https://user-images.githubusercontent.com/62377943/190838271-7100adfc-7be8-4930-a843-fb745e8422c7.png">

<img width="1509" alt="Ekran Resmi 2022-09-17 05 38 11" src="https://user-images.githubusercontent.com/62377943/190837624-95d66c5e-92f9-4260-b0fd-427de6bc4541.png">


## API Reference

Note: Open swagger after running the application. You will see "Authorize" button at the right top corner. You can simply paste your token in there and start to use the api. 

- [Swagger-UI](localhost:8080/swagger-ui.html)


## Manuel Setup 

- Setting Couchbase

```
docker run -d --name db3 -p 8091-8096:8091-8096 -p 11210-11211:11210-11211 couchbase
```

After that, you can access couchbase interface via *http://localhost:8091*

When you open couchbase interface, you have to create a cluster that has username *"Administrator"* password *"password"*

And then create a bucket named *"Todo"*

And create a primary index on this bucket with following query.

```
CREATE PRIMARY INDEX ON `Todo`._default._default;
```

- Running app

```
mvn clean install
mvn spring-boot:run
```

  
