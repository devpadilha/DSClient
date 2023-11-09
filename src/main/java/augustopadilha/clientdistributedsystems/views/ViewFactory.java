package augustopadilha.clientdistributedsystems.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;

public class ViewFactory {
    /* Funções para criar, mostrar e fechar janelas */
    public Stage createStage(FXMLLoader loader, String title) {
        try {
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(title);
            return stage;
        } catch (Exception e) {
            // Lide com a exceção de maneira adequada (por exemplo, lançando uma exceção personalizada)
            e.printStackTrace();
            return null;
        }
    }

    public void showStage(Stage stage) {
        if (stage != null) {
            stage.show();
        }
    }

    public void closeStage(Stage stage) {
        if (stage != null) {
            stage.close();
        }
    }
    /*--------------------------------------------------*/

    /* ---------------- Common views ------------------ */
    public void showConnectWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/augustopadilha/clientdistributedsystems/fxmlfiles/connect.fxml"));
        Stage stage = createStage(loader, "Conecte-se");
        showStage(stage);
    }

    public void showLoginWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/augustopadilha/clientdistributedsystems/fxmlfiles/login.fxml"));
        Stage stage = createStage(loader, "Login");
        showStage(stage);
    }

    public void showRegisterWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/augustopadilha/clientdistributedsystems/fxFiles/registeruser.fxml"));
        createStage(loader, "Registre-se!");
    }
    /*-------------------------------------------------*/

    /*--------------- Common User views ---------------*/
    public void showUserWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/augustopadilha/clientdistributedsystems/fxFiles/user/user.fxml"));
        createStage(loader, "Bem-vindo!");
    }
    /*-------------------------------------------------*/

    /*------------------ Admin views ------------------*/
    public void showAdminWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/augustopadilha/clientdistributedsystems/fxFiles/admin/admin.fxml"));
        createStage(loader, "Bem-vindo!");
    }
    /*-------------------------------------------------*/
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
