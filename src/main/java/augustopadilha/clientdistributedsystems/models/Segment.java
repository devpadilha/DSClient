package augustopadilha.clientdistributedsystems.models;

public class Segment {
    private Point ponto_origem;
    private Point ponto_destino;
    private String direcao;
    private String distancia;
    private String obs;

    public Segment(Point originPoint, Point destinyPoint, String direction, String distance, String obs) {
    this.ponto_origem = originPoint;
    this.ponto_destino = destinyPoint;
    this.direcao = direction;
    this.distancia = distance;
    this.obs = obs;
    }

    public Point getOriginPoint() {
        return this.ponto_origem;
    }

    public Point getDestinyPoint() {
        return this.ponto_destino;
    }

    public String getDirection() {
        return this.direcao;
    }

    public String getDistance() {
        return this.distancia;
    }

    public String getObs() {
        return this.obs;
    }
}