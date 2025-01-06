
# Task Manager

REST API for creating todo list.
---
Build project:
```
mvn clean package
```

Run project on Docker:
```
docker compose up -d
```
---
# API
Create new user:
```
POST http://localhost:8080/task-manager/api/auth/register/
{
    "email": "jan.kowalski@gmail.com",
    "password": "password"
}
```
---
Log in:
```
POST http://localhost:8080/task-manager/api/auth/login/
{
    "email": "jan.kowalski@gmail.com",
    "password": "password"
}

Response: JWT token
```

Get JWT token from log in, and use it in requests to /tasks endpoints.

---
Create task:
```
POST http://localhost:8080/task-manager/api/tasks
Authorization: Bearer {JWT token}
{    
    "title": "...",
    "description": "...",
    "deadline": "2001-01-01T01:01:01",
    "isCompleted": false
}
```
---
Get tasks:
```
GET http://localhost:8080/task-manager/api/tasks?completed=false&deadlineFrom=2025-01-03T00:00:00&deadlineTo=2025-01-04T00:00:00
Authorization: Bearer {JWT token}
```
Get single task:
```
GET http://localhost:8080/task-manager/api/tasks/{task-uuid}
Authorization: Bearer {JWT token}
```
---
Update task:
```
PUT http://localhost:8080/task-manager/api/tasks
Authorization: Bearer {JWT token}
{    
    "uuid": {task uuid}
    "title": "...",
    "description": "...",
    "deadline": "2001-01-01T01:01:01",
    "isCompleted": false
}
```
---
Delete task:
```
DELETE http://localhost:8080/task-manager/api/tasks/{task-uuid}
Authorization: Bearer {JWT token}
```