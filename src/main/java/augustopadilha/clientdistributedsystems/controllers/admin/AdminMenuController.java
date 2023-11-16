package augustopadilha.clientdistributedsystems.controllers.admin;

import augustopadilha.clientdistributedsystems.system.connection.ReceiveData;
import augustopadilha.clientdistributedsystems.system.connection.SendData;
import augustopadilha.clientdistributedsystems.system.utilities.Token;
import augustopadilha.clientdistributedsystems.views.AdminMenuOptions;
import augustopadilha.clientdistributedsystems.views.ViewFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {
    public Button user_register_button;
    public Button users_btn;
    public Button profile_btn;
    public Button logout_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { addListeners(); }

    private void addListeners(){
        user_register_button.setOnAction(event -> onRegisterUser ());
        users_btn.setOnAction(event -> onUsersList());
        logout_btn.setOnAction(event -> {
            try {
                onLogout();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
        // profile_btn.setOnAction(event -> onProfile());
    }

    private void onRegisterUser() {
        ViewFactory.getInstance().getAdminSelectedMenuItem().set(AdminMenuOptions.REGISTER_USER);
    }

    private void onUsersList() {
        ViewFactory.getInstance().getAdminSelectedMenuItem().set(AdminMenuOptions.USERS_LIST);
    }

    private void onProfile() {
        ViewFactory.getInstance().getAdminSelectedMenuItem().set(AdminMenuOptions.PROFILE);
    }

    public void onLogout() throws JsonProcessingException {
        Stage stage = (Stage) logout_btn.getScene().getWindow();

        SendData sender = new SendData();
        String response = sender.sendLogoutData(Token.getJwtToken());
        if (response != null) {
            try {
                 ReceiveData receiver = new ReceiveData(ReceiveData.stringToMap(response));
                 if (receiver.getError()) {
                     ViewFactory.getInstance().showErrorMessage(receiver.getMessage());
                 } else {
                     Token.eraseJwtToken();
                     ViewFactory.getInstance().showLoginWindow();
                     ViewFactory.getInstance().closeStage(stage);
                 }
             } catch (JsonProcessingException e) {
                ViewFactory.getInstance().showErrorMessage(e.getMessage());
             }
         }
    }
}
