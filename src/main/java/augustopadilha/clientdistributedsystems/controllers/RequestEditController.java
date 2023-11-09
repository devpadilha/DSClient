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
import static augustopadilha.clientdistributedsystems.system.utilities.Token.isTokenAdmin;

public class RequestEditController {
    public static void perform(Socket socket, String token) throws IOException {
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println(" **************** - PEDIDO EDICAO - ****************");
            System.out.println("Qual o id do usuário que deseja alterar?");
            int id = Integer.parseInt(stdIn.readLine());
            do {
                if (id < 0) {
                    System.out.println("Id inválido. Por favor, insira um id válido.");
                    id = Integer.parseInt(stdIn.readLine());
                }
            } while (id < 0);

            // Criar o JSON para a solicitação de edição
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("action", "pedido-edicao-usuario");

            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("token", token);
            dataMap.put("id", id);
            jsonMap.put("data", dataMap);
            ObjectMapper objectMapper = new ObjectMapper();

            // Enviar o JSON de login para o servidor
            PrintWriter outToServer = new PrintWriter(socket.getOutputStream(), true);
            String jsonRequest = objectMapper.writeValueAsString(jsonMap);
            sendRequest(outToServer, jsonRequest);

            // Receber JSON de resposta do servidor
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String serverResponse = getResponse(inFromServer);
            Map<String, Object> receivedJsonMap = parseJson(serverResponse);
            boolean error = (boolean) receivedJsonMap.get("error");

            System.out.println("JSON recebido: " + serverResponse + "\n");

            if (!error) {
                Map<String, Object> receivedDataMap = (Map<String, Object>) receivedJsonMap.get("data");
                System.out.println("ID: " + receivedDataMap.get("id") + "\n" +
                        "Nome: " + receivedDataMap.get("name") + "\n" +
                        "E-mail: " + receivedDataMap.get("email") + "\n" +
                        "Tipo: " + receivedDataMap.get("type") + "\n");

                System.out.println(" **************** - EDITAR - ****************");
                String name = null;
                String email = null;
                String password = null;

                System.out.println("Deseja editar nome? (S/N)");
                if(stdIn.readLine().equals("S")) {
                    System.out.println("Digite o novo nome: ");
                    name = stdIn.readLine();
                }
                System.out.println("Deseja editar email? (S/N)");
                if(stdIn.readLine().equals("S")) {
                    System.out.println("Digite o novo email: ");
                    email = stdIn.readLine();
                    // Validar formato do email
                    if (!UserCredentialsController.isValidEmailFormat(email)) {
                        do {
                            System.out.println("Email inválido. Por favor, insira um e-mail válido.");
                            email = stdIn.readLine();
                        } while (!UserCredentialsController.isValidEmailFormat(email));
                    }
                }
                System.out.println("Deseja editar senha? (S/N)");
                if(stdIn.readLine().equals("S")) {
                    System.out.println("Digite a nova senha: ");
                    password = PasswordHashController.passwordMD5(stdIn.readLine());
                }

                String type = null;
                if(isTokenAdmin(token))
                    type = "admin";
                else
                    type = "user";

                // alterar o JSON para edição
                jsonMap.put("action", "edicao-usuario");

                dataMap.put("token", token);
                dataMap.put("id", id);
                dataMap.put("name", name);
                dataMap.put("email", email);
                dataMap.put("password", password);
                dataMap.put("type", type);
                jsonMap.put("data", dataMap);

                // Enviar o JSON de login para o servidor
                jsonRequest = objectMapper.writeValueAsString(jsonMap);
                sendRequest(outToServer, jsonRequest);
            }

        } catch (IOException e) {
            System.err.println("Couldn't get I/O: " + e.getMessage());
        }
    }
}
