package augustopadilha.clientdistributedsystems.system.connection;

import augustopadilha.clientdistributedsystems.App;
import augustopadilha.clientdistributedsystems.models.Point;
import augustopadilha.clientdistributedsystems.models.Segment;
import augustopadilha.clientdistributedsystems.models.User;
import augustopadilha.clientdistributedsystems.system.utilities.Token;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class SendData {
    private ObjectMapper objectMapper = new ObjectMapper();
    Connection connection = App.getConnection();

    private JsonNode data;

    public JsonNode getData() {
        return data;
    }
    /* ------------------------------------------------------------------ Generators ------------------------------------------------------------------ */
    private JsonNode generateFinalData(String action, JsonNode data) {
        this.data = objectMapper.createObjectNode();
        ((ObjectNode) this.getData()).put("action", action);
        ((ObjectNode) this.getData()).set("data", data);
        return this.data;
    }
    /*--------------------------------------------- CRUD USUARIOS ---------------------------------------------------*/
    public JsonNode generateLoginData(String email, String password) throws JsonProcessingException {
        JsonNode data = objectMapper.createObjectNode();
        ((ObjectNode) data).put("email", email);
        ((ObjectNode) data).put("password", password);
        return generateFinalData("login", data);
    }

    public JsonNode generateAutoRegisterData(String name, String email, String password, String type) {
        JsonNode data = objectMapper.createObjectNode();
        ((ObjectNode) data).put("name", name);
        ((ObjectNode) data).put("email", email);
        ((ObjectNode) data).put("password", password);
        ((ObjectNode) data).put("type", type);
        return generateFinalData("autocadastro-usuario", data);
    }

    public JsonNode generateRegisterUserData(String name, String email, String password, String type) throws JsonProcessingException {
        JsonNode data = objectMapper.createObjectNode();
        ((ObjectNode) data).put("token", Token.getJwtToken());
        ((ObjectNode) data).put("name", name);
        ((ObjectNode) data).put("email", email);
        ((ObjectNode) data).put("password", password);
        ((ObjectNode) data).put("type", type);
        return generateFinalData("cadastro-usuario", data);
    }

    public JsonNode generateLogoutData() throws JsonProcessingException {
        JsonNode data = objectMapper.createObjectNode();
        ((ObjectNode) data).put("token", Token.getJwtToken());
        return generateFinalData("logout", data);
    }

    public JsonNode generateClientListdata() throws JsonProcessingException {
        JsonNode data = objectMapper.createObjectNode();
        ((ObjectNode) data).put("token", Token.getJwtToken());
        return generateFinalData("listar-usuarios", data);
    }

    public JsonNode generateProfileData() throws JsonProcessingException {
        JsonNode data = objectMapper.createObjectNode();
        ((ObjectNode) data).put("token", Token.getJwtToken());
        return generateFinalData("pedido-proprio-usuario", data);
    }

    public JsonNode generateEditSelfData(int id, String name, String email, String password) throws JsonProcessingException {
        JsonNode data = objectMapper.createObjectNode();
        ((ObjectNode) data).put("token", Token.getJwtToken());
        ((ObjectNode) data).put("id", id);
        ((ObjectNode) data).put("name", name);
        ((ObjectNode) data).put("email", email);
        ((ObjectNode) data).put("password", password);
        return generateFinalData("autoedicao-usuario", data);
    }

    public JsonNode generateDeleteSelfData (String email, String password) throws JsonProcessingException {
        JsonNode data = objectMapper.createObjectNode();
        ((ObjectNode) data).put("token", Token.getJwtToken());
        ((ObjectNode) data).put("email", email);
        ((ObjectNode) data).put("password", password);
        return generateFinalData("excluir-proprio-usuario", data);
    }
    public JsonNode generateEditUserData(int user_id) throws JsonProcessingException {
        JsonNode data = objectMapper.createObjectNode();
        ((ObjectNode) data).put("token", Token.getJwtToken());
        ((ObjectNode) data).put("user_id", user_id);
        return generateFinalData("edicao-usuario", data);
    }

    public JsonNode generateDeleteUserData(int user_id) throws JsonProcessingException {
        JsonNode data = objectMapper.createObjectNode();
        ((ObjectNode) data).put("token", Token.getJwtToken());
        ((ObjectNode) data).put("user_id", user_id);
        return generateFinalData("excluir-usuario", data);
    }
    /*---------------------------------------------------------------------------------------------------------------*/

    /*------------------------------------------------- CRUD PONTOS -------------------------------------------------*/
    public JsonNode generateRegisterPointData(String name, String obs){
        JsonNode data = objectMapper.createObjectNode();
        ((ObjectNode) data).put("token", Token.getJwtToken());
        ((ObjectNode) data).put("name", name);
        ((ObjectNode) data).put("obs", obs);
        return generateFinalData("cadastro-ponto", data);
    }

    public JsonNode generateListPointsData(){
        JsonNode data = objectMapper.createObjectNode();
        ((ObjectNode) data).put("token", Token.getJwtToken());
        return generateFinalData("listar-pontos", data);
    }

    public JsonNode generateEditPointData(int id, String name, String obs){
        JsonNode data = objectMapper.createObjectNode();
        ((ObjectNode) data).put("token", Token.getJwtToken());
        ((ObjectNode) data).put("id", id);
        ((ObjectNode) data).put("name", name);
        ((ObjectNode) data).put("obs", obs);
        return generateFinalData("edicao-ponto", data);
    }

    public JsonNode generateAskEditPointData(int id){
        JsonNode data = objectMapper.createObjectNode();
        ((ObjectNode) data).put("token", Token.getJwtToken());
        ((ObjectNode) data).put("id", id);
        return generateFinalData("pedido-edicao-ponto", data);
    }

    public JsonNode generateDeletePointData(int id){
        JsonNode data = objectMapper.createObjectNode();
        ((ObjectNode) data).put("token", Token.getJwtToken());
        ((ObjectNode) data).put("id", id);
        return generateFinalData("excluir-ponto", data);
    }
    /*---------------------------------------------------------------------------------------------------------------*/

    /*------------------------------------------------ CRUD SEGMENTOS ------------------------------------------------*/
    public JsonNode generateRegisterSegmentData(Segment segment) {
        JsonNode data = objectMapper.createObjectNode();
        ((ObjectNode) data).put("token", Token.getJwtToken());
        ObjectNode segmentNode = ((ObjectNode) data).putObject("segmento");
        segmentNode.putObject("ponto_origem")
                .put("id", segment.getOriginPoint().getId())
                .put("name", segment.getOriginPoint().getName())
                .put("obs", segment.getOriginPoint().getObs());

        segmentNode.putObject("ponto_destino")
                .put("id", segment.getDestinyPoint().getId())
                .put("name", segment.getDestinyPoint().getName())
                .put("obs", segment.getDestinyPoint().getObs());

        segmentNode.put("direcao", segment.getDirection());
        segmentNode.put("distancia", segment.getDistance());
        segmentNode.put("obs", segment.getObs());

        return generateFinalData("cadastro-segmento", data);
    }

    public JsonNode generateAskEditSegmentData(int id) {
        JsonNode data = objectMapper.createObjectNode();
        ((ObjectNode) data).put("token", Token.getJwtToken());
        ((ObjectNode) data).put("segmento_id", id);
        return generateFinalData("pedido-edicao-segmento", data);
    }

    public JsonNode generateListSegmentsData() {
        JsonNode data = objectMapper.createObjectNode();
        ((ObjectNode) data).put("token", Token.getJwtToken());
        return generateFinalData("listar-segmentos", data);
    }

    public JsonNode generateEditSegmentData(Segment segment) {
        JsonNode data = objectMapper.createObjectNode();
        ((ObjectNode) data).put("token", Token.getJwtToken());
        ((ObjectNode) data).put("id", segment.getId());
        ((ObjectNode) data).set("segmento", objectMapper.convertValue(segment, JsonNode.class));
        return generateFinalData("edicao-segmento", data);
    }

    public JsonNode generateDeleteSegmentData(int id) {
        JsonNode data = objectMapper.createObjectNode();
        ((ObjectNode) data).put("token", Token.getJwtToken());
        ((ObjectNode) data).put("id", id);
        return generateFinalData("excluir-segmento", data);
    }
    /*---------------------------------------------------------------------------------------------------------------*/
    /* --------------------------------------------------------------------------------------------------------------------------------------------- */



    /* ------------------------------------------------------------------ Senders ------------------------------------------------------------------ */
    /*---------------------------------------------- CRUD USUARIOS --------------------------------------------------*/
    public JsonNode sendLoginData(String email, String password) throws JsonProcessingException {
        String serverResponse = null;
        try {
            serverResponse = connection.send(objectMapper.writeValueAsString(generateLoginData(email, password)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return stringToJsonNode(serverResponse);
    }

    public JsonNode sendAutoRegisterData(String name, String email, String password, String type) throws JsonProcessingException {
        String serverResponse = null;
        try {
            serverResponse = connection.send(objectMapper.writeValueAsString(generateAutoRegisterData(name, email, password, type)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return stringToJsonNode(serverResponse);
    }

    public JsonNode sendRegisterUserData(String name, String email, String password, String type) throws JsonProcessingException {
        String serverResponse = null;
        try {
            serverResponse = connection.send(objectMapper.writeValueAsString(generateRegisterUserData(name, email, password, type)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return stringToJsonNode(serverResponse);
    }

    public JsonNode sendLogoutData() throws JsonProcessingException {
        String serverResponse = null;
        try {
            serverResponse = connection.send(objectMapper.writeValueAsString(generateLogoutData()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return stringToJsonNode(serverResponse);
    }

    public JsonNode sendClientListData() throws JsonProcessingException {
        String serverResponse = null;
        try {
            serverResponse = connection.send(objectMapper.writeValueAsString(generateClientListdata()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return stringToJsonNode(serverResponse);
    }

    public JsonNode sendProfileData() throws JsonProcessingException {
        String serverResponse = null;
        try {
            serverResponse = connection.send(objectMapper.writeValueAsString(generateProfileData()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return stringToJsonNode(serverResponse);
    }

    public JsonNode sendEditSelfData(int id, String name, String email, String password) throws JsonProcessingException {
        String serverResponse = null;
        try {
            serverResponse = connection.send(objectMapper.writeValueAsString(generateEditSelfData(id, name, email, password)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return stringToJsonNode(serverResponse);
    }

    public JsonNode sendDeleteSelfData(String email, String password) throws JsonProcessingException {
        String serverResponse = null;
        try {
            serverResponse = connection.send(objectMapper.writeValueAsString(generateDeleteSelfData(email, password)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return stringToJsonNode(serverResponse);
    }

    public JsonNode sendEditUserData(int user_id) throws JsonProcessingException {
        String serverResponse = null;
        try {
            serverResponse = connection.send(objectMapper.writeValueAsString(generateEditUserData(user_id)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return stringToJsonNode(serverResponse);
    }

    public JsonNode sendDeleteUserData(int user_id) throws JsonProcessingException {
        String serverResponse = null;
        try {
            serverResponse = connection.send(objectMapper.writeValueAsString(generateDeleteUserData(user_id)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return stringToJsonNode(serverResponse);
    }
    /*----------------------------------------------------------------------------------------------------------------*/

    /*-------------------------------------------------- CRUD PONTOS --------------------------------------------------*/
    public JsonNode sendRegisterPointData(String name, String obs) throws JsonProcessingException {
        String serverResponse = null;
        try {
            serverResponse = connection.send(objectMapper.writeValueAsString(generateRegisterPointData(name, obs)));
        } catch(JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return stringToJsonNode(serverResponse);
    }

    public JsonNode sendListPointsData() throws JsonProcessingException {
        String serverResponse = null;
        try {
            serverResponse = connection.send(objectMapper.writeValueAsString(generateListPointsData()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return stringToJsonNode(serverResponse);
    }

    public JsonNode sendEditPointData(Point point) throws JsonProcessingException {
        String serverResponse = null;
        try {
            serverResponse = connection.send(objectMapper.writeValueAsString(generateEditPointData(point.getId(), point.getName(), point.getObs())));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return stringToJsonNode(serverResponse);
    }

    public JsonNode sendDeletePointData(int id) throws JsonProcessingException {
        String serverResponse = null;
        try {
            serverResponse = connection.send(objectMapper.writeValueAsString(generateDeletePointData(id)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return stringToJsonNode(serverResponse);
    }
    /*----------------------------------------------------------------------------------------------------------------*/

    /*------------------------------------------------ CRUD SEGMENTOS ------------------------------------------------*/
    public JsonNode sendRegisterSegmentData(Segment segment) throws JsonProcessingException {
        String serverResponse = null;
        try {
            serverResponse = connection.send(objectMapper.writeValueAsString(generateRegisterSegmentData(segment)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return stringToJsonNode(serverResponse);
    }
    public JsonNode sendAskEditSegmentData(int id) throws JsonProcessingException {
        String serverResponse = null;
        try {
            serverResponse = connection.send(objectMapper.writeValueAsString(generateAskEditSegmentData(id)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return stringToJsonNode(serverResponse);
    }
    public JsonNode sendListSegmentsData() throws JsonProcessingException {
        String serverResponse = null;
        try {
            serverResponse = connection.send(objectMapper.writeValueAsString(generateListSegmentsData()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return stringToJsonNode(serverResponse);
    }

    public JsonNode sendEditSegmentData(Segment segment) throws JsonProcessingException {
        String serverResponse = null;
        try {
            serverResponse = connection.send(objectMapper.writeValueAsString(generateEditSegmentData(segment)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return stringToJsonNode(serverResponse);
    }

    public JsonNode sendDeleteSegmentData(int id) throws JsonProcessingException {
        String serverResponse = null;
        try {
            serverResponse = connection.send(objectMapper.writeValueAsString(generateDeleteSegmentData(id)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return stringToJsonNode(serverResponse);
    }
    /*---------------------------------------------------------------------------------------------------------------*/
    /* --------------------------------------------------------------------------------------------------------------------------------------------- */

    public JsonNode stringToJsonNode (String json) throws JsonProcessingException {
        if (json == null || json.isEmpty()) return null;
        return objectMapper.readTree(json);
    }
}
