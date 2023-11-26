package augustopadilha.clientdistributedsystems.controllers.user;

import augustopadilha.clientdistributedsystems.system.connection.ReceiveData;
import augustopadilha.clientdistributedsystems.system.connection.SendData;
import augustopadilha.clientdistributedsystems.system.utilities.Token;
import augustopadilha.clientdistributedsystems.views.MenuOptions;
import augustopadilha.clientdistributedsystems.views.ViewFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UserMenuController implements Initializable {
    public Button dashboard_btn;
    public Button profile_btn;
    public Button logout_btn;
    public Button report_button;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { addListeners(); }

    private void addListeners() {
        dashboard_btn.setOnAction(event -> onDashboard());
        logout_btn.setOnAction(event -> {
            try {
                onLogOut();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void onDashboard() {
        ViewFactory.getInstance().getSelectedMenuItem().set(MenuOptions.DASHBOARD);
    }

    public void onLogOut() throws JsonProcessingException {
        Stage stage = (Stage) logout_btn.getScene().getWindow();

        SendData sender = new SendData();
        JsonNode response = sender.sendLogoutData();
        if (response != null)
        {
            ReceiveData receiver = new ReceiveData(response);
            if (receiver.getError()) {
                ViewFactory.getInstance().showErrorMessage(receiver.getMessage());
            } else {
                Token.eraseJwtToken();
                ViewFactory.getInstance().showLoginWindow();
                ViewFactory.getInstance().closeStage(stage);
            }
        }
    }
}
