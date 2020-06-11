package com.dp.trains.model.entities.user;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Data
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@EqualsAndHashCode(exclude = {"authorities", "userAccesses"})
public class UserEntity implements UserDetails {

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private Boolean enabled;

    @ToString.Exclude
    @OneToMany(mappedBy = "username", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<AuthorityEntity> authorities;

    @ToString.Exclude
    @OneToMany(mappedBy = "username", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<UserAccessEntitiy> userAccesses;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return authorities;
    }
}
