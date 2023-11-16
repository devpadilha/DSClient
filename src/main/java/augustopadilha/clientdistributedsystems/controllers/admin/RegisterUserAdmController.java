package augustopadilha.clientdistributedsystems.controllers.admin;

import augustopadilha.clientdistributedsystems.system.connection.ReceiveData;
import augustopadilha.clientdistributedsystems.system.connection.SendData;
import augustopadilha.clientdistributedsystems.system.connection.UserCredentialsValidator;
import augustopadilha.clientdistributedsystems.views.ViewFactory;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.apache.commons.codec.digest.DigestUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterUserAdmController implements Initializable {
    public TextField name_field;
    public TextField email_field;
    public PasswordField password_field;
    public ChoiceBox account_selector;
    public Button register_button;
    public Label error_label;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        account_selector.setItems(FXCollections.observableArrayList("Usuário", "Administrador"));
        register_button.setOnAction(event -> {
            try {
                onRegister();
            } catch (Exception e) {
                setError_label("Dados inválidos");
                throw new RuntimeException(e);
            }
        });
    }

    private void onRegister() throws Exception {
        SendData sender = new SendData();
        String userType = account_selector.getValue().toString().equals("Administrador") ? "admin" : "user";
        String name = name_field.getText();
        String email = email_field.getText();
        String password = password_field.getText();
        if (UserCredentialsValidator.registerUserIsValid(name, email, password, userType)) {
            password = DigestUtils.md5Hex(password).toUpperCase();
            String response = sender.sendRegisterData(name, email, password, userType);
            if (response != null)
            {
                ReceiveData receiver = new ReceiveData(ReceiveData.stringToMap(response));
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
    }

    private void setError_label(String message) {
        error_label.setText(message);
    }
}
