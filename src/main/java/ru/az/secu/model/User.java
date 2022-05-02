package ru.az.secu.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(name="username")
    private String username;
    @Column(name="password")
    private String password;
    @Column(name="email")
    private String email;
    @Column(name="active")
    private Boolean active;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name="last_name")
    private String lastName;
    @Column(name="first_name")
    private String firstName;
    @Column(name="middle_name")
    private String middleName;
    @Column(name = "activation_code")
    private String activationCode;

    @PrePersist
    private void setDefaultValue(){
        if(this.active==null) this.active=false;
    }

}
