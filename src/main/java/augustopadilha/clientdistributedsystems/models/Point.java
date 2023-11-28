package augustopadilha.clientdistributedsystems.models;

public class Point {
    private int id;
    private String name;
    private String obs;

    public Point() {}
    public Point(int id, String name, String obs) {
        this.id = id;
        this.name = name;
        this.name = obs;
    }

    public Point objectToPoint(Object object) {
        Point point = (Point) object;
        return point;
    }

    public int getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public String getObs() {
        return this.obs;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setObs(String obs) {
        this.obs = obs;
    }

    public void setId(int id) {
        this.id = id;
    }
}
