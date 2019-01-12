package Model;

public class Post {

    private int id;
    private String name;
    private String message;
    private String data;

    public Post(int id, String name, String message, String data) {
        this.id = id;
        this.name = name;
        this.message = message;
        this.data = data;
    }

    public Post(String name, String message, String data) {
        this.name = name;
        this.message = message;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public String getData() {
        return data;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(String data) {
        this.data = data;
    }
}
