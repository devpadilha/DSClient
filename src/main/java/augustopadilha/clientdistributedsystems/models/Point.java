package augustopadilha.clientdistributedsystems.models;

public class Point {
    private int id;
    private String name;
    private String obs;

    public Point(int id, String name, String obs) {
        this.id = id;
        this.name = name;
        this.name = obs;
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
}
