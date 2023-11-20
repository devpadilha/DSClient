package augustopadilha.clientdistributedsystems.controllers.admin;

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

public class AdminMenuController implements Initializable {
    public Button user_register_button;
    public Button users_btn;
    public Button profile_btn;
    public Button logout_btn;
    public static JsonNode response;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { addListeners(); }

    private void addListeners(){
        user_register_button.setOnAction(event -> onRegisterUser ());
        users_btn.setOnAction(event -> {
            try {
                onUsersList();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
        logout_btn.setOnAction(event -> {
            try {
                onLogout();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
        profile_btn.setOnAction(event -> {
            try {
                onProfile();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void onRegisterUser() {
        ViewFactory.getInstance().getSelectedMenuItem().set(MenuOptions.REGISTER_USER);
    }

    private void onUsersList() throws JsonProcessingException {
        SendData sender = new SendData();
        JsonNode response = sender.sendClientListData(Token.getJwtToken());
        if (response != null) {
            ReceiveData receiver = new ReceiveData(response);
            if (receiver.getError()) {
                ViewFactory.getInstance().showErrorMessage(receiver.getMessage());
            } else {
                receiver.getClientList();
                ViewFactory.getInstance().getSelectedMenuItem().set(MenuOptions.USERS_LIST);
            }
        }
    }

    private void onProfile() throws JsonProcessingException {
        SendData sender = new SendData();
        response = sender.sendProfileData(Token.getJwtToken());
        if (response != null) {
            ReceiveData receiver = new ReceiveData(response);
            receiver.getUserData();
            if (receiver.getError()) {
                ViewFactory.getInstance().showErrorMessage(receiver.getMessage());
            } else {
                ViewFactory.getInstance().getSelectedMenuItem().set(MenuOptions.PROFILE);
            }
        }
    }

    public void onLogout() throws JsonProcessingException {
        Stage stage = (Stage) logout_btn.getScene().getWindow();

        SendData sender = new SendData();
        JsonNode response = sender.sendLogoutData(Token.getJwtToken());
        if (response != null) {
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

    public static JsonNode getResponse() {
        return response;
    }
}
