package augustopadilha.clientdistributedsystems.controllers.admin.user;

import augustopadilha.clientdistributedsystems.models.User;
import augustopadilha.clientdistributedsystems.views.ClientCellFactory;
import augustopadilha.clientdistributedsystems.views.ViewFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class UsersListController implements Initializable {
    public ListView<User> user_list_view;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user_list_view.setItems(ViewFactory.getInstance().getClients());
        user_list_view.setCellFactory(studentListView -> new ClientCellFactory());
        user_list_view.refresh();
    }
}
