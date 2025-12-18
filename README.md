# 💬 Real-Time Chat Application

A modern, full-stack chat application featuring real-time messaging capabilities. Built with **Spring Boot** (backend) and **React** (frontend), leveraging **WebSocket** technology for instant communication and **MongoDB** for data persistence.

![Java](https://img.shields.io/badge/Java-21-orange?style=flat&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.0-brightgreen?style=flat&logo=spring)
![React](https://img.shields.io/badge/React-18-blue?style=flat&logo=react)
![MongoDB](https://img.shields.io/badge/MongoDB-5.0-green?style=flat&logo=mongodb)
![Tailwind CSS](https://img.shields.io/badge/Tailwind%20CSS-3.4-38bdf8?style=flat&logo=tailwindcss)

---

## ✨ Features

- 🚪 **Room Management** - Create new chat rooms or join existing ones with unique room IDs
- 💬 **Real-Time Messaging** - Instant message delivery using WebSocket (STOMP protocol)
- 💾 **Message Persistence** - All messages stored in MongoDB with pagination support
- 🗑️ **Message Deletion** - Users can delete their own messages
- 👤 **User Identification** - Username-based chat participation
- 🌙 **Dark Mode UI** - Modern, responsive interface with Tailwind CSS
- 🔄 **Auto-Reconnection** - Automatic WebSocket reconnection with retry logic
- 📱 **Responsive Design** - Works seamlessly across desktop and mobile devices
- 🐳 **Docker Support** - Containerized deployment ready

---

## 🏗️ Tech Stack

### Backend
| Technology | Purpose |
|------------|---------|
| Java 21 | Programming language |
| Spring Boot 3.4.0 | Application framework |
| Spring WebSocket | Real-time communication |
| Spring Data MongoDB | Database integration |
| Lombok | Boilerplate code reduction |
| Maven | Dependency management |

### Frontend
| Technology | Purpose |
|------------|---------|
| React 18 | UI framework |
| Vite | Build tool & dev server |
| Tailwind CSS | Styling framework |
| STOMP.js | WebSocket client |
| SockJS | WebSocket fallback |
| Axios | HTTP client |
| React Router | Navigation |
| React Hot Toast | Notifications |

### Database & Deployment
- **MongoDB 5.0** - NoSQL database
- **Docker & Docker Compose** - Containerization
- **Railway** - Production deployment

---

## 🚀 Getting Started

### Prerequisites

- **Java 21** or higher
- **Node.js 18+** and npm
- **MongoDB 5.0+** (or use Docker)
- **Maven 3.6+**

### 1️⃣ Clone the Repository

```bash
git clone <repository-url>
cd <project-directory>
```

### 2️⃣ Backend Setup

#### Option A: Local Development

```bash
# Navigate to backend directory
cd Chat-App

# Ensure MongoDB is running locally on port 27017
# Or start it with Docker:
docker run -d -p 27017:27017 --name mongodb mongo:5.0

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The backend will start on `http://localhost:8080`

#### Option B: Docker Compose

```bash
cd Chat-App
docker-compose up -d
```

This will start both MongoDB and the Spring Boot application.

### 3️⃣ Frontend Setup

```bash
# Navigate to frontend directory
cd front-chat

# Install dependencies
npm install

# Start development server
npm run dev
```

The frontend will be available at `http://localhost:5173`

---

## 📁 Project Structure

```
.
├── Chat-App/                          # Backend (Spring Boot)
│   ├── src/main/java/com/chat_application/
│   │   ├── config/                    # Configuration classes
│   │   │   ├── WebSocketConfig.java   # WebSocket configuration
│   │   │   ├── WebConfig.java         # CORS configuration
│   │   │   └── AppConstants.java      # Application constants
│   │   ├── controllers/               # REST & WebSocket controllers
│   │   │   ├── roomControl.java       # Room management endpoints
│   │   │   └── chatControl.java       # WebSocket message handler
│   │   ├── entities/                  # Domain models
│   │   │   ├── Room.java              # Room entity
│   │   │   └── Message.java           # Message entity
│   │   ├── repositories/              # MongoDB repositories
│   │   │   ├── roomRepo.java
│   │   │   └── messageRepo.java
│   │   ├── Service/                   # Business logic
│   │   │   ├── roomService.java
│   │   │   └── impl/RoomServiceImpl.java
│   │   └── playload/                  # DTOs
│   │       └── MessageRequest.java
│   ├── src/main/resources/
│   │   ├── application.properties
│   │   ├── application-dev.properties
│   │   └── application-prod.properties
│   ├── Dockerfile
│   ├── docker-compose.yml
│   └── pom.xml
│
└── front-chat/                        # Frontend (React)
    ├── src/
    │   ├── components/                # React components
    │   │   ├── JoinCreateChat.jsx     # Landing page
    │   │   └── ChatPage.jsx           # Main chat interface
    │   ├── config/                    # Configuration
    │   │   ├── AxiosHelper.js         # Axios instance
    │   │   ├── helper.js              # Utility functions
    │   │   └── routes.jsx             # Route definitions
    │   ├── context/                   # State management
    │   │   └── ChatContext.jsx        # Chat context provider
    │   ├── service/                   # API services
    │   │   └── RoomService.js         # Room API calls
    │   ├── App.jsx
    │   └── main.jsx
    ├── index.html
    ├── package.json
    ├── vite.config.js
    └── tailwind.config.js
```

---

## 🔌 API Documentation

### REST Endpoints

#### Create Room
```http
POST /api/v1/rooms
Content-Type: text/plain

roomId123
```

**Response:**
```json
{
  "id": "507f1f77bcf86cd799439011",
  "roomId": "roomId123",
  "message": []
}
```

#### Join Room
```http
GET /api/v1/rooms/{roomId}
```

**Response:**
```json
{
  "id": "507f1f77bcf86cd799439011",
  "roomId": "roomId123",
  "message": [...]
}
```

#### Get Messages (Paginated)
```http
GET /api/v1/rooms/{roomId}/messages?page=0&size=20
```

**Response:**
```json
[
  {
    "messageId": "msg-uuid",
    "sender": "John",
    "content": "Hello!",
    "timeStamp": "2024-12-18T10:30:00"
  }
]
```

#### Delete Message
```http
DELETE /api/v1/rooms/{roomId}/messages/{messageId}
```

### WebSocket Endpoints

**Connection:** `/chat` (with SockJS fallback)

**Subscribe to room messages:**
```
/topic/room/{roomId}
```

**Send message:**
```
/app/sendMessage/{roomId}
```

**Message payload:**
```json
{
  "sender": "username",
  "content": "message text",
  "roomId": "roomId123"
}
```

---

## 🔧 Configuration

### Backend Configuration

Edit `Chat-App/src/main/resources/application-dev.properties`:

```properties
spring.data.mongodb.uri=mongodb://localhost:27017/chatapp
server.port=8080
```

### Frontend Configuration

Edit `front-chat/src/config/AxiosHelper.js`:

```javascript
export const baseURL = "http://localhost:8080";
```

---

## 🐳 Docker Deployment

### Build and Run with Docker Compose

```bash
cd Chat-App
docker-compose up --build
```

This will:
- Start MongoDB on port 27017
- Build and run the Spring Boot application
- Create a shared network for communication

### Manual Docker Build

```bash
# Build the JAR file
cd Chat-App
mvn clean package

# Build Docker image
docker build -t chat-app-backend .

# Run container
docker run -p 8080:8080 \
  -e SPRING_DATA_MONGODB_URI=mongodb://host.docker.internal:27017/chatapp \
  chat-app-backend
```

---

## 🎯 Usage Guide

1. **Start the Application**
   - Ensure backend is running on port 8080
   - Ensure frontend is running on port 5173

2. **Create or Join a Room**
   - Enter your username
   - Enter a room ID (create new or join existing)
   - Click "Create Room" or "Join Room"

3. **Start Chatting**
   - Type your message in the input field
   - Press Enter or click the send button
   - Messages appear in real-time for all room participants

4. **Delete Messages**
   - Click the delete icon next to your own messages
   - Messages are removed for all participants

5. **Leave Room**
   - Click "Leave Room" button to disconnect

---

## 🛠️ Development

### Run Tests

```bash
# Backend tests
cd Chat-App
mvn test

# Frontend tests (if configured)
cd front-chat
npm test
```

### Build for Production

```bash
# Backend
cd Chat-App
mvn clean package

# Frontend
cd front-chat
npm run build
```

---

## 🐛 Troubleshooting

### WebSocket Connection Issues
- Ensure backend is running and accessible
- Check CORS configuration in `WebConfig.java`
- Verify `AppConstants.FRONT_END_URL` matches your frontend URL

### MongoDB Connection Failed
- Verify MongoDB is running: `docker ps` or check local service
- Check connection string in `application-dev.properties`
- Ensure port 27017 is not blocked

### Frontend Build Errors
- Clear node_modules: `rm -rf node_modules && npm install`
- Check Node.js version: `node --version` (should be 18+)

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

## 👨‍💻 Author

Created with ❤️ by [Your Name]

---

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- React community for the amazing ecosystem
- MongoDB for reliable data persistence
- Tailwind CSS for beautiful styling utilities
