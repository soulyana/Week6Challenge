package me.soulyana.springsecuritybasic;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class AppRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    // Don't have more than one role with the same name. It's ambiguous and unnecessary.
    @Column(unique=true)
    private String name;

    //Each role should have a relationship with a number of users
    @ManyToMany(mappedBy = "roles")
    Set<AppUser> users;

    public AppRole() {
        //Instantiate your hashet of users
        this.users = new HashSet<>();
    }

    public AppRole(String role) {
        this.name = role;
        this.users = new HashSet<>();
    }

    public AppRole(String name, Set<AppUser> users) {
        this.name = name;
        this.users = new HashSet<>();
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<AppUser> getUsers() {
        return users;
    }

    public void setUsers(Set<AppUser> users) {
        this.users = users;
    }

    //Create an 'add' method for users

    public void addUser(AppUser aUser)
    {
        this.users.add(aUser);
    }

    @Override
    public String toString() {
        return "AppRole{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", users=" + users +
                '}';
    }
}

