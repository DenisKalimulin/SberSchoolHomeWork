package DZ_12.WithPatternPrototype;

import DZ_12.util.Sex;

public class User implements Cloneable {
    private String fullName;
    private String role;
    private Sex gender;
    private int age;

    public User(String fullName, String role,Sex gender, int age){
        System.out.println("Init new user");
        this.fullName = fullName;
        this.role = role;
        this.gender = gender;
        this.age = age;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Sex getGender() {
        return gender;
    }

    public void setGender(Sex gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "fullName='" + fullName + '\'' +
                ", role='" + role + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                '}';
    }

    @Override
    protected User clone() throws CloneNotSupportedException {
        try {
            return (User) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Can't clone User");
        }
    }
}
