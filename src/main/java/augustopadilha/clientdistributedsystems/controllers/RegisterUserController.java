package augustopadilha.clientdistributedsystems.controllers;

import augustopadilha.clientdistributedsystems.system.connection.UserCredentialsValidator;
import augustopadilha.clientdistributedsystems.system.connection.ReceiveData;
import augustopadilha.clientdistributedsystems.system.connection.SendData;
import augustopadilha.clientdistributedsystems.views.ViewFactory;
import com.fasterxml.jackson.databind.JsonNode;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterUserController implements Initializable {

    public TextField name_field;
    public TextField email_field;
    public PasswordField password_field;
    public Button register_button;
    public Label error_label;
    public Button login_button;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        register_button.setOnAction(event -> {
            try {
                onRegister();
            } catch (Exception e) {
                setError_label("Dados inválidos");
                throw new RuntimeException(e);
            }
        });
        login_button.setOnAction(event -> onLogin());
    }

    private void onRegister() {
        try {
            SendData sender = new SendData();
            String name = name_field.getText();
            String email = email_field.getText();
            String password = DigestUtils.md5Hex(password_field.getText()).toUpperCase();

            if (UserCredentialsValidator.registerUserIsValid(name, email, password, "user")) {
                JsonNode response = sender.sendAutoRegisterData(name, email, password, "user");

                if (response != null) {
                    ReceiveData receiver = new ReceiveData(response);

                    if (receiver.getError()) {
                        ViewFactory.getInstance().showErrorMessage(receiver.getMessage());
                    } else {
                        name_field.setText("");
                        email_field.setText("");
                        password_field.setText("");
                        error_label.setText("Usuário cadastrado com sucesso!");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            setError_label("Erro durante o registro: " + e.getMessage());
        }
    }

    private void onLogin() {
        ViewFactory.getInstance().closeStage((Stage) login_button.getScene().getWindow());
        ViewFactory.getInstance().showLoginWindow();
    }

    private void setError_label(String message) {
        error_label.setText(message);
    }
}
