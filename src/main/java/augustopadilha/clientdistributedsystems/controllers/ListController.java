package augustopadilha.clientdistributedsystems.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static augustopadilha.clientdistributedsystems.App.getResponse;
import static augustopadilha.clientdistributedsystems.App.parseJson;
import static augustopadilha.clientdistributedsystems.controllers.RequestController.sendRequest;

public class ListController {
    public static void perform(Socket socket, String sessionToken) {
        try {
            System.out.println(" **************** - Listagem de Usuários - ****************");
            // Criar o JSON para a ação de logout
            Map<String, Object> jsonMapDataUser = new HashMap<>();
            jsonMapDataUser.put("action", "listar-usuarios");

            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("token", sessionToken);
            jsonMapDataUser.put("data", dataMap);

            // Enviar o JSON para o servidor
            PrintWriter outToServer = new PrintWriter(socket.getOutputStream(), true);
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonRequest = objectMapper.writeValueAsString(jsonMapDataUser);
            sendRequest(outToServer, jsonRequest);

            // Receber JSON de resposta do servidor
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String serverResponse = getResponse(inFromServer);
            Map<String, Object> jsonMap = parseJson(serverResponse);
            boolean error = (boolean) jsonMap.get("error");

            System.out.println("JSON recebido: " + serverResponse + "\n");

            if (!error) {
                Map<String, Object> receivedDataMap = (Map<String, Object>) jsonMap.get("data");
                List<Map<String, Object>> userList = (List<Map<String, Object>>) receivedDataMap.get("users");

                for (Map<String, Object> userMap : userList) {
                    System.out.println("-----------------------------------");
                    System.out.println("ID: " + userMap.get("id") + "\n" +
                            "Nome: " + userMap.get("name") + "\n" +
                            "E-mail: " + userMap.get("email") + "\n" +
                            "Tipo: " + userMap.get("type") + "\n");
                    System.out.println("-----------------------------------");
                }
            }

        } catch (Exception e) {
            System.err.println("Couldn't get I/O: " + e.getMessage());
        }
    }
}
