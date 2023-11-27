package augustopadilha.clientdistributedsystems.views;

import augustopadilha.clientdistributedsystems.controllers.admin.point.PointCellController;
import augustopadilha.clientdistributedsystems.models.Point;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class PointCellFactory extends ListCell<Point> {
    @Override
    protected void updateItem(Point point, boolean empty) {
        super.updateItem(point, empty);
        if(empty || point == null) {
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/augustopadilha/clientdistributedsystems/fxmlfiles/admin/point/pointcell.fxml"));
            PointCellController controller = new PointCellController(point);
            fxmlLoader.setController(controller);
            setText(null);
            try {
                setGraphic(fxmlLoader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
