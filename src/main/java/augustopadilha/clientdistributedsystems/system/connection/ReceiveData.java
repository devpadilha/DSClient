package augustopadilha.clientdistributedsystems.system.connection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class ReceiveData {
    private static final ObjectMapper jackson = new ObjectMapper();

    private String action;
    private boolean error;
    private String message;
    private Map<String, Object> data;

    public ReceiveData(Map<String, Object> response) {
        this.action = (String) response.get("action");
        this.error = (boolean) response.get("error");
        this.message = (String) response.get("message");
        if (response.containsKey("data")) {
            this.data = (Map<String, Object>) response.get("data");
        }
    }

    public static Map<String, Object> stringToMap(String json) throws JsonProcessingException {
        Map<String, Object> map = jackson.readValue(json, Map.class);
        return map;
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