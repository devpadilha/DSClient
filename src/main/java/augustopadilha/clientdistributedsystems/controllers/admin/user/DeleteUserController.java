package augustopadilha.clientdistributedsystems.controllers.admin.user;

import augustopadilha.clientdistributedsystems.models.User;
import augustopadilha.clientdistributedsystems.system.connection.ReceiveData;
import augustopadilha.clientdistributedsystems.system.connection.SendData;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class DeleteUserController implements Initializable {
    public Button delete_btn;
    public Button return_btn;

    private int user_id;

    public DeleteUserController(int user_id) {
        this.user_id = user_id;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        delete_btn.setOnAction(event -> {
            try {
                onDelete();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });

        return_btn.setOnAction(event -> {
            try {
                onReturn();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }
    void onDelete() throws JsonProcessingException {
        SendData sender = new SendData();
        ReceiveData receiver = new ReceiveData(sender.sendDeleteUserData(user_id));
    }

    void onReturn() throws JsonProcessingException {
    }
}
