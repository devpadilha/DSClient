package augustopadilha.clientdistributedsystems;

import augustopadilha.clientdistributedsystems.models.ConnectionModel;
import augustopadilha.clientdistributedsystems.models.Model;
import augustopadilha.clientdistributedsystems.system.connection.Connection;
import javafx.application.Application;
import javafx.stage.Stage;


public class JavaFXApp extends Application {
    private static Connection connection = new Connection();
    private static JavaFXApp instance;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        instance = this;
        openConnectWindow();
    }

    public static void openConnectWindow() {
        Model.getInstance().getViewFactory().showConnectWindow();
    }

    public static ConnectionModel openConnection(String ip, String port) {
        try {
            return new ConnectionModel(ip, port);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
