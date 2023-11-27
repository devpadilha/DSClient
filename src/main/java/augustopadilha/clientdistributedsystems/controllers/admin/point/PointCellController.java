package augustopadilha.clientdistributedsystems.controllers.admin.point;

import augustopadilha.clientdistributedsystems.models.Point;
import augustopadilha.clientdistributedsystems.system.connection.SendData;
import augustopadilha.clientdistributedsystems.views.MenuOptions;
import augustopadilha.clientdistributedsystems.views.ViewFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PointCellController implements Initializable {
    public Label name_lbl;
    public Label id_lbl;
    public Label obs_lbl;
    public Button edit_btn;
    public Button delete_btn;

    private final Point point;

    public PointCellController(Point point) {
        this.point = point;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        name_lbl.setText("Nome: " + point.getName());
        id_lbl.setText("Id: " + point.getId());
        obs_lbl.setText("Obs: " + point.getObs());

        edit_btn.setOnAction(event -> {
            try {
                onEditPoint();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });

        delete_btn.setOnAction(event -> {
            try {
                onDeletePoint();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void onEditPoint() throws JsonProcessingException {
        SendData sender = new SendData();
        sender.sendEditPointData(point);
        ViewFactory.getInstance().showEditPointWindow();
    }

    private void onDeletePoint() throws JsonProcessingException {
        try {
            ViewFactory.getInstance().showDeletePointView(point, (Stage) name_lbl.getScene().getWindow());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
