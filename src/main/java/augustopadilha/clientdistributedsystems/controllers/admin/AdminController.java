package augustopadilha.clientdistributedsystems.controllers.admin;

import augustopadilha.clientdistributedsystems.views.ViewFactory;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    public BorderPane admin_parent;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ViewFactory.getInstance().getSelectedMenuItem().addListener((observable, oldValue, newValue) -> {
            ViewFactory.getInstance().resetAllAnchorPanes();
            switch (newValue) {
                case USERS_LIST:
                    admin_parent.setCenter(ViewFactory.getInstance().getUsersListView());
                    break;

                    case POINTS_LIST:
                        admin_parent.setCenter(ViewFactory.getInstance().getPointsListView());
                        break;

                    case SEGMENTS_LIST:
                        admin_parent.setCenter(ViewFactory.getInstance().getSegmentsListView());
                        break;

                    case PROFILE:
                        admin_parent.setCenter(ViewFactory.getInstance().getProfileView());
                        break;

                    case EDIT_USER:
                        admin_parent.setCenter(ViewFactory.getInstance().getEditUserView());
                        break;

                    case DELETE_USER_ADM:
                        //admin_parent.setCenter(ViewFactory.getInstance().getDeleteUserAdmView());
                        break;

                        default:
                            admin_parent.setCenter(ViewFactory.getInstance().getRegisterUserView());
                            break;
            }
        });
    }
}
