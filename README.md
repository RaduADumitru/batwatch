## BatWatch

Application for reporting bat sightings causing conflicts (such as bat appearances in homes), managing tracking resolution.

The application is built with Spring Boot and uses PostGIS for geospatial data storage.

## Development

Local development can be done using Docker Compose. `docker-compose.yml` defines:
- Spring Boot application
- PostgresDB with PostGIS extension
- PgAdmin for database management via UI

### Run

`docker-compose up --build`

This will open debug port 5005 for the Spring Boot application.

Docker setup makes use of `.env` file for configuration, values inside can be modified as needed.

### Access database from PgAdmin

Assuming docker containers are running:
- navigate to PgAdmin UI: by default http://localhost:5050, port defined in `.env`
- log into UI with credentials defined in `.env` file: by default email `root@batwatch.com`, password `123456`
- to connect to DB: right-click on "Servers" in the left sidebar and select "Register" → "Server..."
- In the "General" tab, enter a name for the server (e.g., `batwatch`)
- In the "Connection" tab, enter the following details:
  - Host name/address: `postgresdb` (this is the service name defined in `docker-compose.yml`)
  - Postgres port, username and password as defined in `.env` file. By default port `5432`, username `root`, password `123456`, only password needs to be manually set. Optionally save the password
- `Save` to register the server and connect to the database
- Navigate to `Servers` → `batwatch` → `Databases` → `batwatch` to access the database. From here you can access the application tables under `Schemas` → `public` → `Tables`.

### Wipe data from docker managed DB
`docker-compose down --volumes`

More on PostGIS https://medium.com/@abiodunstarr/location-based-features-using-postgis-in-a-spring-boot-application-4c8d710d99df

