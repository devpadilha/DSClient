package augustopadilha.clientdistributedsystems.controllers.common;

import augustopadilha.clientdistributedsystems.models.User;
import augustopadilha.clientdistributedsystems.system.connection.SendData;
import augustopadilha.clientdistributedsystems.system.utilities.Token;
import augustopadilha.clientdistributedsystems.system.utilities.UserCredentialsValidator;
import augustopadilha.clientdistributedsystems.views.MenuOptions;
import augustopadilha.clientdistributedsystems.views.ViewFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.commons.codec.digest.DigestUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class EditUserController implements Initializable {
    public TextField name_field;
    public TextField email_field;
    public TextField password_field;
    public Label error_lbl;
    public Button save_btn;
    public Button return_btn;
    private User editedUser;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        editedUser = ViewFactory.getInstance().getUser();

        save_btn.setOnAction(event -> {
            try {
                onSave();
            } catch (Exception e) {
                setError_label("Dados inválidos");
                throw new RuntimeException(e);
            }
        });
        return_btn.setOnAction(event -> onReturn());
    }

    public void onSave() throws JsonProcessingException {
        SendData sender = new SendData();
        editedUser.setName(name_field.getText());
        editedUser.setEmail(email_field.getText());
        String password = DigestUtils.md5Hex(password_field.getText()).toUpperCase();

        if(UserCredentialsValidator.validate(editedUser.getEmail(), password)) {
            sender.sendEditSelfData(Token.getJwtToken(), ViewFactory.getInstance().getUser().getID(), editedUser.getName(), editedUser.getEmail(), password);
        }
        ViewFactory.getInstance().getSelectedMenuItem().set(MenuOptions.PROFILE);
    }

    public void onReturn() {
        ViewFactory.getInstance().getSelectedMenuItem().set(MenuOptions.PROFILE);
    }

    private void setError_label(String message) {
        error_lbl.setText(message);
    }
}
