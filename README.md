# SpringBoot E-Commerce Application

A RESTful e-commerce backend application built with Spring Boot, featuring user management, product catalog, and shopping cart functionality.

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Database Configuration](#database-configuration)
- [Building and Running](#building-and-running)
- [Contributing](#contributing)

## 🎯 Overview

This is a demo Spring Boot application that implements core e-commerce functionality including user registration, product management, and shopping cart operations. The application uses an in-memory H2 database for development and testing purposes.

## ✨ Features

### User Management
- Create, read, update, and delete users
- User authentication with username and password
- User roles (Customer/Admin)
- Address management for users
- Automatic timestamp tracking (created/updated)

### Product Management
- Complete CRUD operations for products
- Product attributes: name, description, price, stock quantity, image URL, category
- Product activation/deactivation
- Automatic timestamp tracking

### Shopping Cart
- Add products to cart
- Update cart item quantities
- Remove items from cart
- View cart items by user
- User-specific cart management via custom headers

## 🛠️ Technology Stack

- **Framework**: Spring Boot 4.0.2
- **Language**: Java 17
- **Build Tool**: Maven
- **Database**: H2 (in-memory)
- **ORM**: Spring Data JPA / Hibernate
- **Libraries**:
  - Lombok (for reducing boilerplate code)
  - Jakarta Persistence API
  - Spring Web MVC

## 📁 Project Structure

```
demo/
├── src/
│   ├── main/
│   │   ├── java/com/example/demo/
│   │   │   ├── Controller/
│   │   │   │   ├── CartController.java
│   │   │   │   ├── ProductController.java
│   │   │   │   └── UserController.java
│   │   │   ├── DTO/
│   │   │   │   ├── AddressDTO.java
│   │   │   │   ├── CartItemRequest.java
│   │   │   │   ├── CartItemResponse.java
│   │   │   │   ├── ProductRequest.java
│   │   │   │   ├── ProductResponse.java
│   │   │   │   ├── UserRequest.java
│   │   │   │   └── UserResponse.java
│   │   │   ├── Entities/
│   │   │   │   ├── Address.java
│   │   │   │   ├── CartItem.java
│   │   │   │   ├── Product.java
│   │   │   │   ├── User.java
│   │   │   │   └── UserRole.java
│   │   │   ├── Repository/
│   │   │   │   ├── CartItemRepository.java
│   │   │   │   ├── ProductRepository.java
│   │   │   │   └── UserRepository.java
│   │   │   ├── Services/
│   │   │   │   ├── CartService.java
│   │   │   │   ├── ProductService.java
│   │   │   │   └── UserService.java
│   │   │   └── DemoApplication.java
│   │   └── resources/
│   │       └── application.yml
│   └── test/
├── pom.xml
└── README.md
```

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- Git

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/DivyanshuSingh007/SpringBoot1.git
   cd SpringBoot1/demo
   ```

2. **Build the project**
   ```bash
   ./mvnw clean install
   ```
   
   Or on Windows:
   ```bash
   mvnw.cmd clean install
   ```

3. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```
   
   Or on Windows:
   ```bash
   mvnw.cmd spring-boot:run
   ```

The application will start on `http://localhost:9090`

## 📡 API Endpoints

### User Endpoints (`/demo`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/demo/` | Get all users |
| GET | `/demo/{id}` | Get user by ID |
| POST | `/demo/` | Create a new user |
| PUT | `/demo/{id}` | Update user by ID |
| DELETE | `/demo/{id}` | Delete user by ID |

**Sample User Request Body:**
```json
{
  "username": "john_doe",
  "password": "password123",
  "firstName": "John",
  "lastName": "Doe",
  "address": {
    "street": "123 Main St",
    "city": "New York",
    "state": "NY",
    "zip": "10001",
    "country": "USA"
  }
}
```

### Product Endpoints (`/product`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/product/` | Get all products |
| GET | `/product/{id}` | Get product by ID |
| POST | `/product/` | Create a new product |
| PUT | `/product/{id}` | Update product by ID |
| DELETE | `/product/{id}` | Delete product by ID |

**Sample Product Request Body:**
```json
{
  "name": "Laptop",
  "description": "High-performance laptop",
  "price": 999.99,
  "stockQuantity": 50,
  "imageUrl": "https://example.com/laptop.jpg",
  "category": "Electronics",
  "active": true
}
```

### Cart Endpoints (`/mycart`)

| Method | Endpoint | Description | Headers Required |
|--------|----------|-------------|------------------|
| GET | `/mycart` | Get all cart items for user | X-User-ID |
| GET | `/mycart/{product_id}` | Get specific cart item | X-User-ID |
| POST | `/mycart` | Add item to cart | X-User-ID |
| POST | `/mycart/remove` | Remove/decrease quantity | X-User-ID |
| DELETE | `/mycart/delete/{product_id}` | Delete item from cart | X-User-ID |

**Sample Cart Item Request Body:**
```json
{
  "productId": 1,
  "quantity": 2
}
```

**Note:** All cart operations require the `X-User-ID` header to identify the user.

## 🗄️ Database Configuration

The application uses an **H2 in-memory database** configured in `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:test
  h2:
    console:
      enabled: true
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: update
```

### Accessing H2 Console

1. Navigate to: `http://localhost:9090/h2-console`
2. Use the following settings:
   - **JDBC URL**: `jdbc:h2:mem:test`
   - **Username**: `sa`
   - **Password**: *(leave empty)*

### Database Tables

The application creates the following tables:
- `user_table` - User information and credentials
- `address` - User addresses (one-to-one with users)
- `product_table` - Product catalog
- `cart_table` - Shopping cart items (many-to-one with users and products)

## 🔧 Building and Running

### Development Mode

```bash
./mvnw spring-boot:run
```

### Production Build

```bash
./mvnw clean package
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

### Running Tests

```bash
./mvnw test
```

## 📝 Configuration

The application can be configured via `src/main/resources/application.yml`:

- **Server Port**: Default is `9090`
- **Database**: H2 in-memory database
- **JPA Settings**: SQL logging enabled, auto-update schema
- **H2 Console**: Enabled for development

## 🏗️ Architecture

The application follows a layered architecture:

1. **Controller Layer**: Handles HTTP requests and responses
2. **Service Layer**: Contains business logic
3. **Repository Layer**: Data access using Spring Data JPA
4. **Entity Layer**: JPA entities representing database tables
5. **DTO Layer**: Data Transfer Objects for API requests/responses

### Key Design Patterns

- **Dependency Injection**: Using Spring's `@RequiredArgsConstructor` with Lombok
- **Repository Pattern**: Spring Data JPA repositories
- **DTO Pattern**: Separation of entity and API models
- **RESTful API**: Standard REST conventions

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This is a demo project for learning purposes.

## 👤 Author

**Divyanshu Singh**
- GitHub: [@DivyanshuSingh007](https://github.com/DivyanshuSingh007)

## 🐛 Known Issues & Future Enhancements

- Add authentication and authorization (Spring Security)
- Implement JWT token-based authentication
- Add input validation
- Implement pagination for list endpoints
- Add order management functionality
- Switch to persistent database (PostgreSQL/MySQL)
- Add comprehensive unit and integration tests
- Implement exception handling with custom error responses
- Add API documentation with Swagger/OpenAPI

## 📞 Support

For issues and questions, please open an issue on the GitHub repository.

---

**Happy Coding! 🚀**
