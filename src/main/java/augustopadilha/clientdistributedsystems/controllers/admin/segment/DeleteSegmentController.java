package augustopadilha.clientdistributedsystems.controllers.admin.segment;

import augustopadilha.clientdistributedsystems.models.Segment;
import augustopadilha.clientdistributedsystems.system.connection.ReceiveData;
import augustopadilha.clientdistributedsystems.system.connection.SendData;
import augustopadilha.clientdistributedsystems.views.MenuOptions;
import augustopadilha.clientdistributedsystems.views.ViewFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DeleteSegmentController implements Initializable {
    public Button delete_btn;
    public Button return_btn;

    private Segment segmentToDelete;

    public void setSegmentToDelete(Segment segmentToDelete) {
        this.segmentToDelete = segmentToDelete;
    }

    public DeleteSegmentController() {}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        delete_btn.setOnAction(event -> {
            try {
                onDelete();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });

        return_btn.setOnAction(event -> {
            try {
                onReturn();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    void onReturn() {
        ViewFactory.getInstance().closeStage((Stage) delete_btn.getScene().getWindow());
        ViewFactory.getInstance().getSelectedMenuItem().set(MenuOptions.SEGMENTS_LIST);
    }

    void onDelete() throws JsonProcessingException {
        SendData sender = new SendData();
        sender.sendDeleteSegmentData(segmentToDelete.getId());
        ViewFactory.getInstance().closeStage((Stage) delete_btn.getScene().getWindow());
        JsonNode response = sender.sendListPointsData();
        if(response != null) {
            ReceiveData receiver = new ReceiveData(response);
            if(receiver.getError()) {
                ViewFactory.getInstance().showErrorMessage(receiver.getMessage());
            } else {
                receiver.getPointsList();
                ViewFactory.getInstance().getSelectedMenuItem().set(MenuOptions.SEGMENTS_LIST);
            }
        }
    }
}
