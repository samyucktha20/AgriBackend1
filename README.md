# Krishimitra - AI-powered Agricultural Assistant

## Overview
Krishimitra is an AI-powered personal assistant for small-scale farmers, providing:
- Instant crop disease diagnosis through image analysis
- Real-time market price analysis and trends
- Government scheme navigation and information
- Voice-first interaction in local languages 

## Architecture

### Backend Services
1. **Spring Boot API with Gradle** (`backend/spring-boot/`)
   - Main REST API for farmers, diagnoses, and market data
   - H2 database for development (will use Cloud SQL in production)
   - Modular service architecture
   - Firebase integration for real-time updates
   - Notification system for alerts

2. **Python AI Service** (`backend/python-ai-service/`)
   - Image analysis for crop disease detection
   - Voice processing (speech-to-text and text-to-speech)
   - Market trend analysis
   - Currently uses mock AI responses, will integrate Vertex AI during hackathon

## Pre-Hackathon Setup

### What You Can Prepare Now:
1. **Backend Structure**: Complete Spring Boot application with:
   - Database models and repositories
   - Service layer with mock responses
   - REST API endpoints
   - H2 database for development

2. **Python AI Service**: Flask service with:
   - Mock image analysis
   - Mock voice processing
   - API endpoints ready for Vertex AI integration

3. **Database Schema**: Complete entity models for:
   - Farmers
   - Crop Diagnoses
   - Market Prices
   - Historical data tracking

### During Hackathon (with GCP Credits):
Replace mock services with:
- **Vertex AI Gemini** for image analysis and text generation
- **Vertex AI Speech-to-Text** for voice commands
- **Vertex AI Text-to-Speech** for voice responses
- **Cloud SQL** for production database
- **Firebase** for real-time features
- **Cloud Storage** for image storage

## Quick Start

### Prerequisites
- Java 17+
- Gradle 8.5+ (included via wrapper)
- Python 3.9+
- Node.js 18+ (for frontend)

### Running the Backend

1. **Start Spring Boot API with Gradle**:
```bash
cd backend/spring-boot
./gradlew bootRun
```

2. **Start Python AI Service**:
```bash
cd backend/python-ai-service
pip install -r requirements.txt
python app.py
```

### API Endpoints

#### Crop Diagnosis
- `POST /api/diagnosis/diagnose` - Analyze crop disease from image
- `GET /api/diagnosis/history/{farmerId}` - Get diagnosis history
- `POST /api/diagnosis/voice-diagnose` - Voice-based diagnosis

#### Market Prices
- `GET /api/market/price/{cropName}` - Get current market price
- `GET /api/market/trend/{cropName}` - Get market trend analysis
- `GET /api/market/history/{cropName}` - Get price history

#### Farmers
- `POST /api/farmers/register` - Register new farmer
- `GET /api/farmers/{id}` - Get farmer details
- `PUT /api/farmers/{id}` - Update farmer information

#### AI Services (Python)
- `POST /api/ai/analyze-crop` - Crop image analysis
- `POST /api/ai/voice-to-text` - Convert voice to text
- `POST /api/ai/text-to-voice` - Convert text to voice
- `POST /api/ai/analyze-market` - Market trend analysis

#### Realtime Features (Firebase)
- `GET /api/realtime/market-price/{cropName}` - Get real-time market price
- `POST /api/realtime/subscribe-market/{cropName}` - Subscribe to market updates
- `POST /api/realtime/send-alert` - Send custom alerts
- `GET /api/realtime/health` - Check Firebase connectivity

## Technology Stack

### Current (Pre-Hackathon)
- **Backend**: Spring Boot 3.2 with Gradle, H2 Database
- **Real-time**: Firebase Realtime Database
- **Notifications**: Firebase Cloud Messaging (mock)
- **AI Service**: Python Flask with mock responses
- **Database**: H2 (in-memory for development)
- **Documentation**: OpenAPI/Swagger

### Hackathon Integration (with GCP)
- **AI/ML**: Vertex AI (Gemini, Speech-to-Text, Text-to-Speech)
- **Database**: Cloud SQL (PostgreSQL)
- **Storage**: Cloud Storage for images
- **Real-time**: Firebase for live updates
- **Deployment**: Google Cloud Run
- **Frontend**: React (mobile-responsive) or Flutter

## Database Schema

### Farmers Table
- Personal information (name, phone, email)
- Location and farm details
- Preferred language
- Registration timestamp

### Crop Diagnoses Table
- Farmer reference
- Crop type and symptoms
- AI analysis results
- Recommendations and treatment steps
- Image storage path
- Confidence scores

### Market Prices Table
- Crop name and market location
- Price ranges (min, max, modal)
- Arrival dates and timestamps
- Historical tracking

## Sample API Calls

### Diagnose Crop Disease
```bash
curl -X POST http://localhost:8080/api/diagnosis/diagnose \
  -H "Content-Type: application/json" \
  -d '{
    "farmerId": 1,
    "cropType": "tomato",
    "symptoms": "Yellow spots on leaves",
    "imageBase64": "base64_encoded_image_data"
  }'
```

### Get Market Price
```bash
curl "http://localhost:8080/api/market/price/tomato?state=Karnataka&district=Bangalore"
```

### Register Farmer
```bash
curl -X POST http://localhost:8080/api/farmers/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Rohan Kumar",
    "email": "rohan@example.com",
    "phoneNumber": "+919876543210",
    "preferredLanguage": "kannada",
    "location": "Bangalore Rural",
    "farmSize": 2.5,
    "primaryCrops": "tomato,onion"
  }'
```

### Build and Run
```bash
# Build the project
cd backend/spring-boot
./gradlew build

# Run the application
./gradlew bootRun

# Run tests
./gradlew test
```

### Firebase Setup (for hackathon)
1. Create Firebase project
2. Download service account JSON
3. Place it as `firebase-service-account.json` in `src/main/resources/`
4. Update `application.yml` with your Firebase database URL

## Development Notes

1. **Mock Data**: All AI responses and Firebase are currently mocked for development
2. **Firebase**: Configured but will work with actual credentials during hackathon
2. **Database**: Using H2 in-memory database for easy development
3. **CORS**: Enabled for frontend development
4. **Logging**: Configured for debugging
5. **Error Handling**: Basic error handling implemented
6. **Validation**: Input validation using Bean Validation

## Next Steps for Hackathon

1. **GCP Integration**:
   - Set up Vertex AI project
   - Configure authentication
   - Replace mock AI services

2. **Frontend Development**:
   - React mobile-responsive web app OR
   - Flutter mobile app
   - Voice interaction UI
   - Camera integration

3. **Firebase Integration**:
   - Real-time market updates
   - Push notifications
   - User authentication

4. **Production Deployment**:
   - Cloud Run deployment
   - Cloud SQL database
   - Environment configuration

## Team Roles Suggestion

- **Backend Developer**: Spring Boot API refinement, GCP integration
- **AI/ML Developer**: Vertex AI integration, model optimization
- **Frontend Developer**: React/Flutter app with voice/camera features
- **DevOps**: GCP deployment, Firebase setup, CI/CD

Good luck with the hackathon! 🚀