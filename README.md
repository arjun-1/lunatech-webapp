[![Build Status](https://travis-ci.org/arjun-1/lunatech-webapp.svg?branch=master)](https://travis-ci.org/arjun-1/lunatech-webapp)
# lunatech-webapp

Solution to the lunatech assignment

## Getting Started

### Compiling and Running 

To compile and run the application, run

```
./sbt run
```
then visit `http://localhost:9000`.
## Anatomy of the application

The application is built using the Play Framework. The 'heart' of the application resides in the folders
 * `models` 
 
 Here we defined the model that describe our data. Basically it are just datastructures via which we can interact with our data (in our case, a SQL database). In this application, we created models `Country`, `Airport` and `Runway`.
 * `controllers` 
 
 Each controller consists of actions, which do something with the requests going to the server. In our application, we have `QueryController` to handle queries, `ReportController` to generate a report, and `HomeController` to serve a welcome page.
 * `views` 
 
 This folder consists of templates to make the html pages rendered by the actions.

The application uses the H2 in memory database and JPA/Hibernate. 
Before the application starts, it populates the database using `dist/init.sql` as specified in `application.conf`:
```
default.url = "jdbc:h2:mem:play;DB_CLOSE_DELAY=-1;INIT=runscript from 'dist/init.sql'"
```
The `init.sql` file calls the `csvread` function from H2 on `countries.csv`, `airports.csv` and `runways.csv` in the `dist/` folder.

Fuzzysearch is implemented on the client side, using [horsey](https://github.com/bevacqua/horsey) and [fuzzysearch](https://github.com/bevacqua/fuzzysearch). This javascript uses a list of countries, which is retreived from `localhost:9000/countries/name`
