# KBox Assignment 
## Run Tests
```
./mvnw test
```

## Run Application
```
./mvnw spring-boot:run
```

## Sample Requests

### List profile views of a user in last 30 Days
```
curl http://localhost:8080/api/profile/{userId}
```

### Record profile view of a user
```
curl -X POST http://localhost:8080/api/profile/{userId}?viewerUserId={viewerUserId}
```