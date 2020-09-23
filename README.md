# Shop Rate

Web application to calculate product price according to the taxes charged for each product. The project front-end was implemented with Angular framework and project 
back-end with Spring Boot framework. The application use postgreSQL to data persistence.

<img src="https://i.ibb.co/sWwdn7J/Whats-App-Image-2020-01-02-at-13-53-33.jpg" alt="combate3" border="0" style="text-align:center;">

## Prerequisites

* [Angular](https://angular.io/)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [PostgreSQL](https://www.postgresql.org/download/)

## Getting Started

* Front-End project : shop-rate-front-end folder.
* Back-End project : shop-rate-back-end folder.

### Build and Deploy Project

* Install postgreSQL
  - Username: postgres
  - Password: admin
* Create database with name: "shop_rate_db"
* Create test database with name: "shop_rate_db_test"
* Deploy Back-End:
  - In back-end folder run the task: "gradle bootRun" 
  - If possible import back-end to intellij ide and run gradle tasks 
* Deploy Front-End:
  - To download dependencies in front-end folder run: "npm install" 
  - Run "ng serve" in front-end folder

### Run Tests

* To execute tests, in back-end folder run: "gradle test"
* The test cases class are in back-end: ...

## License

* GNU General Public License v3.0
