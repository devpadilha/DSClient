package augustopadilha.clientdistributedsystems.controllers.admin.segment;

import augustopadilha.clientdistributedsystems.models.Point;
import augustopadilha.clientdistributedsystems.models.Segment;
import augustopadilha.clientdistributedsystems.system.connection.ReceiveData;
import augustopadilha.clientdistributedsystems.system.connection.SendData;
import augustopadilha.clientdistributedsystems.views.MenuOptions;
import augustopadilha.clientdistributedsystems.views.ViewFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EditSegmentController implements Initializable {
    public TextField direction_field;
    public TextField distance_field;
    public TextField obs_field;
    public TextField origin_point_id_field;
    public TextField destiny_point_id_field;
    public Label error_lbl;
    public Button return_btn;
    public Button save_btn;

    private Segment editedSegment;
    private Point originPoint;
    private Point destinyPoint;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        editedSegment = ViewFactory.getInstance().getSegment();

        save_btn.setOnAction(event -> {
            try {
                onSave();
            } catch (Exception e) {
                setError_label("Dados inválidos");
                throw new RuntimeException(e);
            }
        });
        return_btn.setOnAction(event -> onReturn());
    }

    public void onSave() throws JsonProcessingException {
        SendData sender = new SendData();
        editedSegment.setId(ViewFactory.getInstance().getSegment().getId());
        editedSegment.setDirection(direction_field.getText());
        editedSegment.setDistance(distance_field.getText());
        editedSegment.setObs(obs_field.getText());

        ReceiveData listPointsResponse = new ReceiveData(sender.sendListPointsData());
        if(listPointsResponse.getError()) {
            setError_label(listPointsResponse.getMessage());
        }
        listPointsResponse.getPointsList();
        editedSegment.setOriginPoint(ViewFactory.getInstance().getPointById(Integer.parseInt(origin_point_id_field.getText())));
        editedSegment.setDestinyPoint(ViewFactory.getInstance().getPointById(Integer.parseInt(destiny_point_id_field.getText())));


        sender.sendEditSegmentData(editedSegment);
        ViewFactory.getInstance().closeStage((Stage) save_btn.getScene().getWindow());
        ViewFactory.getInstance().getSelectedMenuItem().set(MenuOptions.SEGMENTS_LIST);
        JsonNode response = sender.sendListSegmentsData();
        if(response != null) {
            ReceiveData receiver = new ReceiveData(response);
            if (receiver.getError()) {
                ViewFactory.getInstance().showErrorMessage(receiver.getMessage());
            } else {
                receiver.getPointsList();
                ViewFactory.getInstance().getSelectedMenuItem().set(MenuOptions.SEGMENTS_LIST);
            }
        }
    }

    public void onReturn() {
        ViewFactory.getInstance().getSelectedMenuItem().set(MenuOptions.SEGMENTS_LIST);
    }

    private void setError_label(String message) {
        error_lbl.setText(message);
    }
}
