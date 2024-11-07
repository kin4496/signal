package com.ssafy.signal.user.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name="User")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name="login_id")
    private String loginId;

    @Column(name="password")
    private String password;

    @Column(name="user_name")
    private String userName;
}
