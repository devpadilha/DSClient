package augustopadilha.clientdistributedsystems.controllers;

import augustopadilha.clientdistributedsystems.JavaFXApp;
import augustopadilha.clientdistributedsystems.models.ConnectionModel;
import augustopadilha.clientdistributedsystems.views.ViewFactory;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ConnectController implements Initializable {
    @FXML
    private Button button_connect;
    @FXML
    private TextField tf_ip;
    @FXML
    private TextField tf_port;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tf_ip.setText("localhost");
        tf_port.setText("23000");
        button_connect.setOnAction(event -> openDialog(JavaFXApp.openConnection(tf_ip.getText(), tf_port.getText())));
    }

    public void openDialog(ConnectionModel result) {
        Platform.runLater(() -> {
            try {
                if (result != null && result.validate()) {
                    String ip = result.getIp();
                    String port = result.getPort();
                    try {
                        JavaFXApp.getConnection().connect(ip, port);

                        ViewFactory.getInstance().closeStage((Stage) button_connect.getScene().getWindow());
                        ViewFactory.getInstance().showLoginWindow();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    JavaFXApp.openConnectWindow();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
