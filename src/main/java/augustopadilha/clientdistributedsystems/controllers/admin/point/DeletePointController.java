package augustopadilha.clientdistributedsystems.controllers.admin.point;

import augustopadilha.clientdistributedsystems.models.Point;
import augustopadilha.clientdistributedsystems.system.connection.ReceiveData;
import augustopadilha.clientdistributedsystems.system.connection.SendData;
import augustopadilha.clientdistributedsystems.views.MenuOptions;
import augustopadilha.clientdistributedsystems.views.ViewFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DeletePointController implements Initializable {
    public Button delete_btn;
    public Button return_btn;

    private Point pointToDelete;

    public void setPointToDelete(Point pointToDelete) {
        this.pointToDelete = pointToDelete;
    }

    public DeletePointController() {}

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
        ViewFactory.getInstance().getSelectedMenuItem().set(MenuOptions.POINTS_LIST);
    }

    void onDelete() throws JsonProcessingException {
        SendData sender = new SendData();
        sender.sendDeletePointData(pointToDelete.getId());
        ViewFactory.getInstance().closeStage((Stage) delete_btn.getScene().getWindow());
        JsonNode response = sender.sendListPointsData();
        if(response != null) {
            ReceiveData receiver = new ReceiveData(response);
            if(receiver.getError()) {
                ViewFactory.getInstance().showErrorMessage(receiver.getMessage());
            } else {
                receiver.getPointsList();
                ViewFactory.getInstance().getSelectedMenuItem().set(MenuOptions.POINTS_LIST);
            }
        }
    }
}
