package com.enosnery.RestAPI.models;




import javax.persistence.*;


@SequenceGenerator(name="seq", initialValue=0, allocationSize=100)
@Entity

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private Long id;

    private String name;

    @Column(unique = true)
    private String login;

    private String password;

    public User(){
    }

    public User(String name, String login, String password){
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
