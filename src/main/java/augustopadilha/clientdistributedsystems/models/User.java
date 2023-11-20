package augustopadilha.clientdistributedsystems.models;

import com.fasterxml.jackson.databind.JsonNode;

public class User {
    private String name;
    private String email;
    private String type;
    private int id;

    public User() {
    }

    public User(String name, String email, String type, int id) {
        this.name = name;
        this.email = email;
        this.type = type;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getID(){
        return id;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }
}