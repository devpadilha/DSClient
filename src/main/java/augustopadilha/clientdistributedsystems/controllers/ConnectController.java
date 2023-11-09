package augustopadilha.clientdistributedsystems.controllers;

import augustopadilha.clientdistributedsystems.JavaFXApp;
import augustopadilha.clientdistributedsystems.models.ConnectionModel;
import augustopadilha.clientdistributedsystems.models.Model;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
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

                        Model.getInstance().getViewFactory().closeStage((Stage) button_connect.getScene().getWindow());
                        Model.getInstance().getViewFactory().showLoginWindow();
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
