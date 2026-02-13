# Authentication System - API Documentation

## Overview
This authentication system provides login and signup functionality for your Spring Boot application.

## Files Created/Updated

### DTOs (Data Transfer Objects)
1. **LoginRequest.java** - Request body for login
2. **SignupRequest.java** - Request body for signup
3. **AuthResponse.java** - Response body for authentication operations

### Service Layer
1. **AuthService.java** - Business logic for authentication

### Controller Layer
1. **AuthController.java** - REST API endpoints for authentication

### Repository Layer
1. **UserRepository.java** - Updated with `findByUsername()` method

---

## API Endpoints

### 1. Health Check
- **URL**: `GET /api/auth/health`
- **Response**: `"Auth service is running"`

### 2. User Signup
- **URL**: `POST /api/auth/signup`
- **Content-Type**: `application/json`
- **Request Body**:
```json
{
  "username": "john_doe",
  "password": "password123",
  "firstName": "John",
  "lastName": "Doe",
  "role": "Customer"  // Optional, defaults to "Customer"
}
```
- **Success Response** (201 Created):
```json
{
  "userId": 1,
  "username": "john_doe",
  "firstName": "John",
  "lastName": "Doe",
  "role": "Customer",
  "message": "User registered successfully",
  "success": true
}
```
- **Error Response** (400 Bad Request):
```json
{
  "success": false,
  "message": "Username already exists"
}
```

### 3. User Login
- **URL**: `POST /api/auth/login`
- **Content-Type**: `application/json`
- **Request Body**:
```json
{
  "username": "john_doe",
  "password": "password123"
}
```
- **Success Response** (200 OK):
```json
{
  "userId": 1,
  "username": "john_doe",
  "firstName": "John",
  "lastName": "Doe",
  "role": "Customer",
  "message": "Login successful",
  "success": true
}
```
- **Error Responses**:
  - 401 Unauthorized (invalid credentials):
  ```json
  {
    "success": false,
    "message": "Invalid password"
  }
  ```
  - 400 Bad Request (missing fields):
  ```json
  {
    "success": false,
    "message": "Username and password are required"
  }
  ```

---

## Testing with cURL

### Signup Example:
```bash
curl -X POST http://localhost:8080/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "password123",
    "firstName": "Test",
    "lastName": "User"
  }'
```

### Login Example:
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "password123"
  }'
```

---

## Features

### Signup Features:
✅ Username uniqueness validation
✅ Password length validation (minimum 6 characters)
✅ Required field validation
✅ Default role assignment (Customer)
✅ Automatic timestamp creation (via @CreationTimestamp)

### Login Features:
✅ Username verification
✅ Password validation
✅ User details in response
✅ Proper error handling

---

## Security Notes

⚠️ **IMPORTANT**: The current implementation uses **plain text password storage** for development purposes. 

### For Production:
You should implement password hashing using BCrypt:

1. Add Spring Security dependency to `pom.xml`:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

2. Update AuthService to use BCrypt:
```java
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class AuthService {
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    // In signup:
    newUser.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
    
    // In login:
    if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
        // Invalid password
    }
}
```

---

## Next Steps

1. **Run the application** and test the endpoints
2. **Implement password hashing** for production
3. **Add JWT tokens** for session management
4. **Add email verification** for signup
5. **Implement password reset** functionality
6. **Add rate limiting** to prevent brute force attacks
7. **Configure CORS** properly for your frontend domain

---

## Validation Rules

- **Username**: Required, must be unique
- **Password**: Required, minimum 6 characters
- **FirstName**: Optional
- **LastName**: Optional
- **Role**: Optional (defaults to Customer)

---

## Error Handling

All endpoints return proper HTTP status codes:
- **200 OK** - Successful login
- **201 Created** - Successful signup
- **400 Bad Request** - Invalid input or validation failed
- **401 Unauthorized** - Invalid credentials
- **500 Internal Server Error** - Server error
