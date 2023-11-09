package augustopadilha.clientdistributedsystems.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import static augustopadilha.clientdistributedsystems.App.getResponse;
import static augustopadilha.clientdistributedsystems.App.parseJson;
import static augustopadilha.clientdistributedsystems.controllers.RequestController.sendRequest;

public class ListDataController {
    public static void perform(Socket socket, String sessionToken) {
            try {
                System.out.println(" **************** - Dados do Usuário - ****************");
                // Criar o JSON para listar os dados do usuário
                Map<String, Object> jsonMapListData = new HashMap<>();
                jsonMapListData.put("action", "pedido-proprio-usuario");

                Map<String, String> dataMapListData = new HashMap<>();
                dataMapListData.put("token", sessionToken);
                jsonMapListData.put("data", dataMapListData);
                ObjectMapper objectMapper = new ObjectMapper();

                // Enviar o JSON de login para o servidor
                PrintWriter outToServerListData = new PrintWriter(socket.getOutputStream(), true);
                String jsonRequestListData = objectMapper.writeValueAsString(jsonMapListData);
                sendRequest(outToServerListData, jsonRequestListData);

                // Receber JSON de resposta do servidor
                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String serverResponse = getResponse(inFromServer);
                Map<String, Object> jsonMap = parseJson(serverResponse);
                boolean error = (boolean) jsonMap.get("error");

                System.out.println("JSON recebido: " + serverResponse + "\n");

                if (!error) {
                    Map<String, Object> dataMap = (Map<String, Object>) jsonMap.get("data");
                    System.out.println("ID: " + dataMap.get("id") + "\n" +
                                    "Nome: " + dataMap.get("name") + "\n" +
                                    "E-mail: " + dataMap.get("email") + "\n" +
                                    "Tipo: " + dataMap.get("type") + "\n");
                }

            } catch (IOException e) {
                System.err.println("Erro de E/S durante o login: " + e.getMessage());
                // Trate a exceção de acordo com as necessidades do seu aplicativo
            }
    }
}
