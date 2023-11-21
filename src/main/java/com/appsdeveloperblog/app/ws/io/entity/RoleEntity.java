package com.appsdeveloperblog.app.ws.io.entity;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name="roles")
public class RoleEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 5605260522147928803L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<UserEntity> users;

    @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name="role_authorities",
            joinColumns = @JoinColumn(name="roles_id",referencedColumnName = "id") ,
            inverseJoinColumns = @JoinColumn(name="authorities_id", referencedColumnName = "id") )
    private Collection<AuthorityEntity> authorities;

    public Collection<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Collection<UserEntity> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Collection<AuthorityEntity> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<AuthorityEntity> authorities) {
        this.authorities = authorities;
    }
}
