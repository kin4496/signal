package com.ssafy.signal.user.domain;

import jakarta.persistence.*;

@Entity
@Table(name="User")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;

    @Column(name="password")
    private String password;

    @Column(name="user_name")
    private String userName;
}
