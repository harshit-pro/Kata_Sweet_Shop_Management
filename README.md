# 🍬 Sweet Shop: Full-Stack Inventory Management System

Welcome to Sweet Shop, a full-stack inventory management system designed for confectionery businesses. This application provides a professional, feature-rich interface for both customers and administrators, built with a modern tech stack. The project includes a Spring Boot backend that handles business logic and a React frontend for a seamless user experience.

-----
<img width="1360" height="204" alt="image" src="https://github.com/user-attachments/assets/b952f74b-5375-4a3d-b630-1396fe918caa" />

## 📖 Project Overview

This is a comprehensive inventory management system with the following features:

  * **For Customers:**
      * **User Authentication:** Secure login and registration.
      * **Interactive Dashboard:** Browse, search, and filter a wide variety of sweets.
      * **Easy Shopping:** Purchase your favorite sweets with ease.
  * **For Administrators:**
      * **Admin Panel:** A comprehensive dashboard to perform CRUD (Create, Read, Update, Delete) operations on the sweets inventory.
      * **Image Uploads:** Seamless image uploading for sweets, integrated with a backend service.
  * **General:**
      * **Modern UI/UX:** A beautiful and intuitive interface built with **React**, **TypeScript**, and **shadcn/ui**.
      * **Responsive Design:** Fully responsive layout that works on desktops, tablets, and mobile devices.
      * **State Management:** Efficient state management using **React Query** for server-state and React Context for global UI state.

-----

## ⚙️ Tech Stack

### Backend

  * **Framework:** Spring Boot (Java 21)
  * **Database:** PostgreSQL
  * **ORM:** Hibernate / JPA
  * **Authentication:** JWT (JSON Web Tokens)
  * **Image Uploads:** Cloudinary
  * **Build Tool:** Maven
  * **Testing:** JUnit 5, Mockito, Testcontainers

### Frontend

  * **Framework:** React (TypeScript), Vite
  * **Styling:** Tailwind CSS, shadcn/ui
  * **State Management:** TanStack Query (React Query)
  * **Routing:** React Router
  * **API Communication:** Axios

-----

## 🚀 Getting Started

To get a local copy up and running, follow these simple steps for both the backend and frontend components.

### Backend Setup (`kata_sweet_shop_management`)

1.  **Prerequisites:**

      * Java 21 or later
      * Maven
      * Docker

2.  **Clone the repository:**

    ```sh
    git clone https://github.com/harshit-pro/kata_sweet_shop_management.git
    cd kata_sweet_shop_management
    ```

3.  **Configure Environment:**

    The project uses a `docker-compose.yml` file to set up a PostgreSQL database. The default credentials are in `src/main/resources/application.properties`.

    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5433/sweetshop
    spring.datasource.username=postgres
    spring.datasource.password=12345
    ```

4.  **Run the Database:**

    ```sh
    docker-compose up -d
    ```

5.  **Run the Application:**

    ```sh
    ./mvnw spring-boot:run
    ```

    The backend server will start on `http://localhost:8086`.

### Frontend Setup (`treats-inventory-manager`)

1.  **Prerequisites:**

      * [Node.js](https://nodejs.org/) (v18 or later recommended)
      * [npm](https://www.npmjs.com/) or any other package manager.

2.  **Clone the repository:**

    ```sh
    git clone https://github.com/harshit-pro/treats-inventory-manager.git
    cd treats-inventory-manager
    ```

3.  **Install NPM packages:**

    ```sh
    npm install
    ```

4.  **Configure Environment Variables:**

    Create a `.env` file in the root of the project and add your Cloudinary credentials.

    ```env
    VITE_CLOUDINARY_CLOUD_NAME=your_cloudinary_cloud_name
    VITE_CLOUDINARY_UPLOAD_PRESET=your_cloudinary_upload_preset
    ```

5.  **Run the development server:**

    ```sh
    npm run dev
    ```

    The application will be available at `http://localhost:5175`.

-----

## 📸 Application Screenshots
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
-----

## 🤖 My AI Usage

I utilized various AI tools to enhance my development workflow and improve the quality of the project.

### Tools Used

  * **GitHub Copilot:** For autocompleting code, generating boilerplate, and suggesting implementations for functions.
  * **ChatGPT:** To brainstorm component structures, debug complex issues, and generate documentation.
  * **Gemini:** For refining API logic, improving user experience, and creating professional documentation like this README.

### How I Used Them

  * I used **GitHub Copilot** extensively within my IDE to speed up the creation of React components, utility functions, and API service files. It was particularly helpful for writing repetitive code like form handling and state management logic.
  * I turned to **ChatGPT** when I encountered challenging bugs or needed to explore different approaches for implementing features like filtering and searching on the dashboard. It also helped in writing clear and concise error messages for the UI.
  * I leveraged **Gemini** to structure the overall project, design the API interactions in `src/lib/api.ts`, and generate a polished and professional `README.md` file that effectively communicates the project's features and setup.

### Reflection

AI tools were instrumental in accelerating the development process and allowing me to focus more on high-level architecture and user experience. They served as a powerful pair-programming partner, offering suggestions and solutions that I could adapt and integrate into the project. While AI provided a significant boost in productivity, I ensured that all generated code was thoroughly reviewed and tested to meet the project's quality standards.

-----

## 🧪 Test Report

The backend of this project follows TDD principles with a comprehensive suite of unit and integration tests.

### Running Tests

To run the test suite for the backend, use the following command:

```sh
./mvnw test
```

### Test Coverage

The test suite includes:

  * **Unit Tests:** These tests cover individual components in isolation.
      * `SweetServiceTest.java`: Verifies the business logic for managing sweets, such as purchasing, stock validation, and creation.
      * `UserServiceTest.java`: Ensures the user registration logic, including validation for existing users and missing fields, works correctly.
  * **Integration Tests:** These tests cover the interaction between different parts of the application.
      * `SweetIntegrationTest.java`: Tests the full flow of creating and retrieving sweets, including multipart image uploads with a mocked Cloudinary service.

### Results

All tests are designed to pass in a CI/CD environment, ensuring the reliability and stability of the backend application. The tests cover critical paths and business logic, providing confidence in the correctness of the implementation.
