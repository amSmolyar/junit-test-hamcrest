package ru.netology;

public class User {
    private String email;
    private String login;
    private String password;
    private int age;

    public User(String email, String login, String password, int age) {
        this.email = email;
        this.login = login;
        this.password = password;
        this.age = age;
    }

    public User() {
        this.email = "";
        this.login = "";
        this.password = "";
        this.age = 1234567;
    }

    public String getEmail() {
        return this.email;
    }

    public String getLogin() {
        return this.login;
    }

    public String getPassword() {
        return this.password;
    }

    public int getAge() {
        return this.age;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return  false;

        User newUser = (User) obj;
        return ((this.email == newUser.getEmail() || (this.email != null && this.email.equals(newUser.getEmail()))) &&
                (this.login == newUser.getLogin() || (this.login != null && this.login.equals(newUser.getLogin()))) &&
                (this.password == newUser.getPassword() || (this.password != null && this.password.equals(newUser.getPassword()))) &&
                this.age == newUser.getAge());
    }

    @Override
    public int hashCode() {
        return this.age +
                (this.email == null ? 0 : this.email.hashCode()) +
                (this.login == null ? 0 : this.login.hashCode()) +
                (this.password == null ? 0 : this.password.hashCode());
    }

    @Override
    public String toString() {
        return "[User: email " + this.email +
                ", login " + this.login +
                ", password " + this.password +
                ", age " + this.age + "]";
    }
}
