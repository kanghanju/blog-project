package com.estsoft.blogjpa.domain.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",updatable = false)
    private Long id;

    @Column(name = "email",nullable = false,unique = true)
    private String email;

    @Column(name = "password",nullable = false)
    private String password;

    @Builder
    public User(String email,String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    //계정 만료 여부 반환 (true : 만료 안됨)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //계정 잠금 여부 반환 (true : 잠금 안됨)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //패스워드의 만료 여부 반환 (true : 만료 안됨)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //계정 사용 여부 반환 (true : 사용 가능)
    @Override
    public boolean isEnabled() {
        return true;
    }
}
