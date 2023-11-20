package augustopadilha.clientdistributedsystems.controllers.common;

import augustopadilha.clientdistributedsystems.models.User;
import augustopadilha.clientdistributedsystems.system.connection.ReceiveData;
import augustopadilha.clientdistributedsystems.system.connection.SendData;
import augustopadilha.clientdistributedsystems.system.utilities.Token;
import augustopadilha.clientdistributedsystems.views.ViewFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class DeleteUserController implements Initializable {
    public TextField email_field;
    public TextField password_field;
    public Button delete_btn;
    public Label error_lbl;

    private User user;
    protected Stage profileStage;

    public void setProfileStage(Stage profileStage) {
        this.profileStage = profileStage;
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
    }

    void onDelete() throws JsonProcessingException {
        String email = email_field.getText();
        String password = DigestUtils.md5Hex(password_field.getText()).toUpperCase();

        if (email.isEmpty()) {
            error_lbl.setText("Preencha o email!");
            return;
        }

        else if (password.isEmpty()) {
            error_lbl.setText("Preencha a senha!");
            return;
        }
        user = ViewFactory.getInstance().getUser();

        if(!user.getEmail().equals(email)) {
            error_lbl.setText("Email incorreto!");
            return;
        }

        SendData sender = new SendData();

        ReceiveData receiver = new ReceiveData(sender.sendDeleteSelfData(Token.getJwtToken(), email, password));
        if(receiver.getError())
            error_lbl.setText(receiver.getMessage());

        else {
            profileStage.close();
            ViewFactory.getInstance().showLoginWindow();
        }
    }
}
