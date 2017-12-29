package model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user_table")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue
    protected long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    protected Role role;

    @NotNull
    @Column(unique = true, nullable = false)
    protected String username;

    @NotNull
    @Column(unique = false, nullable = false)
    protected String password;

    public User() {
    }

    public long getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
