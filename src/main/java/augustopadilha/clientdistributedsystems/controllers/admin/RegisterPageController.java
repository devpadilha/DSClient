package augustopadilha.clientdistributedsystems.controllers.admin;

import augustopadilha.clientdistributedsystems.system.connection.ReceiveData;
import augustopadilha.clientdistributedsystems.system.connection.SendData;
import augustopadilha.clientdistributedsystems.views.MenuOptions;
import augustopadilha.clientdistributedsystems.views.ViewFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterPageController implements Initializable {
    public Button register_user_button;
    public Button register_point_button;
    public Button register_segment_button;
    public Label error_label;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        register_user_button.setOnAction(event -> {
            try {
                onRegisterUser();
            } catch (Exception e) {
                setError_label("Dados inválidos");
                throw new RuntimeException(e);
            }
        });

        register_point_button.setOnAction(event -> {
            try {
                onRegisterPoint();
            } catch (Exception e) {
                setError_label("Dados inválidos");
                throw new RuntimeException(e);
            }
        });

        register_segment_button.setOnAction(event -> {
            try {
                onRegisterSegment();
            } catch (Exception e) {
                setError_label("Dados inválidos");
                throw new RuntimeException(e);
            }
        });
    }

    private void onRegisterUser() throws Exception {
        ViewFactory.getInstance().getSelectedMenuItem().set(MenuOptions.REGISTER_USER);
    }

    private void onRegisterPoint() throws Exception {
        ViewFactory.getInstance().getSelectedMenuItem().set(MenuOptions.REGISTER_POINT);
    }

    private void onRegisterSegment() throws Exception {
        SendData sender = new SendData();
        ReceiveData receicer = new ReceiveData(sender.sendListPointsData());
        if (receicer.getError()) {
            ViewFactory.getInstance().showErrorMessage(receicer.getMessage());
        } else {
            receicer.getPointsList();
            ViewFactory.getInstance().getSelectedMenuItem().set(MenuOptions.REGISTER_SEGMENT);
        }
    }

    private void setError_label(String message) {
        error_label.setText(message);
    }
}
