# Customer Statement Processor
There is some storage managed by the server, where user can upload customer statement files (xml or csv). Once file uploaded, a validation operation will be executed by the server to validate it.

## Spring Batch Solution
A user as a client can do the following:
  * Upload customer statement file (xml or csv).
  * Subscribe to Server-Sent Events.
  * Get list of all uploaded files with summaried validation result.
  * Get a full validation result for specific file.
  * Delete specific file from storage.

### Run and Test The Application - Using Docker

  * Build docker image, then run container using it
```bash
# Open terminal tab, then run

# Build Docker image from branch 'develop-spring-batch-solution'
docker build https://github.com/devahmedshendy/customer-statement-processor.git#develop-spring-batch-solution:server

# Run Docker container for image 'customer_statement_processor_api:latest'
docker container run --detach --publish 9090:9090 --name customer_statement_processor_api customer_statement_processor_api:latest

# Watch container logs
docker container logs -f customer_statement_processor_api
```

  * Monitor server-sent events
```bash
# Open separate terminal tab, then run
curl "http://localhost:9090/api/sse"
```

  * Test the application
```bash
# Open separate terminal tab, then run

# Upload customer statement file 
# NOTE: Remember to change the path to customer statement file
# NOTE: Some samples to use under 'customer-statement-samples/' in master branch
curl -X POST -H "Content-Type: multipart/form-data" -F "file=@/path/to/records.csv" "http://localhost:9090/api/storage"

# Get list of all uploaded files
curl -X GET "http://localhost:9090/api/storage"

# Get validation result for specific file
curl -X GET "http://localhost:9090/api/validation/{id}"

# Delete from file storage
curl -X DELETE "http://localhost:9090/api/storage?id={id}"
```
