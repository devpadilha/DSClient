package augustopadilha.clientdistributedsystems.controllers.admin;

import augustopadilha.clientdistributedsystems.views.ViewFactory;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    public BorderPane admin_parent;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ViewFactory.getInstance().getAdminSelectedMenuItem().addListener((observableValue, oldValue, newValue) -> {
            switch (newValue) {
                case REGISTER_USER:
                    admin_parent.setCenter(ViewFactory.getInstance().getRegisterUserView());
                    break;
                case USERS_LIST:
                    admin_parent.setCenter(ViewFactory.getInstance().getUsersListView());
                    break;
                case PROFILE:
                    admin_parent.setCenter(ViewFactory.getInstance().getProfileView());
                    break;
                default:
                    break;
            }
        });
    }
}
