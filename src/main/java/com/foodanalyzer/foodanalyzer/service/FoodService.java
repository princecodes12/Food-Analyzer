package com.foodanalyzer.foodanalyzer.service;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
public class FoodService {

    private static final String GEMINI_API_KEY = "AIzaSyAtMWJy-epif81wWhdFb5mGk2ymTGmrPzI";
    private final OkHttpClient client = new OkHttpClient();

    public String analyzeImage(MultipartFile image) {
        try {
            String base64Image = Base64.getEncoder().encodeToString(image.getBytes());

            // Call Gemini
            String geminiResponse = callGemini(base64Image);
            System.out.println("Gemini API Raw Response: " + geminiResponse);

            if (geminiResponse == null || geminiResponse.isEmpty()) {
                return "[]";
            }

            String jsonArray = extractJsonObject(geminiResponse);
            System.out.println("Food Information: " + jsonArray);

            return jsonArray;

        } catch (Exception e) {
            e.printStackTrace();
            return "[]";
        }
    }

    private String callGemini(String base64Image) throws IOException {
        JSONObject payload = new JSONObject()
                .put("contents", new JSONArray().put(new JSONObject()
                        .put("parts", new JSONArray()
                                .put(new JSONObject().put("text", "Analyze the uploaded food image and return a valid JSON array. " +
                                                "Each array item must include: 'name' (food name), 'weight_in_grams', and 'calories'. " +
                                                "Also include a field 'total_calories' at the end of the JSON to represent the sum of all item calories. " +
                                                "The response must be ONLY a valid JSON object. " +
                                                "Do not add any explanation, markdown, or formatting. " +
                                                "Example format: { \"items\": [ {\"name\": \"apple\", \"weight_in_grams\": 150, \"calories\": 78}, {\"name\": \"pizza\", \"weight_in_grams\": 200, \"calories\": 540} ], \"total_calories\": 618 }"))

                                        .put(new JSONObject().put("inlineData", new JSONObject()
                                        .put("mimeType", "image/jpeg")
                                        .put("data", base64Image)
                                ))
                        )
                ));

        Request request = new Request.Builder()
                .url("https://generativelanguage.googleapis.com/v1/models/gemini-2.0-flash:generateContent?key="
                        + GEMINI_API_KEY)
                .post(RequestBody.create(payload.toString(), MediaType.parse("application/json")))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Gemini API error: " + response.code() + " - " + response.message());
            }

            return response.body().string();
        }
    }

    private String extractJsonObject(String geminiResponse) {
        JSONObject json = new JSONObject(geminiResponse);
        JSONArray candidates = json.getJSONArray("candidates");

        if (candidates.length() == 0) {
            return "{}";
        }

        String text = candidates.getJSONObject(0)
                .getJSONObject("content")
                .getJSONArray("parts")
                .getJSONObject(0)
                .getString("text");

        // Clean markdown & extra formatting
        String cleaned = text
                .replaceAll("(?i)```json", "")
                .replaceAll("```", "")
                .replace("Detected food:", "")
                .trim();

        // Ensure it starts with JSON object
        if (!cleaned.startsWith("{")) {
            return "{}";
        }

        return cleaned;
    }

}
