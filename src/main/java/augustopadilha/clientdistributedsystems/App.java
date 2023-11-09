package augustopadilha.clientdistributedsystems;

import augustopadilha.clientdistributedsystems.controllers.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;

public class App {

    public static void main(String[] args) throws IOException {
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Digite o número IP do servidor: ");
        String serverHostname = stdIn.readLine();

        System.out.print("Digite o número da porta: ");
        int serverPort = Integer.parseInt(stdIn.readLine());
        System.out.println();

        try (Socket socket = new Socket(serverHostname, serverPort)) {
            String sessionToken = "";
            boolean running = true;

            while (running) {
                if(sessionToken == "") {
                    try {
                        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String serverResponse = "";

                        System.out.println("Digite o Número da Operação");
                        System.out.println("1 - Cadastro");
                        System.out.println("2 - Login");
                        System.out.println("0 - Encerrar");

                        int menu = Integer.parseInt(stdIn.readLine());

                        switch (menu) {
                            case 0:
                                System.out.println("Encerrando");
                                running = false;
                                break;

                            case 1:
                                RegistrationController.perform(socket, sessionToken);
                                serverResponse = getResponse(inFromServer);
                                System.out.println("JSON recebido: " + serverResponse);
                                break;

                            case 2:
                                sessionToken = LoginController.perform(socket);
                                break;

                            default:
                                System.out.println("Opção inválida");
                                break;
                        }
                    } catch (IOException e) {
                        System.err.println("Couldn't get I/O: " + e.getMessage());
                        System.exit(1);
                    }
                } else {
                    try {
                        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String serverResponse = "";

                        System.out.println("Digite o Número da Operação");
                        System.out.println("1 - Cadastro");
                        System.out.println("2 - Login");
                        System.out.println("3 - Logout");
                        System.out.println("4 - Listar Usuários");
                        System.out.println("5 - Listar Informações do Usuário");
                        System.out.println("6 - Editar Próprio Usuário");
                        System.out.println("7 - Editar Usuário");
                        System.out.println("0 - Encerrar");

                        int menu = Integer.parseInt(stdIn.readLine());

                        switch (menu) {
                            case 0:
                                System.out.println("Encerrando");
                                running = false;
                                break;

                            case 1:
                                RegistrationController.perform(socket, sessionToken);
                                serverResponse = getResponse(inFromServer);
                                System.out.println("JSON recebido: " + serverResponse);
                                break;

                            case 2:
                                sessionToken = LoginController.perform(socket);
                                break;

                            case 3:
                                LogoutController.perform(sessionToken, socket);
                                sessionToken = "";
                                serverResponse = getResponse(inFromServer);
                                System.out.println("JSON recebido: " + serverResponse);
                                break;

                            case 4:
                                ListController.perform(socket, sessionToken);
                                break;

                            case 5:
                                ListDataController.perform(socket, sessionToken);
                                break;

                            case 6:
                                EditSelfController.perform(socket, sessionToken);
                                break;
                            case 7:
                                RequestEditController.perform(socket, sessionToken);
                                serverResponse = getResponse(inFromServer);
                                System.out.println("JSON recebido: " + serverResponse);
                                break;

                            default:
                                System.out.println("Opção inválida");
                                break;
                        }
                    } catch (IOException e) {
                        System.err.println("Couldn't get I/O: " + e.getMessage());
                        System.exit(1);
                    }
                }
            }

        } catch (UnknownHostException e) {
            System.err.println("Server host desconhecido: " + serverHostname);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Não foi possível se conector com: " + serverHostname);
            System.exit(1);
        }
    }

    public static String getResponse(BufferedReader inFromServer) {
        try {
            String serverResponse = inFromServer.readLine();
            return serverResponse;
        } catch (IOException e) {
            System.err.println("Error reading server response: " + e.getMessage());
            return "";
        }
    }

    public static Map<String, Object> parseJson(String json) {
        try {
            // Ler o JSON e convertê-lo para um Map
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.readTree(json);
            Map<String, Object> jsonMap = objectMapper.readValue(json, Map.class);

            return jsonMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
