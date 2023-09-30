package java_oop_db;

public class User {

    private Long id;

    private String firstName;

    private String lastName;

    private Integer age;

    private String username;

    public User(Long id, String firstName, String lastName, String username, Integer age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getAge() {
        return age;
    }

    public String getusername() {
        return username;
    }

    // Setter method (if needed)
    public void setusername(String username) {
        this.username = username;
    }
}
