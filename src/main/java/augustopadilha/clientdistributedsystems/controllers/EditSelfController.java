package augustopadilha.clientdistributedsystems.controllers;

import augustopadilha.clientdistributedsystems.system.connection.UserCredentialsValidator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import static augustopadilha.clientdistributedsystems.controllers.RequestController.sendRequest;
import static augustopadilha.clientdistributedsystems.system.JWTManager.getId;

public class EditSelfController {
    public static void perform(Socket socket, String token) throws IOException {
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        try {
            String name = null;
            String email = null;
            String password = null;
            int id = getId(token);

            System.out.println(" **************** - EDITAR - ****************");
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
                if (!UserCredentialsValidator.isEmailValid(email)) {
                    do {
                        System.out.println("Email inválido. Por favor, insira um e-mail válido.");
                        email = stdIn.readLine();
                    } while (!UserCredentialsValidator.isEmailValid(email));
                }
            }
            System.out.println("Deseja editar senha? (S/N)");
            if(stdIn.readLine().equals("S")) {
                System.out.println("Digite a nova senha: ");
                password = PasswordHashController.passwordMD5(stdIn.readLine());
            }

            // Criar o JSON para o edição
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("action", "autoedicao-usuario");

            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("token", token);
            dataMap.put("id", id);
            dataMap.put("email", email);
            dataMap.put("password", password);
            jsonMap.put("data", dataMap);
            ObjectMapper objectMapper = new ObjectMapper();

            // Enviar o JSON de login para o servidor
            PrintWriter outToServer = new PrintWriter(socket.getOutputStream(), true);
            String jsonRequest = objectMapper.writeValueAsString(jsonMap);
            sendRequest(outToServer, jsonRequest);

        } catch (IOException e) {
            System.err.println("Couldn't get I/O: " + e.getMessage());
        }
    }
}
