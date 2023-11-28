package augustopadilha.clientdistributedsystems.controllers.admin.point;

import augustopadilha.clientdistributedsystems.system.connection.ReceiveData;
import augustopadilha.clientdistributedsystems.system.connection.SendData;
import augustopadilha.clientdistributedsystems.views.ViewFactory;
import com.fasterxml.jackson.databind.JsonNode;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterPointController implements Initializable {
    public TextField name_field;
    public TextArea obs_area;
    public Button register_button;
    public Label error_label;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        String name = name_field.getText();
        String obs = obs_area.getText();
        JsonNode response = sender.sendRegisterPointData(name, obs);
        if (response != null) {
            ReceiveData receiver = new ReceiveData(response);
            if (receiver.getError()) {
                ViewFactory.getInstance().showErrorMessage(receiver.getMessage());
            } else {
                name_field.setText("");
                obs_area.setText("");
                error_label.setText("Ponto cadastrado com sucesso!");
            }
        }
    }

    private void setError_label(String message) {
        error_label.setText(message);
    }
}
