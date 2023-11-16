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

public class RegistrationController {
    public static void perform(Socket socket, String sessionToken) {
        try {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            System.out.println(" **************** - CADASTRAR - ****************");
            System.out.println("Nome: ");
            String name = stdIn.readLine();
            System.out.println("Email: ");
            String email = stdIn.readLine();
            // Validar formato do email
            while (!UserCredentialsValidator.isEmailValid(email)) {
                System.out.println("Email inválido. Por favor, insira um e-mail válido.");
                email = stdIn.readLine();
            }
            System.out.println("Senha: ");
            String password = PasswordHashController.passwordMD5(stdIn.readLine());
            System.out.println("Tipo de usuário: ");
            System.out.println("0 - para Usuário Comum");
            System.out.println("1 - para Administrador");
            String role = stdIn.readLine();
            switch (role) {
                case "0":
                    role = "user";
                    break;
                case "1":
                    role = "admin";
                    break;
                default:
                    while (!role.equals("0") && !role.equals("1")) {
                        System.out.println("Tipo de usuário inválido. Por favor, insira um tipo válido.");
                        role = stdIn.readLine();
                    }
            }

            // Criar o JSON para o cadastro
            Map<String, Object> jsonMapCadastro = new HashMap<>();
            jsonMapCadastro.put("action", "cadastro-usuario");

            Map<String, String> dataMapCadastro = new HashMap<>();
            dataMapCadastro.put("name", name);
            dataMapCadastro.put("email", email);
            dataMapCadastro.put("password", password);
            dataMapCadastro.put("token", sessionToken);
            dataMapCadastro.put("type", role);
            jsonMapCadastro.put("data", dataMapCadastro);

            // Enviar o JSON para o servidor
            PrintWriter outToServerCadastro = new PrintWriter(socket.getOutputStream(), true);
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonRequestCadastro = objectMapper.writeValueAsString(jsonMapCadastro);
            sendRequest(outToServerCadastro, jsonRequestCadastro);

        } catch (IOException e) {
            System.err.println("Couldn't get I/O: " + e.getMessage());
        }
    }
}
