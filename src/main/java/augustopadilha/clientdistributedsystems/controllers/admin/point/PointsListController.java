package augustopadilha.clientdistributedsystems.controllers.admin.point;

import augustopadilha.clientdistributedsystems.models.Point;
import augustopadilha.clientdistributedsystems.views.PointCellFactory;
import augustopadilha.clientdistributedsystems.views.ViewFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class PointsListController implements Initializable {
    public ListView<Point> point_list_view;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        point_list_view.setItems(ViewFactory.getInstance().getPoints());
        point_list_view.setCellFactory(studentListView -> new PointCellFactory());
        point_list_view.refresh();
    }

    // DA PRA TENTAR CHAMAR REFRESH MANUALMENTE OU ENTENDER O PORQUE DE NAO ESTAR ATUALIZANDO AUTOMATICAMENTE

    // TENTAR CHAMAR REFRESH AQUI
    /*
    public void refreshListView() {
        point_list_view.refresh();
    }
    */
}
