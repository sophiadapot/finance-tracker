package com.tracker.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class OpenAIService {
    private static final String API_KEY = System.getenv("OPENAI_API_KEY");
    //used in http 
    private static final String URL = "https://api.openai.com/v1/chat/completions";
    
    private final String userPersona;

    public OpenAIService(String userPersona) {
        this.userPersona = (userPersona != null) ? userPersona : "General User";
    }

    public String getFinancialAdvice(Map<String, Double> data) throws Exception {
        if (API_KEY == null) return "Error: API Key missing.";

        // 1. Create a system instruction based on the user's persona
        String systemInstruction = "You are a financial advisor for a " + userPersona + ". " +"Keep advice concise and specific to their demographic.";
        
        String userPrompt = "My spending data: " + data.toString() + ". Give me 3 tips.";

        String json = "{"+"\"model\": \"gpt-3.5-turbo\","+"\"messages\": ["+"{\"role\": \"system\", \"content\": \"" + systemInstruction + "\"},"+"{\"role\": \"user\", \"content\": \"" + userPrompt + "\"}"+"]"+"}";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL)).header("Content-Type", "application/json").header("Authorization", "Bearer " + API_KEY).POST(HttpRequest.BodyPublishers.ofString(json)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        // Return raw response (parse 'choices[0].message.content' in the controller)
        return response.body();
    }
}