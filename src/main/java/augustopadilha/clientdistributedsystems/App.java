package augustopadilha.clientdistributedsystems;

import augustopadilha.clientdistributedsystems.models.ConnectionModel;
import augustopadilha.clientdistributedsystems.system.connection.Connection;
import augustopadilha.clientdistributedsystems.views.ViewFactory;
import javafx.application.Application;
import javafx.stage.Stage;


public class App extends Application {
    private static Connection connection = new Connection();

    @Override
    public void start(Stage stage) {
        openConnectWindow();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void openConnectWindow() {
        ViewFactory.getInstance().showConnectWindow();
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
