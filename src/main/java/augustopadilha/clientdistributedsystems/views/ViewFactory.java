package augustopadilha.clientdistributedsystems.views;

import augustopadilha.clientdistributedsystems.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;

public class ViewFactory {
    private static ViewFactory viewFactory = null;
    private ObservableList<User> clients = null;
    private boolean loginAccountType;
    private AnchorPane profileView;
    private User clientUser;

    private User user;

    // Common views
    private final ObjectProperty<MenuOptions> SelectedMenuItem;

    // Client views
    private AnchorPane dashboardView;

    // Admin views
    private AnchorPane registerUserView;
    private AnchorPane usersListView;

    public ViewFactory() {
        this.loginAccountType = false;
        this.SelectedMenuItem = new SimpleObjectProperty<>();
    }
    /* Funções para criar, mostrar e fechar janelas */
    private void createStage(FXMLLoader loader, String title) {
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle(title);
        stage.show();
    }

    public void closeStage(Stage stage) {
        if (stage != null) {
            stage.close();
        }
    }
    /*--------------------------------------------------*/

    /* ---------------- Common views ------------------ */
    public ObjectProperty<MenuOptions> getSelectedMenuItem() {
        return SelectedMenuItem;
    }

    public void showConnectWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/augustopadilha/clientdistributedsystems/fxmlfiles/connect.fxml"));
        createStage(loader, "Conecte-se");
    }

    public void showLoginWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/augustopadilha/clientdistributedsystems/fxmlfiles/login.fxml"));
        createStage(loader, "Login");
    }

    public void showRegisterWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/augustopadilha/clientdistributedsystems/fxmlfiles/registeruser.fxml"));
        createStage(loader, "Registre-se");
    }

    public AnchorPane getProfileView() {
        if (profileView == null) {
            try {
                profileView = new FXMLLoader(getClass().getResource("/augustopadilha/clientdistributedsystems/fxmlfiles/profile.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return profileView;
    }
    /*-------------------------------------------------*/

    /*------------------ User views ------------------*/
    public void showUserWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/augustopadilha/clientdistributedsystems/fxmlfiles/user/user.fxml"));
        createStage(loader, "Bem-vindo!");
    }

    public AnchorPane getDashboardView() {
        if (dashboardView == null) {
            try {
                dashboardView = new FXMLLoader(getClass().getResource("/augustopadilha/clientdistributedsystems/fxmlfiles/user/dashboard.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dashboardView;
    }
    /* ------------------------------------------------- */

    /*------------------ Admin views ------------------*/
    public void showAdminWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/augustopadilha/clientdistributedsystems/fxmlfiles/admin/admin.fxml"));
        createStage(loader, "Bem-vindo!");
    }

    public AnchorPane getRegisterUserView() {
        if (registerUserView == null) {
            try {
                registerUserView = new FXMLLoader(getClass().getResource("/augustopadilha/clientdistributedsystems/fxmlfiles/admin/registeruseradm.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return registerUserView;
    }

    public AnchorPane getUsersListView() {
        if (usersListView == null) {
            try {
                usersListView = new FXMLLoader(getClass().getResource("/augustopadilha/clientdistributedsystems/fxmlfiles/admin/userslist.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return usersListView;
    }
    /*-------------------------------------------------*/
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Erro", JOptionPane.ERROR_MESSAGE);
    }
    public static synchronized ViewFactory getInstance() {
        if (viewFactory == null) {
            viewFactory = new ViewFactory();
        }
        return viewFactory;
    }

    public ObservableList<User> getClients() {
        if (clients == null) {
            clients = FXCollections.observableArrayList();
        }
        return clients;
    }

    public void setClients(JsonNode jsonNode) throws JsonProcessingException {
        if (jsonNode != null && jsonNode.isArray()) {
            ObservableList<User> users = FXCollections.observableArrayList( );
            ObjectMapper objectMapper = new ObjectMapper();
            for (JsonNode clientNode : jsonNode) {
                User client = objectMapper.treeToValue(clientNode, User.class);
                users.add(client);
            }
            this.clients = users;
        }
    }

    public void resetAllAnchorPanes() {
        profileView = null;
        //editUserView = null;
        //deleteUserView = null;
        registerUserView = null;
        usersListView = null;
        //editUserADMView = null;
        //deleteUserADMView = null;
    }

    public void setClientUser(User clientUser) {
        this.clientUser = clientUser;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
