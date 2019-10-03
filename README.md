# Customer Statement Processor
There is some storage managed by the server, where user can upload customer statement files (xml or csv). Once file uploaded, a validation operation will be executed by the server to validate it.

## Spring Batch Solution
The user can do the following:
  * Upload customer statement file (xml or csv).
  * Subscribe to Server-Sent Events.
  * Get list of all uploaded files with summarized validation result.
  * Get a full validation result for specific file.
  * Delete specific file from storage.

### Build The Application - Using Docker (Remote or Local)

  * Remote
```bash
# Open new terminal tab, then run

# Build Docker image from branch 'master'
docker build https://github.com/devahmedshendy/customer-statement-processor.git

# Run Docker container for image 'customer_statement_processor:latest'
docker container run --detach --publish 9090:9090 --name customer_statement_processor customer_statement_processor:latest

# Watch container logs
docker container logs -f customer_statement_processor
```

  * Local
```bash
# Open new terminal tab, then run

# Clone repository
git clone https://github.com/devahmedshendy/customer-statement-processor.git

# Change directory to project
cd customer_statement_processor

# Build Docker image from master branch with name 'customer_statement_processor'
docker build -t customer_statement_processor .

# Run Docker container for image 'customer_statement_processor:latest'
docker container run --detach --publish 9090:9090 --name customer_statement_processor customer_statement_processor:latest

# Watch container logs
docker container logs -f customer_statement_processor
```

### Build The Application - Using Maven

```bash
# Open new terminal tab, then run

# Clone repository
git clone https://github.com/devahmedshendy/customer-statement-processor.git

# Change directory to project
cd customer-statement-processor

# Build application using Maven
mvn clean package

# Run Spring boot application using Maven
mvn spring-boot:run
```

### Test The Application

  * Monitor server-sent events
```bash
# Open separate terminal tab, then run
curl "http://localhost:9090/api/sse"
```

  * Test the application
```bash
# Open separate terminal tab, then run

# Change to project directory
cd customer-statement-processor

# Upload customer statement file that contains errors 
curl -X POST -H "Content-Type: multipart/form-data" -F "file=@./sample/bad_records.csv" "http://localhost:9090/api/storage"

# Upload customer statement file that doesn't contain errors 
curl -X POST -H "Content-Type: multipart/form-data" -F "file=@./sample/records.csv" "http://localhost:9090/api/storage"

# Get list of all uploaded files
curl -X GET "http://localhost:9090/api/storage"

# Get validation result for specific file
curl -X GET "http://localhost:9090/api/validation/{id}"

# Delete from file storage
curl -X DELETE "http://localhost:9090/api/storage?id={id}"
```
