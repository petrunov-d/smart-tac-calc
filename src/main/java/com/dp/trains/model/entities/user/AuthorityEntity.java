package com.dp.trains.model.entities.user;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Data
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "authorities")
@EqualsAndHashCode(exclude = {"username", "authority"})
public class AuthorityEntity implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "authority")
    private String authority;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "username", nullable = false)
    private UserEntity username;

    @Override
    public String getAuthority() {

        return authority;
    }
}
