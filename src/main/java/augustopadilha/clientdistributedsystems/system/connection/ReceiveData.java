package augustopadilha.clientdistributedsystems.system.connection;

import augustopadilha.clientdistributedsystems.views.ViewFactory;
import augustopadilha.clientdistributedsystems.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class ReceiveData {
    private static final ObjectMapper jackson = new ObjectMapper();

    private String action;
    private boolean error;
    private String message;
    private JsonNode data;

    public ReceiveData(JsonNode response) {
        this.action = response.get("action").asText();
        this.error = response.get("error").asBoolean();
        this.message = response.get("message").asText();
        if (response.has("data")) {
            this.data =  response.get("data");
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
        if (data.has("token")) {
            return data.get("token").asText();
        } else {
            System.out.println("Não há token neste objeto.");
            return null;
        }
    }

    public void getClientList() throws JsonProcessingException {
        JsonNode rootNode = jackson.readTree(data.toString());
        if (data.has("users")) {
            JsonNode jsonNode = rootNode.get("users");
            ViewFactory.getInstance().setClients(jsonNode);
        }
    }

    public void getPointsList() throws JsonProcessingException {
        JsonNode rootNode = jackson.readTree(data.toString());
        if (data.has("pontos")) {
            JsonNode jsonNode = rootNode.get("pontos");
            ViewFactory.getInstance().setPoints(jsonNode);
        }
    }

    public void getSegmentsList() throws JsonProcessingException {
        JsonNode rootNode = jackson.readTree(data.toString());
        if (data.has("segmentos")) {
            JsonNode jsonNode = rootNode.get("segmentos");
            ViewFactory.getInstance().setSegments(jsonNode);
        }
    }

    public void getUserData() throws JsonProcessingException {
        JsonNode jsonNode = jackson.readTree(data.toString());
        ViewFactory.getInstance().setUser(jsonNode.get("user"));
    }
}