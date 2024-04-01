package com.example.demo.Models.Entites;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.Models.Enums.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name="Student")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Student extends User implements UserDetails{
    @Builder
    public Student(String firstName, String lastName, String username, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private boolean isActive;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Comment> comments;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "claimer",cascade = CascadeType.ALL, orphanRemoval = true)
    List<Strike> claimers;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "streamer",cascade = CascadeType.ALL, orphanRemoval = true)
    List<Strike> streamer;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Stream> streams;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    List<StudentRefreshToken> refreshTokens;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> studentNotifications;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "streamer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> streamerNotifications;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subscriber", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subscriber> subscribers;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "streamer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subscriber> followings;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
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
        return true;
    }
    public boolean getActive(){
        return this.isActive;
    }
}
