package com.gravit.gravitlauncher.DB.DTO;

import jakarta.persistence.Table;
import lombok.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "users")
@Builder
public class UserDTO {

    private Long id;
    private String userName;
    private String email;
    private String password;
    private Collection<String> roleNames;

}
