# Archive Wizard - Read Me

This ReadMe explains how to configure and run the backend for ArchiveWizard.

## Requirements

Before starting the application, make sure you have:

- JDK 25
- PostgreSQL running and accessible
- OpenSSL installed for generating JWT RSA keys

## 1. Configure database connection settings

The application expects the following Gradle properties to be available:

- `dbHost`
- `dbPort`
- `dbName`
- `dbUsername`
- `dbPassword`

You can provide them in either of these locations:

- `~/.gradle/gradle.properties`
- the project root `gradle.properties`

Example:

```properties
# Database connection settings
dbHost=
dbPort=
dbName=

# Database credentials
dbUsername=
dbPassword=
```

The project also supports environment variables as fallbacks:

```bash
export DB_HOST=
export DB_PORT=
export DB_NAME=
export DB_USERNAME=
export DB_PASSWORD=
```

These values are used by the Spring Boot configuration in `application.properties` to build the datasource URL.

## 2. Generate JWT signing keys

The backend loads RSA keys from:

- `src/main/resources/certs/private.pem`
- `src/main/resources/certs/public.pem`

Create the folder and generate the key pair:

### Bash

```bash
mkdir -p src/main/resources/certs
openssl genrsa -out src/main/resources/certs/private.pem 2048
openssl rsa -in src/main/resources/certs/private.pem -pubout -out src/main/resources/certs/public.pem
```

### PowerShell

```powershell
New-Item -ItemType Directory -Force -Path .\src\main\resources\certs | Out-Null
openssl genrsa -out .\src\main\resources\certs\private.pem 2048
openssl rsa -in .\src\main\resources\certs\private.pem -pubout -out .\src\main\resources\certs\public.pem
```

## 3. Start the backend

From the project root, run:

### macOS / Linux

```bash
./gradlew bootRun
```

### Windows

```powershell
.\gradlew.bat bootRun
```

Wait until Gradle finishes starting the Spring Boot application. Once the server is up, the backend is ready to accept requests.

## Notes

- This project uses Spring Boot and PostgreSQL.
- The default database schema is configured for local development and may be recreated automatically depending on the JPA settings.
- Ensure Java 25 is selected as the active JDK before running Gradle.

## Troubleshooting

### Gradle throws a Java version error

Install and activate JDK 25, then verify:

```bash
java -version
```

### Database connection fails

Check that:

- PostgreSQL is running
- the host, port, database name, username, and password are correct
- the database user has permission to create/access the target database

### JWT authentication fails

Make sure both certificate files exist and are readable:

```bash
ls -l src/main/resources/certs
```

The files must be named exactly:

- `private.pem`
- `public.pem`

Once the ReadinessState in the Command Line Interface changes to `ACCEPTING_TRAFFIC`, the backend is ready to accept requests.