# Shop Rate

Web application to calculate product prices according to taxes charged for each product. The project front-end was implemented with Angular framework and project 
back-end with Spring Boot framework. The application use postgreSQL to data persistence.

<img src="https://i.ibb.co/9G4Zh2N/b58bcb98-3694-47df-a7e0-15e870e0105e.jpg" alt="combate3" border="0" style="text-align:center;">

## System Architecture

### Domain

<img src="https://i.ibb.co/Y7mnjjf/078c4cca-e0f0-4776-a896-0eab2e6d509e.jpg" alt="combate3" border="0" style="text-align:center;">

* Produto:
  - Attributes: id, name, price (without rate), totalRatePrice (rate or tax value) e productRateList (product rates or taxes)
  - Métodos: getTotalPrice (price with taxes included), getTotalRatePrice (product tax value)
  
* ProductRate (contains rate information for each type of product):
  - Attributes: id, rate (percentage rate value), productType (unique by product rate)

### System Flow (MVC Pattern)

<img src="https://i.ibb.co/Vjn1Vfw/b41fafd1-c7c0-4221-a333-24cdb695c0f8.jpg" alt="combate3" border="0" style="text-align:center;">

* Product Controller (end-point):
  - findAll: get all products
  - find: get product by id
  - save: save product
  - delete: delete product by id
  - getProductPrices: passing product ids, return product prices with taxes included
  
* Product Service:
  - findAll: get all products
  - find: get product by query params
  - save: save product
  - delete: delete product by id
  - getProductPrices: calculate products total prices and taxes by the rates of each product

* Product Repository (crud methods to handler product data) 
  
## Prerequisites

* [Angular](https://angular.io/)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [PostgreSQL](https://www.postgresql.org/download/)

## Getting Started

* Front-End project: shop-rate-front-end folder
* Back-End project: shop-rate-back-end folder

### Build and Deploy Project

* Install postgreSQL
  - Username: postgres
  - Password: admin
* Create database with name: "shop_rate_db"
* Create test database with name: "shop_rate_db_test"
* Deploy Back-End:
  - In back-end folder run "bootRun" gradle the task 
  - If possible import back-end to intellij ide and run gradle tasks 
* Deploy Front-End:
  - To download dependencies in front-end folder run: "npm install" 
  - Run "npm start" in front-end folder

### Run Tests

* To execute tests run "test" gradle task
* The test cases class are in shop-rate-back-end:
  - ../test/java/com/project/shoprate

## License

* GNU General Public License v3.0
