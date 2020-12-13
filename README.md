# Employee-Service

As its name suggests, this service is responsible for handling the employees of a company. The application must expose a REST API. It should contain endpoints to:
  - Create a department
    - Id (auto-increment)
    - Name
    
 - Create an employee with the following properties:
   - Uuid (generated automatically)
   - E-mail
   - Full name (first and last name)
   - Birthday (format YYYY-MM-DD)
   - Employee’s department
   
  - Get a specific employee by uuid (response in JSON Object format)
  - Update an employee
  - Delete an employee

Whenever an employee is created, updated or deleted, an event related to this action must be pushed in Kafka. This event will be listened to by the [`event-service`](https://github.com/takeaway/bob-challenge-event-service/).

#### Restrictions

 - The `email field` is unique, i.e. _2 employees cannot have the same email._

# Install and run

# Requirements
	- Docker/Docker Compose
	- maven
	- JDK 1.8
	- Git client
	
# Running the application

	- Naviagte to the root folder of the bob-challenge-employee-service project. Launch the supporting services: ZooKeeper, Kafka and MySQL database by running the command:
```
docker-compose up -d
```
	
	- Open the bob-challenge-employee-service project in an IDE like Eclipse/STS. Launch the application as a SpringBoot project.

	- Open the bob-challenge-event-service project in an IDE like Eclipse/STS. Launch the application as a SpringBoot project.

# Application resources available:
	- Fetch all employees: http://localhost:8181/employee-service/api/employees
    - Fetch an employee by Id: http://localhost:8181/employee-service/api/employee/{employeeId}
    - Fetch all departments: http://localhost:8181/employee-service/api/departments
    - Fetch a department by Id: http://localhost:8181/employee-service/api/deployment/{deploymentId}
    - Fetch all events: http://localhost:8182/event-service/api/events
    - OpenAPI specification: http://localhost:8181/employee-service/v3/api-docs/

