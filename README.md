# manage-users-jwt
To test the API you can use swagger by accessing this link http://localhost:9090/swagger-ui.html after compiling and starting the application.

## 1 - Génération d'utilisateurs
method: GET
url: /api/users/generate
content-type: application/json
secured: no
parameters: 
- count: number
