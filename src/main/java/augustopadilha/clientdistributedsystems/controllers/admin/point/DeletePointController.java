package augustopadilha.clientdistributedsystems.controllers.admin.point;

import augustopadilha.clientdistributedsystems.models.Point;
import augustopadilha.clientdistributedsystems.system.connection.SendData;
import augustopadilha.clientdistributedsystems.views.MenuOptions;
import augustopadilha.clientdistributedsystems.views.ViewFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DeletePointController implements Initializable {
    public Button delete_btn;
    public Button return_btn;

    private Point point;
    private Point pointToDelete;
    public Stage pointsListStage;


    public void setPointToDelete(Point pointToDelete) {
        this.pointToDelete = pointToDelete;
    }
    public DeletePointController(Point point) {
        this.point = point;
    }
    public void setPointsListStage(Stage pointsListStage) {
        this.pointsListStage = pointsListStage;
    }

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
        sender.sendDeletePointData(point.getId());
        ViewFactory.getInstance().getSelectedMenuItem().set(MenuOptions.POINTS_LIST);
    }
}
