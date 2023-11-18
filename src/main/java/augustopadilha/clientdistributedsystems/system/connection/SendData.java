package augustopadilha.clientdistributedsystems.system.connection;

import augustopadilha.clientdistributedsystems.JavaFXApp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.HashMap;
import java.util.Map;

public class SendData {
    private ObjectMapper objectMapper = new ObjectMapper();
    Connection connection = JavaFXApp.getConnection();
    private JsonNode data;

    public JsonNode getData() {
        return data;
    }
    /* ------------------- Generators ------------------- */
    private JsonNode generateFinalData(String action, JsonNode data) {
        this.data = objectMapper.createObjectNode();
        ((ObjectNode) this.getData()).put("action", action);
        ((ObjectNode) this.getData()).set("data", data);
        return this.data;
    }

    public JsonNode generateLoginData(String email, String password) throws JsonProcessingException {
        JsonNode data = objectMapper.createObjectNode();
        ((ObjectNode) data).put("email", email);
        ((ObjectNode) data).put("password", password);
        return generateFinalData("login", data);
    }

    public JsonNode generateAutoRegisterData(String name, String email, String password, String type) {
        JsonNode data = objectMapper.createObjectNode();
        ((ObjectNode) data).put("name", name);
        ((ObjectNode) data).put("email", email);
        ((ObjectNode) data).put("password", password);
        ((ObjectNode) data).put("type", type);
        return generateFinalData("autocadastro-usuario", data);
    }

    public JsonNode generateRegisterData(String token, String name, String email, String password, String type) throws JsonProcessingException {
        JsonNode data = objectMapper.createObjectNode();
        ((ObjectNode) data).put("token", token);
        ((ObjectNode) data).put("name", name);
        ((ObjectNode) data).put("email", email);
        ((ObjectNode) data).put("password", password);
        ((ObjectNode) data).put("type", type);
        return generateFinalData("cadastro-usuario", data);
    }

    public JsonNode generateLogoutData(String token) throws JsonProcessingException {
        JsonNode data = objectMapper.createObjectNode();
        ((ObjectNode) data).put("token", token);
        return generateFinalData("logout", data);
    }

    public JsonNode generateClientListdata(String token) throws JsonProcessingException {
        JsonNode data = objectMapper.createObjectNode();
        ((ObjectNode) data).put("token", token);
        return generateFinalData("listar-usuarios", data);
    }

    public JsonNode generateProfileData(String token) throws JsonProcessingException {
        JsonNode data = objectMapper.createObjectNode();
        ((ObjectNode) data).put("token", token);
        return generateFinalData("pedido-proprio-usuario", data);
    }
    /* --------------------------------------------------- */

    /* --------------------- Senders --------------------- */
    public JsonNode sendLoginData(String email, String password) throws JsonProcessingException {
        String serverResponse = null;
        try {
            serverResponse = connection.send(objectMapper.writeValueAsString(generateLoginData(email, password)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return stringToJsonNode(serverResponse);
    }

    public JsonNode sendAutoRegisterData(String name, String email, String password, String type) throws JsonProcessingException {
        String serverResponse = null;
        try {
            serverResponse = connection.send(objectMapper.writeValueAsString(generateAutoRegisterData(name, email, password, type)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return stringToJsonNode(serverResponse);
    }

    public JsonNode sendRegisterData(String token, String name, String email, String password, String type) throws JsonProcessingException {
        String serverResponse = null;
        try {
            serverResponse = connection.send(objectMapper.writeValueAsString(generateRegisterData(token, name, email, password, type)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return stringToJsonNode(serverResponse);
    }

    public JsonNode sendLogoutData(String token) throws JsonProcessingException {
        String serverResponse = null;
        try {
            serverResponse = connection.send(objectMapper.writeValueAsString(generateLogoutData(token)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return stringToJsonNode(serverResponse);
    }

    public JsonNode sendClientListData(String token) throws JsonProcessingException {
        String serverResponse = null;
        try {
            serverResponse = connection.send(objectMapper.writeValueAsString(generateClientListdata(token)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return stringToJsonNode(serverResponse);
    }

    public JsonNode sendProfileData(String token) throws JsonProcessingException {
        String serverResponse = null;
        try {
            serverResponse = connection.send(objectMapper.writeValueAsString(generateProfileData(token)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return stringToJsonNode(serverResponse);
    }
    /* --------------------------------------------------- */

    public JsonNode stringToJsonNode (String json) throws JsonProcessingException {
        if (json == null || json.isEmpty()) return null;
        return objectMapper.readTree(json);
    }
}
