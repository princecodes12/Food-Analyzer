# Food-Analyzer
Food Analyzer is a Spring Boot application that uses Gemini Flash 2.0 API to analyze food images. It detects food items, estimates their weight, and calculates both individual and total calorie content. This tool helps users gain quick nutritional insights from a simple image upload.


# 🍽️ Food Analyzer

**Food Analyzer** is a Spring Boot-based REST API that uses Google's **Gemini Flash 2.0 Vision API** to analyze food images and provide nutritional insights.

---

## 📌 Description

Food Analyzer is a Spring Boot application that integrates with Gemini Flash 2.0 API to intelligently detect food items from an uploaded image. It returns a structured JSON response containing the food names, estimated weights in grams, calories for each item, and total calorie count. This application is ideal for diet tracking, fitness apps, and health-conscious users who want instant nutrition analysis from images.

---

## 🚀 Features

- Upload food images via REST API
- Analyze and identify multiple food items
- Estimate food weight in grams
- Calculate individual and total calories
- Get structured JSON output

---

## 🛠️ Tech Stack

- Java 17+
- Spring Boot
- Gemini Flash 2.0 Vision API (Google Generative Language API)
- OkHttp for API requests
- Maven

---
 Setup Instructions
Clone the repository

bash
Copy
Edit
git clone https://github.com/your-username/food-analyzer.git
cd food-analyzer
Add your Gemini API Key
Open FoodService.java and replace the placeholder API key:

java
Copy
Edit
private static final String GEMINI_API_KEY = "YOUR_API_KEY_HERE";
Build and Run the Application

bash
Copy
Edit
mvn clean install
mvn spring-boot:run
Test with Postman

Method: POST

URL: http://localhost:8080/api/food/analyze

Body: Form-data → Key: image, Value: (Upload your image)

🧠 Notes
Ensure your Gemini API key has access to the Vision model (gemini-2.0-flash).

You can enhance accuracy by preprocessing images or integrating with calorie APIs like CalorieNinjas.

## 📷 API Endpoint

### `POST /api/food/analyze`

**Request**:  
- `multipart/form-data` with a field named `image` containing the food image

**Response (JSON)**:
```json
{
  "items": [
    { "name": "apple", "weight_in_grams": 150, "calories": 78 },
    { "name": "pizza", "weight_in_grams": 200, "calories": 540 }
  ],
  "total_calories": 618
}

  
