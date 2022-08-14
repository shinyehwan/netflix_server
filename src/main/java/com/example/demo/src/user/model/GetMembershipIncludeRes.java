package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class GetMembershipIncludeRes {
    private int userIdx;
    private String email;
    private String password;
    private String phone;
    private String membershipName;
    private String nextPayment;
}
