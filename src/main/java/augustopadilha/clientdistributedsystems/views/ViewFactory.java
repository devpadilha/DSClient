package augustopadilha.clientdistributedsystems.views;

import augustopadilha.clientdistributedsystems.controllers.admin.point.DeletePointController;
import augustopadilha.clientdistributedsystems.controllers.admin.segment.DeleteSegmentController;
import augustopadilha.clientdistributedsystems.controllers.common.DeleteSelfController;
import augustopadilha.clientdistributedsystems.models.Point;
import augustopadilha.clientdistributedsystems.models.Segment;
import augustopadilha.clientdistributedsystems.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ViewFactory {
    private static ViewFactory viewFactory = null;
    private ObservableList<User> clients = null;
    private ObservableList<Point> points = null;
    private ObservableList<Segment> segments = null;
    private boolean loginAccountType;
    private AnchorPane profileView;
    private AnchorPane editUserView;
    private AnchorPane routeView;
    private User user = null;
    private Point point = null;
    private Segment segment = null;

    // Common views
    private final ObjectProperty<MenuOptions> SelectedMenuItem;

    // Client views
    private AnchorPane dashboardView;

    // Admin views
    private AnchorPane registerUserView;
    private AnchorPane usersListView;
    private AnchorPane pointsListView;
    private AnchorPane segmentsListView;
    private AnchorPane editPageView;

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

    public void showDeleteSelfView(Stage profileStage) {
        Stage deleteStage = new Stage();
        deleteStage.initModality(Modality.APPLICATION_MODAL);
        deleteStage.initOwner(profileStage);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/augustopadilha/clientdistributedsystems/fxmlfiles/common/deleteuser.fxml"));
            Parent root = loader.load();
            DeleteSelfController controller = loader.getController();
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

    public void showDeletePointView(Point point) {
        Stage deleteStage = new Stage();
        deleteStage.initModality(Modality.APPLICATION_MODAL);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/augustopadilha/clientdistributedsystems/fxmlfiles/admin/point/deletepoint.fxml"));
            Parent root = loader.load();

            DeletePointController controller = loader.getController();
            controller.setPointToDelete(point);

            Scene scene = new Scene(root);
            deleteStage.setScene(scene);
            deleteStage.setTitle("Confirmar exclusão");
            deleteStage.setResizable(false);
            deleteStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showDeleteSegmentView(Segment segment) {
        Stage deleteStage = new Stage();
        deleteStage.initModality(Modality.APPLICATION_MODAL);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/augustopadilha/clientdistributedsystems/fxmlfiles/admin/segment/deletesegment.fxml"));
            Parent root = loader.load();

            DeleteSegmentController controller = loader.getController();
            controller.setSegmentToDelete(segment);

            Scene scene = new Scene(root);
            deleteStage.setScene(scene);
            deleteStage.setTitle("Confirmar exclusão");
            deleteStage.setResizable(false);
            deleteStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showEditSegmentWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/augustopadilha/clientdistributedsystems/fxmlfiles/admin/segment/editsegment.fxml"));
            createStage(loader, "Editar segmento");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void showEditPointWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/augustopadilha/clientdistributedsystems/fxmlfiles/admin/point/editpoint.fxml"));
            createStage(loader, "Editar ponto");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showEditUserWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/augustopadilha/clientdistributedsystems/fxmlfiles/admin/edituser.fxml"));
            createStage(loader, "Editar usuário");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*=========================================================== TEP 4 =========================================================================================*/
    public AnchorPane getShowRouteView() {
        if (routeView == null) {
            try {
                routeView = new FXMLLoader(getClass().getResource("/augustopadilha/clientdistributedsystems/fxmlfiles/common/showroute.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return routeView;
    }
    /*=============================================================================================================================================================*/
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

    public AnchorPane getRegisterPageView() {
        if (editPageView == null) {
            try {
                editPageView = new FXMLLoader(getClass().getResource("/augustopadilha/clientdistributedsystems/fxmlfiles/admin/registerpage.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return editPageView;
    }

    public AnchorPane getRegisterUserView() {
        if (registerUserView == null) {
            try {
                registerUserView = new FXMLLoader(getClass().getResource("/augustopadilha/clientdistributedsystems/fxmlfiles/admin/user/registeruser.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return registerUserView;
    }

    public AnchorPane getRegisterPointView() {
        if (registerUserView == null) {
            try {
                registerUserView = new FXMLLoader(getClass().getResource("/augustopadilha/clientdistributedsystems/fxmlfiles/admin/point/registerpoint.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return registerUserView;
    }

    public AnchorPane getRegisterSegmentView() {
        if (registerUserView == null) {
            try {
                registerUserView = new FXMLLoader(getClass().getResource("/augustopadilha/clientdistributedsystems/fxmlfiles/admin/segment/registersegment.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return registerUserView;
    }

    public AnchorPane getUsersListView() {
        if (usersListView == null) {
            try {
                usersListView = new FXMLLoader(getClass().getResource("/augustopadilha/clientdistributedsystems/fxmlfiles/admin/user/userslist.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return usersListView;
    }

    public AnchorPane getPointsListView() {
        if (pointsListView == null) {
            try {
                pointsListView = new FXMLLoader(getClass().getResource("/augustopadilha/clientdistributedsystems/fxmlfiles/admin/point/pointslist.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return pointsListView;
    }

    public AnchorPane getSegmentsListView() {
        if (segmentsListView == null) {
            try {
                segmentsListView = new FXMLLoader(getClass().getResource("/augustopadilha/clientdistributedsystems/fxmlfiles/admin/segment/segmentslist.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return segmentsListView;
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

    public Point getPoint() {
        if(point == null) {
            point = new Point();
        }
        return point;
    }

    public Segment getSegment() {
        if(segment == null) {
            segment = new Segment();
        }
        return segment;
    }



    public void setUser(JsonNode jsonNode) throws JsonProcessingException {
        if (jsonNode != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            this.user = objectMapper.treeToValue(jsonNode, User.class);
        }
    }

    public ObservableList<Point> getPoints() {
        if (points == null) {
            points = FXCollections.observableArrayList();
        }
        return points;
    }

    public ObservableList<Object> getPointsIds() {
        if(points == null) {
            points = FXCollections.observableArrayList();
        }
        // Criar uma lista de strings contendo os IDs
        // Adicionar objetos Point e IDs à lista
        List<Object> pointIds = new ArrayList<>();
        //pointIds.addAll(points);
        pointIds.addAll(points.stream().map(Point::getId).collect(Collectors.toList()));

        return FXCollections.observableArrayList(pointIds);
    }

    public Point getPointById(int id) {
        if(points == null) {
            points = FXCollections.observableArrayList();
        }
        return points.stream()
                .filter(point -> point.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void setPoints(JsonNode jsonNode) throws JsonProcessingException {
        if (jsonNode != null && jsonNode.isArray()) {
            ObservableList<Point> points = FXCollections.observableArrayList();
            ObjectMapper objectMapper = new ObjectMapper();
            for (JsonNode pointNode : jsonNode) {
                Point point = objectMapper.treeToValue(pointNode, Point.class);
                points.add(point);
            }
            this.points = points;
        }
    }

    public void setSegments(JsonNode jsonNode) throws JsonProcessingException {
        if (jsonNode != null && jsonNode.isArray()) {
            ObservableList<Segment> segments = FXCollections.observableArrayList();
            ObjectMapper objectMapper = new ObjectMapper();
            for (JsonNode segmentNode : jsonNode) {
                Segment segment = Segment.fromJsonString(objectMapper.writeValueAsString(segmentNode));
                segments.add(segment);
            }
            this.segments = segments;
        }
    }

    public ObservableList<Segment> getSegments() {
        if (segments == null) {
            segments = FXCollections.observableArrayList();
        }
        return segments;
    }

    public void resetAllAnchorPanes() {
        profileView = null;
        editPageView = null;
        editUserView = null;
        registerUserView = null;
        usersListView = null;
        pointsListView = null;
        segmentsListView = null;
        //editUserADMView = null;
        //deleteUserADMView = null;
    }

    public void setPoint(Point point) {
        this.point = point;
    }
    public void setSegment(Segment segment) {
        this.segment = segment;
    }
}
