package tech.zeta.model;

public class User {
    private long userId;
    private String name;
    private String email;
    private String password;
    private String role;
    private boolean isActive;
    private long currentId=1;

    public User() {
    }

    public User(String name, String email, String role) {
        this.userId = ++currentId;
        this.name = name;
        this.email = email;
        this.role = role;
        this.isActive = true;
        setPassword(name);
    }

    public long getUser_id() {
        return userId;
    }

    public void setUser_id(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String name) {
        String reverseName="";
        for(int index=name.length()-1;index>=0;index--) reverseName+=name.charAt(index);
        this.password=password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
