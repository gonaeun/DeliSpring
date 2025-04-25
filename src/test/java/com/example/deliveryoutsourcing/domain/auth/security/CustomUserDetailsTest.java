package com.example.deliveryoutsourcing.domain.auth.security;

import java.util.Collection;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

class CustomUserDetailsTest {

    @Test
    void getUserId_잘_꺼내오는지() {
        //given
        Long expectedUserId = 1L;
        String email = "test@example.com";
        String password = "password";
        Collection<GrantedAuthority> authorities = List.of();

        CustomUserDetails userDetails = new CustomUserDetails(expectedUserId, email, password, authorities);

        //when
        Long realUserId = userDetails.getUserId();

        //then
        System.out.println("realUserId = " + realUserId);
        assertEquals(expectedUserId, realUserId);
    }
}