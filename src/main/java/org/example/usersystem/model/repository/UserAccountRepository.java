package org.example.usersystem.model.repository;

import io.github.cdimascio.dotenv.Dotenv;
import org.example.usersystem.model.entity.UserAccount;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UserAccountRepository {
    private final String API_URL;
    private final String API_KEY;
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public UserAccountRepository() {
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing() // .env 파일 없어도 제공받은 환경변수로 작동
                .load();
        API_URL = dotenv.get("SUPABASE_API_URL");
        API_KEY = dotenv.get("SUPABASE_API_KEY");
    }

    public void createUser(UserAccount userAccount) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "/rest/v1/USER_ACCOUNT"))
                .header("apikey", API_KEY)
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/json")
                .header("Prefer", "return=minimal")
                .POST(
                        HttpRequest.BodyPublishers.ofString(
                                objectMapper.writeValueAsString(userAccount)
                        )
                )
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            if (!response.body().isBlank()) {
                System.out.println(response.body());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}