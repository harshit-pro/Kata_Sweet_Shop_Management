# 🍬 Kata Sweet Shop
A Spring Boot-based application for managing a sweet shop's inventory. This project demonstrates a clean backend design, Test-Driven Development (TDD), and integration with Cloudinary for image uploads.

-----

## 📖 Project Overview

This is a demo inventory management system with the following features:

  * **Add sweets:** Include details like name, category, price, quantity, and an image.
  * **Image Uploads:** Images are uploaded via multipart requests and stored in Cloudinary.
  * **Purchase sweets:** With stock validation to ensure availability.
  * **Error Handling:** Meaningful error handling for insufficient stock or missing fields.

The project is built to showcase the Red → Green → Refactor cycles of TDD, clean coding practices, and AI-assisted development.

-----

## ⚙️ Tech Stack

  * **Backend:** Spring Boot (Java 21)
  * **Database:** PostgreSQL
  * **ORM:** Hibernate / JPA
  * **Image Uploads:** Cloudinary
  * **Build Tool:** Maven
  * **Testing:** JUnit 5, Mockito, Spring Boot Test, Testcontainers

-----

## 🚀 Getting Started

### 1\. Clone Repository

```bash
git clone https://github.com/<your-username>/kata-sweet-shop.git
cd kata-sweet-shop
```

### 2\. Configure Environment

Set up a `application.properties` file with your database and Cloudinary credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/sweetshop
spring.datasource.username=postgres
spring.datasource.password=postgres

cloudinary.cloud-name=your-cloud-name
cloudinary.api-key=your-api-key
cloudinary.api-secret=your-api-secret
```

### 3\. Run the Application

```bash
./mvnw spring-boot:run
```

### 4\. Run Tests

```bash
./mvnw test
```

-----

## 🧪 Test Report

The project follows TDD principles with unit and integration tests. Example test cases include:

  * ✅ Purchasing sweets reduces stock.
  * ✅ Purchasing with insufficient stock throws `InsufficientStockException`.
  * ✅ Creating sweets with image URLs works.
  * ✅ Creating sweets without required fields fails validation.
  * ✅ Multipart image upload endpoint works with mocked Cloudinary.

Run the test suite with:

```bash
./mvnw test
```

-----

## 📸 API Endpoints

### Sweets

  * **GET /api/sweets/all:** Get all sweets.
  * **GET /api/sweets/search:** Search for sweets by name, category, or price range.
  * **POST /api/sweets/add:** Add a new sweet (Admin only).
  * **PUT /api/sweets/update/{id}:** Update a sweet (Admin only).
  * **DELETE /api/sweets/delete/{id}:** Delete a sweet (Admin only).
  * **POST /api/sweets/purchase/{id}:** Purchase a sweet (User or Admin).
  * **POST /api/sweets/restock/{id}:** Restock a sweet (Admin only).

### Authentication

  * **POST /api/auth/register:** Register a new user.
  * **POST /api/auth/login:** Login to get a JWT token.

-----

## 🤖 My AI Usage

I used AI tools responsibly during development.

### Tools Used

  * **ChatGPT:** Helped with TDD workflow (writing Red-Green-Refactor commit messages, generating validation logic, debugging test failures).
  * **GitHub Copilot:** Assisted in generating boilerplate code (DTOs, repository interfaces, test scaffolding).
  * **Gemini:** Brainstormed API endpoint structures and helped refine database schema design.

### How I Used Them

  * I used ChatGPT to draft commit messages in the TDD cycle (Red-Green-Refactor), and for explanations when test failures occurred.
  * I used Copilot inside IntelliJ for writing repetitive code (entity mappings, getter/setters, and mock setup in tests).
  * I used Gemini early on to outline endpoints and service responsibilities before implementation.

### Reflection
📸 Screenshots

<img width="1466" height="823" alt="image" src="https://github.com/user-attachments/assets/813041a7-ea9a-473e-a4c8-cee0803ba165" />
<img width="1470" height="824" alt="image" src="https://github.com/user-attachments/assets/beaf39ed-1c2c-48dd-8f23-e0371505c86f" />
<img width="1442" height="729" alt="image" src="https://github.com/user-attachments/assets/6ed81ea7-0abf-463d-8100-b8b2191d3c66" />
<img width="1461" height="856" alt="image" src="https://github.com/user-attachments/assets/ff1579b6-cfe3-47ca-98a6-3f81b7bc9651" />
<img width="1438" height="769" alt="image" src="https://github.com/user-attachments/assets/988309c2-ad6f-40ff-b7cc-f329044088de" />
<img width="773" height="860" alt="image" src="https://github.com/user-attachments/assets/3656cdbb-b79a-43f0-bc84-503d1eba7f6b" />
<img width="1023" height="849" alt="image" src="https://github.com/user-attachments/assets/7f23b541-ea29-461b-9624-19d373fe596f" />
<img width="1105" height="561" alt="image" src="https://github.com/user-attachments/assets/fa5847f1-27dd-496e-8d72-2a62c8ed6887" />
<img width="801" height="643" alt="image" src="https://github.com/user-attachments/assets/adfa66e3-9b71-4da1-94ae-670cc27178ad" />
<img width="1468" height="676" alt="image" src="https://github.com/user-attachments/assets/724ca6d0-ce94-4a7b-b18c-68f9b5e05708" />
<img width="1452" height="848" alt="image" src="https://github.com/user-attachments/assets/beb4a22d-db0b-4ac3-946a-9fd6368043f7" />


AI tools significantly improved my workflow by:

  * Reducing time spent on boilerplate and setup.
  * Helping me debug faster with clear explanations.
  * Encouraging better discipline with TDD by structuring tests before writing logic.

However, I carefully reviewed every suggestion, ensuring it aligned with clean coding practices and project requirements. AI served as a pair programmer, not a replacement for my reasoning.

-----

## 📝 License

This project is open-source under the MIT License.
