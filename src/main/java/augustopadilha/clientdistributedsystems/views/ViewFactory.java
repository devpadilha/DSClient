package augustopadilha.clientdistributedsystems.views;

import augustopadilha.clientdistributedsystems.controllers.common.DeleteUserController;
import augustopadilha.clientdistributedsystems.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.util.TimerTask;

public class ViewFactory {
    private static ViewFactory viewFactory = null;
    private ObservableList<User> clients = null;
    private boolean loginAccountType;
    private AnchorPane profileView;
    private AnchorPane editUserView;
    private AnchorPane deleteUserView;
    private User user = null;

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

    /* Funções para criar e mostrar janela, e fechar janela */
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/augustopadilha/clientdistributedsystems/fxmlfiles/unlogged/connect.fxml"));
        createStage(loader, "Conecte-se");
    }

    public void showLoginWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/augustopadilha/clientdistributedsystems/fxmlfiles/unlogged/login.fxml"));
        createStage(loader, "Login");
    }

    public void showRegisterWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/augustopadilha/clientdistributedsystems/fxmlfiles/unlogged/registeruser.fxml"));
        createStage(loader, "Registre-se");
    }

    public AnchorPane getProfileView() {
        if (profileView == null) {
            try {
                profileView = new FXMLLoader(getClass().getResource("/augustopadilha/clientdistributedsystems/fxmlfiles/common/profile.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return profileView;
    }

    public AnchorPane getEditUserView() {
        if (editUserView == null) {
            try {
                editUserView = new FXMLLoader(getClass().getResource("/augustopadilha/clientdistributedsystems/fxmlfiles/common/edituser.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return editUserView;
    }

    public void showDeleteUserView(Stage profileStage) {
        Stage deleteStage = new Stage();
        deleteStage.initModality(Modality.APPLICATION_MODAL);
        deleteStage.initOwner(profileStage);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/augustopadilha/clientdistributedsystems/fxmlfiles/common/deleteuser.fxml"));
            Parent root = loader.load();
            DeleteUserController controller = loader.getController();
            controller.setProfileStage(profileStage);

            Scene scene = new Scene(root);
            deleteStage.setScene(scene);
            deleteStage.setTitle("Confirmar exclusão");
            deleteStage.setResizable(false);
            deleteStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showEditUserWindow() {
        Stage stage = new Stage();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/augustopadilha/clientdistributedsystems/fxmlfiles/admin/edituser.fxml"));
            createStage(loader, "Editar usuário");
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            ObservableList<User> users = FXCollections.observableArrayList();
            ObjectMapper objectMapper = new ObjectMapper();
            for (JsonNode clientNode : jsonNode) {
                User client = objectMapper.treeToValue(clientNode, User.class);
                users.add(client);
            }
            this.clients = users;
        }
    }

    public User getUser() {
        if(user == null) {
            user = new User();
        }
        return user;
    }

    public void setUser(JsonNode jsonNode) throws JsonProcessingException {
        if (jsonNode != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            this.user = objectMapper.treeToValue(jsonNode, User.class);
        }
    }

    public void resetAllAnchorPanes() {
        profileView = null;
        editUserView = null;
        deleteUserView = null;
        registerUserView = null;
        usersListView = null;
        //editUserADMView = null;
        //deleteUserADMView = null;
    }
}
