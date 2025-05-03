# Java Chat Application

A full-stack chat application built using **Spring Boot** for the backend and **React.js** for the frontend. It supports real-time messaging using **WebSocket** and is styled with **Tailwind CSS**.

---

## 🚀 Features

- 🔒 Create and Join Chat Rooms
- 💬 Real-time Messaging (WebSocket)
- 📦 Room and Message Persistence (via Repositories)
- 🎨 Modern UI with Tailwind CSS

---

## 🏗️ Tech Stack

**Backend:**
- Java 17
- Spring Boot
- Spring WebSocket
- Spring Data JPA (or MongoDB based on your setup)

**Frontend:**
- React.js (with Vite)
- Axios for API calls
- Tailwind CSS

---

## ⚙️ Setup Instructions

### 1️⃣ Backend Setup

```bash
# Navigate to the backend folder
cd Chat-App

# Build the project (using Maven)
mvn clean install

# Run the application
mvn spring-boot:run
```

The backend will start (default port: `8080`).

### 2️⃣ Frontend Setup

```bash
# Navigate to the frontend folder
cd front-chat

# Install dependencies
npm install

# Run the app
npm run dev
```

Frontend will be available at `http://localhost:5173`

---

## 🗂️ Project Structure

**Backend:**
- `controllers/`: Handles HTTP/WebSocket requests
- `services/`: Business logic
- `entities/`: Message & Room models
- `repositories/`: Database access
- `config/`: WebSocket & app configs

**Frontend:**
- `components/`: Chat UI components
- `config/`: Axios helpers & routes
- `context/`: Chat context management

---

## 🔗 API Endpoints (Sample)

- `POST /rooms`: Create a new room
- `GET /rooms`: List all rooms
- WebSocket endpoint: `/chatroom`

---

## 🤝 Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

---

## 📝 License

This project is licensed under the MIT License.
