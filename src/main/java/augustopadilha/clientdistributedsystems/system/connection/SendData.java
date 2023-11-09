package augustopadilha.clientdistributedsystems.system.connection;

import augustopadilha.clientdistributedsystems.JavaFXApp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class SendData {
    /* ------------------- Generators ------------------- */
    private Map<String, Object> generateFinalData(String action, Map<String, Object> data) throws JsonProcessingException {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("action", action);
        dataMap.put("data", data);
        return dataMap;
    }
    public Map<String, Object> generateLoginData(String email, String password) throws JsonProcessingException {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("email", email);
        dataMap.put("password", password);
        return generateFinalData("login", dataMap);
    }
    public Map<String, Object> generateRegisterData(String name, String email, String password, String type) throws JsonProcessingException {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("name", name);
        dataMap.put("email", email);
        dataMap.put("password", password);
        if(type.equals("admin")) {
            dataMap.put("type", type);
            return generateFinalData("cadastro-usuario", dataMap);
        } else
            return generateFinalData("autocadastro-usuario", dataMap);
    }

    public Map<String, Object> generateLogoutData(String token) throws JsonProcessingException {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("token", token);
        return generateFinalData("logout", dataMap);
    }
    /* --------------------------------------------------- */

    /* --------------------- Senders --------------------- */
    public String sendLoginData(String email, String password) throws JsonProcessingException {
        String serverResponse = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Connection connection = JavaFXApp.getConnection();
            serverResponse = connection.send(objectMapper.writeValueAsString(generateLoginData(email, password)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return serverResponse;
    }
public String sendRegisterData(String name, String email, String password, String type) throws JsonProcessingException {
        String serverResponse = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Connection connection = JavaFXApp.getConnection();
            serverResponse = connection.send(objectMapper.writeValueAsString(generateRegisterData(name, email, password, type)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return serverResponse;
}

    public String sendLogoutData(String token) throws JsonProcessingException {
        String serverResponse = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Connection connection = JavaFXApp.getConnection();
            serverResponse = connection.send(objectMapper.writeValueAsString(generateLogoutData(token)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return serverResponse;
    }
    /* --------------------------------------------------- */
}
