package augustopadilha.clientdistributedsystems.controllers.admin.point;

import augustopadilha.clientdistributedsystems.models.Point;
import augustopadilha.clientdistributedsystems.system.connection.ReceiveData;
import augustopadilha.clientdistributedsystems.system.connection.SendData;
import augustopadilha.clientdistributedsystems.views.MenuOptions;
import augustopadilha.clientdistributedsystems.views.ViewFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EditPointController implements Initializable {
    public TextField name_field;
    public TextField obs_field;
    public Button return_btn;
    public Button save_btn;
    public Label error_lbl;
    private Point editedPoint;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        editedPoint = ViewFactory.getInstance().getPoint();

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
        editedPoint.setName(name_field.getText());
        editedPoint.setObs(obs_field.getText());
        editedPoint.setId(ViewFactory.getInstance().getPoint().getId());
        sender.sendEditPointData(editedPoint);
        ViewFactory.getInstance().closeStage((Stage) save_btn.getScene().getWindow());
        ViewFactory.getInstance().getSelectedMenuItem().set(MenuOptions.POINTS_LIST);
        JsonNode response = sender.sendListPointsData();
        if(response != null) {
            ReceiveData receiver = new ReceiveData(response);
            if (receiver.getError()) {
                ViewFactory.getInstance().showErrorMessage(receiver.getMessage());
            } else {
                receiver.getPointsList();
                ViewFactory.getInstance().getSelectedMenuItem().set(MenuOptions.POINTS_LIST);
            }
        }
    }

    public void onReturn() {
        ViewFactory.getInstance().getSelectedMenuItem().set(MenuOptions.POINTS_LIST);
    }

    private void setError_label(String message) {
        error_lbl.setText(message);
    }
}
