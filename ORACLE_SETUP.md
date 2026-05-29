# Oracle Database Setup Guide

## Prerequisites

### 1. Install Docker Desktop

```bash
# Install Docker Desktop (if not already installed)
brew install --cask docker
```

Then launch **Docker Desktop** and make sure it's running.

### 2. Pull Oracle Database XE Image

```bash
docker pull gvenzl/oracle-xe
```

### 3. Run Oracle Database Container

```bash
docker run -d \
  --name oracle-xe \
  -p 1521:1521 -p 5500:5500 \
  -e ORACLE_PASSWORD=oracle \
  gvenzl/oracle-xe
```

### 4. Verify Container is Running

```bash
docker ps
```

You should see the `oracle-xe` container running.

### 5. Wait for Database to Start

The database takes about 2-3 minutes to fully start. Check the logs:

```bash
docker logs oracle-xe
```

Wait until you see: `DATABASE IS READY TO USE!`

## Database Connection Details

- **Host:** `localhost`
- **Port:** `1521`
- **Service Name:** `XEPDB1`
- **Username:** `system`
- **Password:** `oracle`

## Create Application User (Optional but Recommended)

### Connect to Database

```bash
docker exec -it oracle-xe sqlplus system/oracle@XEPDB1
```

### Create User and Grant Permissions

```sql
-- Create user
CREATE USER taskmanager IDENTIFIED BY taskmanager123;

-- Grant necessary privileges
GRANT CONNECT, RESOURCE TO taskmanager;
GRANT CREATE SESSION TO taskmanager;
GRANT CREATE TABLE TO taskmanager;
GRANT CREATE SEQUENCE TO taskmanager;
GRANT CREATE VIEW TO taskmanager;
GRANT UNLIMITED TABLESPACE TO taskmanager;

-- Create sequence for ID generation
CREATE SEQUENCE task_seq
START WITH 1
INCREMENT BY 1
NOCACHE
NOCYCLE;

-- Grant sequence access to user
GRANT SELECT, ALTER ON task_seq TO taskmanager;

-- Exit
EXIT;
```

### Update Application Properties (if using custom user)

If you created a custom user, update `application.properties`:

```properties
spring.datasource.username=taskmanager
spring.datasource.password=taskmanager123
```

## Oracle Enterprise Manager (Optional)

Access Oracle Enterprise Manager at: http://localhost:5500/em

- **Username:** `system`
- **Password:** `oracle`

## Useful Docker Commands

### Stop the Container
```bash
docker stop oracle-xe
```

### Start the Container
```bash
docker start oracle-xe
```

### Remove the Container
```bash
docker stop oracle-xe
docker rm oracle-xe
```

### View Container Logs
```bash
docker logs oracle-xe
```

### Connect to Database via SQL*Plus
```bash
docker exec -it oracle-xe sqlplus system/oracle@XEPDB1
```

## Testing the Connection

### 1. Test with SQL*Plus
```bash
docker exec -it oracle-xe sqlplus system/oracle@XEPDB1
```

Then run:
```sql
SELECT * FROM USER_TABLES;
EXIT;
```

### 2. Test with Your Spring Boot Application

Run your Spring Boot application:

```bash
mvn spring-boot:run
```

Check the logs for successful database connection.

## Troubleshooting

### Container Won't Start
- Make sure Docker Desktop is running
- Check if port 1521 is already in use: `lsof -i :1521`
- Try removing and recreating the container

### Connection Refused
- Wait for the database to fully start (check logs)
- Verify the container is running: `docker ps`
- Check the correct service name (XEPDB1, not XE)

### Authentication Issues
- Default password is `oracle`
- Make sure you're using the correct service name
- Try connecting as `system` user first

### Port Already in Use
```bash
# Find what's using port 1521
lsof -i :1521

# Kill the process if needed
kill -9 <PID>
```

## Next Steps

1. Start the Oracle container using the commands above
2. Run your Spring Boot application: `mvn spring-boot:run`
3. Test the API endpoints to verify Oracle integration
4. Check the database tables are created automatically

## Database Schema

Your application will automatically create the following tables:
- `TASKS` - Main task table
- `TASK_SEQ` - Sequence for ID generation

You can verify this by connecting to the database and running:
```sql
SELECT table_name FROM user_tables;
SELECT sequence_name FROM user_sequences;
```
