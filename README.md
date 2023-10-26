* * *

Cart Application
============

This application built in java provides functionality for managing carts and their associated products.


Building the project
--------------------

1.  Clone the project
2.  Build the project using Maven:
    `mvn install package`

Dockerizing the application
-----------------------

1.  Build the Docker image for the application:

    `docker build -t cart:0.1 .`

2.  Run the Docker container:

    `docker run -p 9090:9090 cart:0.1`

Services
---------------------


### Swagger UI

You can explore and interact with the API using Swagger UI:

[http://localhost:9090/swagger-ui/index.html](http://localhost:9090/swagger-ui/index.html)

### Data Insert Controller (POST)


 I made this endpoint so that it would not be annoying to insert the carts and products you can insert data by making a post request to `/api/data/insert-data`
 This endpoint always delete the data in db and after deletion it inserts 4 carts, and 4 products.

 ### Extra

 The carts have a relation n:m with products, you can NOT add a product which doesnt exist to the cart.

 If the prodcuts is already in the cart, the amount is added to the prev amount. 


* * *
