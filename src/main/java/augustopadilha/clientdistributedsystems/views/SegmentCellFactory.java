package augustopadilha.clientdistributedsystems.views;

import augustopadilha.clientdistributedsystems.controllers.admin.segment.SegmentCellController;
import augustopadilha.clientdistributedsystems.models.Segment;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class SegmentCellFactory extends ListCell<Segment> {
    @Override
    protected void updateItem(Segment segment, boolean empty) {
        super.updateItem(segment, empty);
        if(empty || segment == null) {
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/augustopadilha/clientdistributedsystems/fxmlfiles/admin/segment/segmentcell.fxml"));
            SegmentCellController controller = new SegmentCellController(segment, segment.getOriginPoint(), segment.getDestinyPoint());
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
