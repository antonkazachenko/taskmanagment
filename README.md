# Task Management API

The **Task Management API** is a RESTful API designed for managing tasks effectively. This project leverages modern tools and frameworks to ensure robust functionality and ease of deployment.

## Features
- **RESTful API**: Built with Java and Spring Boot for efficient task management.
- **Database Integration**: Uses MySQL for reliable and persistent data storage.
- **Containerization**: Dockerized for seamless deployment and scalability.
- **Testing**: Core functionality is implemented and tested using JUnit to ensure high code quality.

## Technologies Used
- **Java**
- **Spring Boot**
- **MySQL**
- **Docker**
- **JUnit**

## Getting Started
To get started with the Task Management API, follow the steps below:

1. Clone the repository:
   ```bash
   git clone https://github.com/antonkazachenko/taskmanagment.git
   cd taskmanagment
   ```
2. **Set up MySQL**:
   - Create a database for the API.
   - Update the database configuration in `application.properties`.

3. **Build and run the application**:
   ```bash
   ./mvnw spring-boot:run
   ```
4. **Use Docker (Optional)**:
   - Build the Docker image:
     ```bash
     docker build -t task-management-api .
     ```
   - Run the container:
     ```bash
     docker run -p 8080:8080 task-management-api
     ```

## API Endpoints

The API provides the following endpoints for task management:

1. `GET /api/tasks` 
   - Retrieve all tasks with pagination and sorting.
   - Query Parameters:
     - `page` (default: 0) - Page number.
     - `size` (default: 10) - Number of tasks per page.
     - `sortBy` (default: "id") - Field to sort by.

2. `POST /api/tasks` 
   - Create a new task.
   - Request Body:
     - JSON object containing task details.

3. `PUT /api/tasks/{id}` 
   - Update an existing task.
   - Path Variable:
     - `id` - ID of the task to update.
   - Request Body:
     - JSON object containing updated task details.

4. `DELETE /api/tasks/{id}` 
   - Delete an existing task.
   - Path Variable:
     - `id` - ID of the task to delete.

5. `GET /api/tasks/search`
   - Search tasks by query string.
   - Query Parameters:
     - `query` - The search keyword.

