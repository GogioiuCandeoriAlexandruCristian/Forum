# Forum
run "mvn clean install" for generating(in target folder) Mappers implementations.
Execute script from file "insert_script.sql" in your MySql database

Steps 1: Postman request (POST) : http://localhost:8080/api/auth/signup -> For sign up
body-raw(JSON):
{
    "username":"test",
    "email":"alexx@gmail.com",
    "password":"parola123",
    "points":0
}

Step 2:Postman request (POST): http://localhost:8080/api/auth/signin
body-raw(JSON):
{
    "username":"test",
    "password":"parola123"
}
Response body: {
    "id": 2,
    "username": "test",
    "email": "alexx@gmail.com",
    "roles": [
        "ROLE_USER"
    ],
    "tokenType": "Bearer",
    "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNTk0ODI2NTE1LCJleHAiOjE1OTQ5MTI5MTV9.k43SlHAOEibshQ7I65-_xV82bB7fpGwp3jcDGrIAiIviOYlcgDJdlz9G5KaBGxI3siAWg8hkfW61pcs3I1rJ2g"
}

Step 3: Postamn -> any other request should have authorization token set in HEADERS -> Authorization Bearer "generated JWT"
