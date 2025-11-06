package org.example.usersystem.model.repository;

import io.github.cdimascio.dotenv.Dotenv;
import org.example.usersystem.model.entity.UserAccount;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

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

    // 데이터를 찾아온다
    // 1. 식별자(id) -> 바로 1개를 받아옴;
    // 2. all -> 전체.
    // 3. query, 조건 -> 특정 조건에 맞는 데이터만 받아오기.
    public UserAccount findByUsername(String username) {
        // 일단 받아오고...
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL
                        + "/rest/v1/USER_ACCOUNT"
                        + "?"
                        + "select=*" // table에 있는 모든 속성(열, 값)을 가져오겠다
                        + "&"
                        + "username=eq.%s".formatted(username)
                ))
                .header("apikey", API_KEY)
                .header("Authorization", "Bearer " + API_KEY)
                .GET()
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            if (response.body().isBlank()) throw new RuntimeException();
            return objectMapper.readValue(
                    response.body(), // 문자열 형태로 전달받은 데이터
                    new TypeReference<List<UserAccount>>() {} // UserAccount로 파싱하여서 리스트 형태로 받고...
            ).get(0); // 1개만 있다고 가정
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // -> username -> account -> service -> account/form password가 일치하는지...
    }
}