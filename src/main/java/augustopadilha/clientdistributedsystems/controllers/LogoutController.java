package augustopadilha.clientdistributedsystems.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import static augustopadilha.clientdistributedsystems.controllers.RequestController.sendRequest;

public class LogoutController {
    public static void perform(String token, Socket socket) {
        try {
            System.out.println(" **************** - LOGOUT - ****************");
            // Criar o JSON para a ação de logout
            Map<String, Object> jsonMapLogout = new HashMap<>();
            jsonMapLogout.put("action", "logout");

            Map<String, String> dataMapLogout = new HashMap<>();
            dataMapLogout.put("token", token);
            jsonMapLogout.put("data", dataMapLogout);

            // Enviar o JSON para o servidor
            PrintWriter outToServerLogout = new PrintWriter(socket.getOutputStream(), true);
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonRequestLogout = objectMapper.writeValueAsString(jsonMapLogout);
            sendRequest(outToServerLogout, jsonRequestLogout);

        } catch (Exception e) {
            System.err.println("Couldn't get I/O: " + e.getMessage());
        }
    }
}
