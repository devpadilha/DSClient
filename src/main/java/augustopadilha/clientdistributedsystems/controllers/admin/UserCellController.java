package augustopadilha.clientdistributedsystems.controllers.admin;

import augustopadilha.clientdistributedsystems.models.User;
import augustopadilha.clientdistributedsystems.system.connection.SendData;
import augustopadilha.clientdistributedsystems.system.utilities.Token;
import augustopadilha.clientdistributedsystems.views.MenuOptions;
import augustopadilha.clientdistributedsystems.views.ViewFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class UserCellController implements Initializable {
    public Label name_lbl;
    public Label type_lbl;
    public Label email_lbl;
    public Label id_lbl;
    public Button edit_button;

    private final User client;

    public UserCellController(User client) {
        this.client = client;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        id_lbl.setText("ID: " + client.getID());
        name_lbl.setText("Nome: " + client.getName());
        type_lbl.setText("Tipo: " + (client.getType().equals("admin") ? "Administrador" : "Usuário"));
        email_lbl.setText("Email: " + client.getEmail());
        edit_button.setOnAction(event -> {
            try {
                onEditUser();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void onEditUser() throws JsonProcessingException {
        SendData sender = new SendData();
        sender.sendEditUserData(client.getID());
        ViewFactory.getInstance().showEditUserWindow();
    }
}
