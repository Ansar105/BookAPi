Rest Assured Book API Framework – Setup & Usage Guide
This framework is designed to test a FastAPI-powered BookStore application with full CRUD coverage, authentication, error handling, and reporting.
Tech Stack
•	Java 17 → Core implementation language
•	RestAssured → API automation & validations
•	TestNG → Test execution flow & configuration
•	ExtentReports → Interactive HTML test reports
•	Maven → Build tool & dependency management
•	GitHub Actions → CI/CD pipeline integration
What the Framework Covers
Core Functional Tests
•	Check if the server is up and running (health check)
•	Register a new user and log in with valid credentials\
•	Add a new book with valid details
•	Fetch the list of all books
•	Retrieve a specific book by its ID
•	Update an existing book’s details
•	Delete a book from the system

Negative & Edge Case
•	Access APIs with invalid/missing tokens
•	Try fetching non-existent books
•	Attempt login/signup with invalid credentials
•	Validate unexpected responses (400, 422, 500 errors)
Request Chaining
•	The access token from login is automatically reused in subsequent requests (create, update, delete).
•	Ensures realistic authentication flows.



Test Strategy

1.	Architecture
Separate test classes for User, Book, and Health APIs
dependsOnGroups used to maintain order (e.g., Book tests run only after User login succeeds)
Centralized data.json file for request payloads
2.	Scalability
Configurable base URL & tokens (config.properties)
Clear separation of concerns → logic, request specs, data, reporting
Positive + Negative test coverage
Common utilities for status code & response validations
BaseTest + Listeners manage test lifecycle & reports
3.	Challenges & Resolutions
500 error instead of 422 → Added assertions to flag backend issue
Unexpected 400 errors → Suggested Swagger documentation improvements
No Delete API for User → Proposed API enhancement or reusing test users
Execution issues due to dependencies → Fixed using dependsOnGroups
Missing backend validations → Recommended stronger input validation
How to Run the Tests
Prerequisites
Java 17+
Maven 3.6+
Git
Steps
1. Clone the repo:
          git clone https://github.com/Ansar105/BookAPi
cd RESTAPIPOC
2. Update the config.properties file with your base URL:
          url=http://localhost:8000
3.Run the tests:
          mvn clean test
4.View the report:
          Open → test-output/testReport.html
