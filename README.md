# REVATURE-MAX BACKEND (P2)

## Project Description

A backend API implementation for learning/training technology.

## Technologies Used

* Spring Boot 2.4.3
  - Spring MVC, Spring Data, Spring Test
  - Hibernate, Jackson
* Java JWT 0.11.2
* Microsoft SQL Server
* Azure-hosted Ubuntu VM
  - Jenkins, Docker

## Features

* Registering a new account with name, email, and password.
* Logging in with email and password.
* Providing an access token upon successful login.
* Password salting and hashing for added security.
* Role-based authorization for specific features.
* Creating a new Revature batch and assigning trainees. (Instructor only)
* Managing a curriculum for a batch and assigning quizzes/topics to specific days. (Instructor only)
* Inputting quiz scores and topic competencies. (Associate only)
* Viewing quiz score averages and topic competency averages for a batch.


## Getting Started
   
(include git clone command)
(include all environment setup steps)

> Be sure to include BOTH Windows and Unix command  
> Be sure to mention if the commands only work on a specific platform (eg. AWS, GCP)

- All the `code` required to get started
- Images of what it should look like

## Endpoints

### /login

> **POST /login**
> - Requires Content-Type: application/x-www-form-urlencoded
>   - email and password form parameters required
> - Returns token string in body used for authentication or 401

### /employees

> **GET /employees/{employee-id}**
> - Requires authentication as employee resource owner
> - Returns employee information in a DTO or 401/404

> **POST /employees**
> - Requires Content-Type: application/x-www-form-urlencoded
>   - username, email, and password form parameters required
> - Creates new associate or returns 409 upon conflicting email

> **PUT /employees/{employee-id}/quizzes/{quiz-id}**
> - Requires authentication as employee resource owner
> - Content-Type: application/json
>   - Requires request body: { "score": [VALUE] }

> **PUT /employees/{employee-id}/topics/{topic-id}**
> - Requires authentication as employee resource owner
> - Content-Type: application/json
>   - Requires request body: { "competency": [VALUE or null], "favNotes": [NOTES_ID or null] }

> **PUT /employees/{employee-id}/notes**
> - Requires authentication as employee resource owner
> - Content-Type: application/json
>   - Requires request body: { "id": [NOTES_ID or null], "topic": { "id": [TOPIC_ID] }, "content": [VALUE] }
> - Returns id of new notes or null contents empty

### /batches

> **GET /batches/{batch-id}**
> - Requires authentication as batch instructor or associate assigned to batch
> - Returns batch information DTO with quiz score averages and topic competency averages

> **GET /batches/{batch-id}/curriculum**
> - Requires authentication as batch instructor or associate assigned to batch
> - Returns the days in the curriculum with quizzes/topics set for each day

> **POST /batches/{batch-id}/curriculum**
> - Requires authentication as batch instructor
> - Requires Content-Type: application/json
>   - Requires request body as a specific DTO to set the quizzes/topics for a day

> **POST /batches/{batch-id}/quizzes**
> - Requires authentication as batch instructor
> - Requires Content-Type: application/json
>   - Requires request body as a specific DTO to set the quizzes/topics for a day

> **PUT /batches/{batch-id}/quizzes/{quiz-id}**

> **DELETE /batches/{batch-id}/quizzes/{quiz-id}**

> **GET /batches/{batch-id}/topics/{topic-id}**

> **GET /batches/{batch-id}/associates**

> **POST /batches/{batch-id}/associates**

### /topics

## Contributors

* Andrew Shields (@andrew.shields) - Team Lead
* Jose Rubio (@rubioj.m.93) - Frontend Lead
* Steffen Moseley (@scm16) - Backend Lead
* Tucker Richlie (@mtrichlie) - Git Flow Manager
