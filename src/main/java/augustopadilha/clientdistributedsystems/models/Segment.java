package augustopadilha.clientdistributedsystems.models;

public class Segment {
    private Point ponto_origem;
    private Point ponto_destino;
    private String direcao;
    private String distancia;
    private String obs;

    public Segment(Point ponto_origem, Point ponto_destino, String direcao, String distancia, String obs) {
    this.ponto_origem = ponto_origem;
    this.ponto_destino = ponto_destino;
    this.direcao = direcao;
    this.distancia = distancia;
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

    public String getDirecao() {
        return direcao;
    }

    public String getDistancia() {
        return distancia;
    }

    public String getObs() {
        return this.obs;
    }
}