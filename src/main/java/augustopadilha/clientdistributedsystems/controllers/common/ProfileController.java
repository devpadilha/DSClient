package augustopadilha.clientdistributedsystems.controllers.common;

import augustopadilha.clientdistributedsystems.models.User;
import augustopadilha.clientdistributedsystems.system.connection.ReceiveData;
import augustopadilha.clientdistributedsystems.views.MenuOptions;
import augustopadilha.clientdistributedsystems.views.ViewFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    public Label name_lbl;
    public Label email_lbl;
    public Label id_lbl;
    public Label type_lbl;
    public Button edit_btn;
    public Button delete_btn;

    private User user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        user = ViewFactory.getInstance().getUser();

        name_lbl.setText(user.getName());
        email_lbl.setText("Email: " + user.getEmail());
        id_lbl.setText(String.valueOf("ID: " + user.getID()));
        type_lbl.setText(user.getType());

        edit_btn.setOnAction(event -> onEdit());
        delete_btn.setOnAction(event -> {
            try {
                onDelete();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void onEdit() {
        ViewFactory.getInstance().getSelectedMenuItem().set(MenuOptions.EDIT_USER);
    }

    private void onDelete() throws JsonProcessingException {
        DeleteUserController deleteUserController = new DeleteUserController();
        deleteUserController.setProfileStage((Stage) delete_btn.getScene().getWindow());
        ViewFactory.getInstance().showDeleteUserView(deleteUserController.profileStage);
    }
}
