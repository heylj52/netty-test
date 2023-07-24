package object;

import java.io.Serializable;

public class User implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String id;
    private int age;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public User(String id, int age) {
        super();
        this.id = id;
        this.age = age;
    }
    @Override
    public String toString() {
        return "User [id=" + id + ", age=" + age + "]";
    }
}

