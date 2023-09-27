package com.gravit.gravitlauncher.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;


@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false,  unique = true)
    private String userName;
    @Column (name = "email", nullable = false, unique = true)
    private String email;
    @Column (name = "password", nullable = false)
    private String password;
    @Column (name = "is_email_verify", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isEmailVerify = false;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable (
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn (name = "role_id")
    )

    private Collection<Role> roles;

}
