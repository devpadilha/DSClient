package augustopadilha.clientdistributedsystems.controllers;

import augustopadilha.clientdistributedsystems.system.connection.ReceiveData;
import augustopadilha.clientdistributedsystems.system.connection.SendData;
import augustopadilha.clientdistributedsystems.system.connection.UserCredentialsValidator;
import augustopadilha.clientdistributedsystems.system.utilities.Token;
import augustopadilha.clientdistributedsystems.views.ViewFactory;
import com.fasterxml.jackson.databind.JsonNode;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;

import java.net.URL;
import java.util.ResourceBundle;

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
        ViewFactory.getInstance().closeStage((Stage) login_button.getScene().getWindow());
        ViewFactory.getInstance().showRegisterWindow();
    }

    private void onLogin() throws Exception {
        // Cria uma instância de SendData para enviar dados ao servidor
        SendData sender = new SendData();

        // Obtém o email e a senha dos campos de entrada
        String email = email_field.getText();
        String password = password_field.getText();

        // Valida o email e a senha
        if (UserCredentialsValidator.validate(email, password)) {
            // Criptografa a senha usando MD5
            password = DigestUtils.md5Hex(password).toUpperCase();

            // Envia os dados de login para o servidor
            JsonNode response = sender.sendLoginData(email, password);

            // Verifica se a resposta do servidor não é nula
            if (response != null) {
                ReceiveData receiver = new ReceiveData(response);
                if (receiver.getError()) {
                    ViewFactory.getInstance().showErrorMessage(receiver.getMessage());
                } else {
                    // Obtém a janela atual e salva o token JWT
                    Stage stage = (Stage) error_label.getScene().getWindow();
                    Token.saveJwtToken(receiver.getToken());

                    // Verifica se o token JWT pertence a um administrador para abrir a janela correspondente
                    if (Token.isTokenAdmin(Token.getJwtToken())) {
                        ViewFactory.getInstance().showAdminWindow();
                    } else {
                        ViewFactory.getInstance().showUserWindow();
                    }

                    // Fecha a janela de login
                    ViewFactory.getInstance().closeStage(stage);
                }
            } else {
                ViewFactory.getInstance().showErrorMessage("Erro ao conectar com o servidor");
            }
        } else {
            ViewFactory.getInstance().showErrorMessage("Dados inválidos");
        }
    }

    private void setError_label(String message) {
        error_label.setText(message);
    }
}