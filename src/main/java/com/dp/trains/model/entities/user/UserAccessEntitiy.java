package com.dp.trains.model.entities.user;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_access")
@EqualsAndHashCode(exclude = {"username"})
public class UserAccessEntitiy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_access")
    private String userAccess;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "username", nullable = false)
    private UserEntity username;
}
