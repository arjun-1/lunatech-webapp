create table Airport(id integer not null, ident varchar(255), type varchar(255), name varchar(255), latitude_deg varchar(255), longitude_deg varchar(255), elevation_ft varchar(255), continent varchar(255), iso_country varchar(255), iso_region varchar(255), municipality varchar(255), scheduled_service varchar(255), gps_code varchar(255), iata_code varchar(255), local_code varchar(255), home_link varchar(255), wikipedia_link varchar(255), keywords varchar(255), primary key (id));
create table Runway(id integer not null, airport_ref integer, airport_ident varchar(255), length_ft varchar(255), width_ft varchar(255), surface varchar(255), lighted varchar(255), closed varchar(255), le_ident varchar(255), le_latitude_deg varchar(255), le_longitude_deg varchar(255), le_elevation_ft varchar(255), le_heading_degT varchar(255), le_displaced_threshold_ft varchar(255), he_ident varchar(255), he_latitude_deg varchar(255), he_longitude_deg varchar(255), he_elevation_ft varchar(255), he_heading_degT varchar(255), he_displaced_threshold_ft varchar(255), primary key (id));
create table Country(id integer, code varchar(2) not null, name varchar(255), continent varchar(255), wikipedia_link varchar(255), keywords varchar(255), primary key (code));


LOAD DATA LOCAL INFILE '/home/arjun/random-repo/resources/airports.csv'
INTO TABLE Airport
COLUMNS TERMINATED BY ','
OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 LINES;


LOAD DATA LOCAL INFILE '/home/arjun/random-repo/resources/runways.csv'
INTO TABLE Runway
COLUMNS TERMINATED BY ','
OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 LINES;


LOAD DATA LOCAL INFILE '/home/arjun/random-repo/resources/countries.csv'
INTO TABLE Country
COLUMNS TERMINATED BY ','
OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 LINES;