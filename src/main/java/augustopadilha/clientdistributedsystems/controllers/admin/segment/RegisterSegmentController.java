package augustopadilha.clientdistributedsystems.controllers.admin.segment;

import augustopadilha.clientdistributedsystems.models.Point;
import augustopadilha.clientdistributedsystems.models.Segment;
import augustopadilha.clientdistributedsystems.system.connection.ReceiveData;
import augustopadilha.clientdistributedsystems.system.connection.SendData;
import augustopadilha.clientdistributedsystems.views.ViewFactory;
import com.fasterxml.jackson.databind.JsonNode;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterSegmentController implements Initializable {
    public ChoiceBox origin_point_list;
    public ChoiceBox destiny_point_list;
    public ChoiceBox direction_selector;
    public TextField distance_field;
    public TextArea obs_area;
    public Button register_button;
    public Label error_label;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        origin_point_list.setItems(ViewFactory.getInstance().getPointsIds());
        destiny_point_list.setItems(ViewFactory.getInstance().getPointsIds());
        direction_selector.setItems(FXCollections.observableArrayList("Norte",
                                                                            "Sul",
                                                                            "Leste",
                                                                            "Oeste",
                                                                            "Nordeste",
                                                                            "Noroeste",
                                                                            "Sudeste",
                                                                            "Sudoeste"));
        register_button.setOnAction(event -> {
            try {
                onRegister();
            } catch (Exception e) {
                setError_label("Dados inválidos");
                throw new RuntimeException(e);
            }
        });
    }

    private void onRegister() throws Exception {
        Segment segment = new Segment((String) direction_selector.getValue(), distance_field.getText(), obs_area.getText(), augustopadilha.clientdistributedsystems.views.ViewFactory.getInstance().getPointById((Integer) origin_point_list.getValue()), ViewFactory.getInstance().getPointById((Integer) destiny_point_list.getValue()));
        SendData sender = new SendData();
        JsonNode response = sender.sendRegisterSegmentData(segment);
        if (response != null) {
            ReceiveData receiver = new ReceiveData(response);
            if (receiver.getError()) {
                ViewFactory.getInstance().showErrorMessage(receiver.getMessage());
            } else {
                distance_field.setText("");
                obs_area.setText("");
                error_label.setText("Segmento cadastrado com sucesso!");
            }
        }
    }

    private void setError_label(String message) {
        error_label.setText(message);
    }
}
