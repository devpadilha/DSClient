package augustopadilha.clientdistributedsystems.controllers;

import augustopadilha.clientdistributedsystems.models.Model;
import augustopadilha.clientdistributedsystems.system.JsonHandler;
import augustopadilha.clientdistributedsystems.system.connection.SendData;
import augustopadilha.clientdistributedsystems.system.utilities.Token;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static augustopadilha.clientdistributedsystems.App.getResponse;
import static augustopadilha.clientdistributedsystems.App.parseJson;
import static augustopadilha.clientdistributedsystems.controllers.RequestController.sendRequest;

public class LoginController implements Initializable {
    public TextField email_field;
    public TextField password_field;
    public Button login_button;
    public Label error_label;
    public Button register_button;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        email_field.setText("admin@admin.com");
        password_field.setText("admin123");
        login_button.setOnAction(event -> {
            try {
                onLogin();
            } catch (Exception e) {
                setError_label("Dados inválidos");
                throw new RuntimeException(e);
            }
        });
        register_button.setOnAction(event -> onRegister());
    }

    private void onRegister() {
        Model.getInstance().getViewFactory().closeStage((Stage) login_button.getScene().getWindow());
        Model.getInstance().getViewFactory().showRegisterWindow();
    }

    private void onLogin() throws Exception {
        // Cria uma instância de SendData para enviar dados ao servidor
        SendData sender = new SendData();

        // Obtém o email e a senha dos campos de entrada
        String email = email_field.getText();
        String password = password_field.getText();

        // Valida o email e a senha
        if (UserCredentialsController.validate(email, password)) {
            // Criptografa a senha usando MD5
            password = DigestUtils.md5Hex(password).toUpperCase();

            // Envia os dados de login para o servidor
            String response = sender.sendLoginData(email, password);

            // Verifica se a resposta do servidor não é nula
            if (response != null) {
                JsonHandler receiver = new JsonHandler(JsonHandler.parseJson(response));
                if (receiver.getError()) {
                    Model.getInstance().getViewFactory().showErrorMessage(receiver.getMessage());
                } else {
                    // Obtém a janela atual e salva o token JWT
                    Stage stage = (Stage) error_label.getScene().getWindow();
                    Token.saveJwtToken(receiver.getToken());

                    // Verifica se o token JWT pertence a um administrador para abrir a janela correspondente
                    if (Token.isTokenAdmin(Token.getJwtToken())) {
                        Model.getInstance().getViewFactory().showAdminWindow();
                    } else {
                        Model.getInstance().getViewFactory().showUserWindow();
                    }

                    // Fecha a janela de login
                    Model.getInstance().getViewFactory().closeStage(stage);
                }
            } else {
                Model.getInstance().getViewFactory().showErrorMessage("Erro ao conectar com o servidor");
            }
        } else {
            Model.getInstance().getViewFactory().showErrorMessage("Dados inválidos");
        }
    }
    public static String perform(Socket socket) throws IOException {
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println(" **************** - LOGIN - ****************");
            System.out.println("Email: ");
            String email = stdIn.readLine();
            // Validar formato do email
            if (!UserCredentialsController.isValidEmailFormat(email)) {
                do {
                    System.out.println("Email inválido. Por favor, insira um e-mail válido.");
                    email = stdIn.readLine();
                } while (!UserCredentialsController.isValidEmailFormat(email));
            }
            System.out.println("Senha: ");
            String password = PasswordHashController.passwordMD5(stdIn.readLine());

            // Criar o JSON para o login
            Map<String, Object> jsonMapLogin = new HashMap<>();
            jsonMapLogin.put("action", "login");

            Map<String, String> dataMapLogin = new HashMap<>();
            dataMapLogin.put("email", email);
            dataMapLogin.put("password", password);
            jsonMapLogin.put("data", dataMapLogin);
            ObjectMapper objectMapper = new ObjectMapper();

            // Enviar o JSON de login para o servidor
            PrintWriter outToServerLogin = new PrintWriter(socket.getOutputStream(), true);
            String jsonRequestLogin = objectMapper.writeValueAsString(jsonMapLogin);
            sendRequest(outToServerLogin, jsonRequestLogin);

            // Receber JSON de resposta do servidor
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String serverResponse = getResponse(inFromServer);
            System.out.println("JSON recebido: " + serverResponse + "\n");
            Map<String, Object> jsonMap = parseJson(serverResponse);
            boolean error = (boolean) jsonMap.get("error");

            if (!error) {
                Map<String, String> userData = (Map<String, String>) jsonMap.get("data");
                return userData.get("token");
            }

        } catch (IOException e) {
            System.err.println("Couldn't get I/O: " + e.getMessage());
        }
        return null; // Retornar o token obtido ou nulo se o login falhar
    }

    private void setError_label(String message) {
        error_label.setText(message);
    }
}
