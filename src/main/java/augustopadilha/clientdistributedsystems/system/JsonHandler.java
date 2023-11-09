package augustopadilha.clientdistributedsystems.system;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Map;

public class JsonHandler {
    private static final ObjectMapper jackson = new ObjectMapper();
    private String action;
    private boolean error;
    private String message;
    private Map<String, Object> data;

    public JsonHandler(Map<String, Object> json) {
        this.action = (String) json.get("action");
        this.error = (boolean) json.get("error");
        this.message = (String) json.get("message");
        if (json.containsKey("data")) {
            this.data = (Map<String, Object>) json.get("data");
        }
    }

    public static Map<String, Object> parseJson(String json) {
        try {
            // Ler o JSON e convertê-lo para um Map
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.readTree(json);
            Map<String, Object> jsonMap = objectMapper.readValue(json, Map.class);

            return jsonMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public String getMessage() {
        return message;
    }
    public boolean getError() {
        return error;
    }
    public String getToken() {
        if (data.containsKey("token")) {
            return (String) data.get("token");
        } else {
            System.out.println("Não há token neste objeto.");
            return null;
        }
    }
}
