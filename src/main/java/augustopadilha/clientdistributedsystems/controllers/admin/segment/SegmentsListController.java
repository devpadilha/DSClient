package augustopadilha.clientdistributedsystems.controllers.admin.segment;

import augustopadilha.clientdistributedsystems.models.Segment;
import augustopadilha.clientdistributedsystems.views.ClientCellFactory;
import augustopadilha.clientdistributedsystems.views.SegmentCellFactory;
import augustopadilha.clientdistributedsystems.views.ViewFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class SegmentsListController implements Initializable {
    public ListView<Segment> segment_list_view;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        segment_list_view.setItems(ViewFactory.getInstance().getSegments());
        segment_list_view.setCellFactory(studentListView -> new SegmentCellFactory());
        segment_list_view.refresh();
    }
}
