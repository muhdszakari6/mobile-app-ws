package com.appsdeveloperblog.app.ws.io.entity;


import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

@Entity()
@Table(name = "authorities")
public class AuthorityEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -5828101164006114538L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;
    @ManyToMany(mappedBy = "authorities")
    private Collection<RoleEntity> roles;
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Collection<RoleEntity> roles) {
        this.roles = roles;
    }
}
