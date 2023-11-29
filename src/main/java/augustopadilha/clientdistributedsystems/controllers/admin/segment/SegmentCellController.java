package augustopadilha.clientdistributedsystems.controllers.admin.segment;

import augustopadilha.clientdistributedsystems.models.Point;
import augustopadilha.clientdistributedsystems.models.Segment;
import augustopadilha.clientdistributedsystems.views.ViewFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class SegmentCellController implements Initializable {
    public Label direction_lbl;
    public Label distance_lbl;
    public Label id_lbl;
    public Label obs_lbl;
    public Label origin_point_name_lbl;
    public Label origin_point_id_lbl;
    public Label origin_point_obs_lbl;
    public Label destiny_point_name_lbl;
    public Label destiny_point_id_lbl;
    public Label destiny_point_obs_lbl;
    public Button edit_btn;
    public Button delete_btn;

    private final Segment segment;
    private final Point originPOint;
    private final Point destinyPOint;

    public SegmentCellController(Segment segment, Point originPoint, Point destinyPoint) {
        this.segment = segment;
        this.originPOint = originPoint;
        this.destinyPOint = destinyPoint;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        direction_lbl.setText("Direção: " + segment.getDirection());
        distance_lbl.setText("Distância: " + segment.getDistance());
        id_lbl.setText("Id: " + segment.getId());
        obs_lbl.setText("Obs: " + (segment.getObs() == null ? "" : segment.getObs()));

        origin_point_name_lbl.setText("Nome: " + originPOint.getName());
        origin_point_id_lbl.setText("Id: " + originPOint.getId());
        origin_point_obs_lbl.setText("Obs: " + (originPOint.getObs() == null ? "" : originPOint.getObs()));

        destiny_point_name_lbl.setText("Nome: " + destinyPOint.getName());
        destiny_point_id_lbl.setText("Id: " + destinyPOint.getId());
        destiny_point_obs_lbl.setText("Obs: " + (destinyPOint.getObs() == null ? "" : destinyPOint.getObs()));

        edit_btn.setOnAction(event -> {
            try {
                onEditSegment();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });

        delete_btn.setOnAction(event -> {
            try {
                onDeleteSegment();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void onEditSegment() throws JsonProcessingException {
        ViewFactory.getInstance().setSegment(segment);
        ViewFactory.getInstance().showEditSegmentWindow();
    }

    private void onDeleteSegment() throws JsonProcessingException {
        try {
            ViewFactory.getInstance().showDeleteSegmentView(segment);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
