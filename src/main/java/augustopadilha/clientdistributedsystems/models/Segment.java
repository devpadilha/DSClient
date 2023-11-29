package augustopadilha.clientdistributedsystems.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Segment {
    private String direcao;
    private String distancia;
    private String obs;
    private Point ponto_origem;
    private Point ponto_destino;
    private int segment_id;

    private static final ObjectMapper objectMapper = new ObjectMapper();


    public Segment() {

    }
    public Segment(String direcao, String distancia, String obs, Point ponto_origem, Point ponto_destino, int id) {
        this.direcao = direcao;
        this.distancia = distancia;
        this.obs = obs;
        this.ponto_origem = ponto_origem;
        this.ponto_destino = ponto_destino;
        this.segment_id = id;
    }

    public Segment(String direcao, String distancia, String obs, Point ponto_origem, Point ponto_destino) {
        this.direcao = direcao;
        this.distancia = distancia;
        this.obs = obs;
        this.ponto_origem = ponto_origem;
        this.ponto_destino = ponto_destino;
    }

    public static Segment fromJsonString(String jsonString) throws JsonProcessingException {
        // Utilize a biblioteca Jackson para converter a string JSON em um objeto JsonNode
        JsonNode jsonNode = objectMapper.readTree(jsonString);

        // Extrair os valores do JsonNode para criar um novo objeto Segment
        JsonNode originNode = jsonNode.path("ponto_origem");
        JsonNode destNode = jsonNode.path("ponto_destino");

        String direction = jsonNode.path("direcao").asText();
        String distance = jsonNode.path("distancia").asText();
        String obs = jsonNode.path("obs").asText();

        Point originPoint = new Point(
                originNode.path("name").asText(),
                originNode.path("obs").asText(),
                originNode.path("id").asInt()
        );

        Point destinyPoint = new Point(
                destNode.path("name").asText(),
                destNode.path("obs").asText(),
                destNode.path("id").asInt()
        );

        // Obtendo o ID do Segmento
        int id = jsonNode.path("id").asInt();

        return new Segment(direction, distance, obs, originPoint, destinyPoint, id);
    }

    public Point getOriginPoint() {
        return this.ponto_origem;
    }

    public Point getDestinyPoint() {
        return this.ponto_destino;
    }

    public int getId() {
        return segment_id;
    }

    public String getDirection() {
        return this.direcao;
    }

    public String getDistance() {
        return distancia;
    }
    public String getObs() {
        return this.obs;
    }

    public void setDirection(String direcao) {
        this.direcao = direcao;
    }

    public void setDistance(String distancia) {
        this.distancia = distancia;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public void setOriginPoint(Point ponto_origem) {
        this.ponto_origem = ponto_origem;
    }

    public void setDestinyPoint(Point ponto_destino) {
        this.ponto_destino = ponto_destino;
    }

    public void setId(int id) {
        this.segment_id = id;
    }
}