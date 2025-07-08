package com.license.backend.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.license.backend.domain.constant.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_user_id_seq")
    @SequenceGenerator(name = "users_user_id_seq", allocationSize = 1)
    @Column
    private Integer userId;

    @Column(unique = true, nullable = false)
    @Email
    private String email;

    @Column
    private String firstname;

    @Column
    private String lastname;

    @Column
    private String userPassword;

    @Column
    @Enumerated(EnumType.STRING)
    private Roles userType;

    @Column
    private Boolean isActive;

    @Lob
    private byte[] profilePicture;

    @Column
    private String description;

    @Column
    private String resetPasswordToken;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private List<Visualization> visualizations = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "user_liked_visualizations",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "visualization_id")
    )
    @JsonIgnore
    private Set<Visualization> likedVisualizations = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(userType);
    }

    @Override
    public String getPassword() {
        return userPassword;
    }

    @Override
    public String getUsername() {
        return email;
    }

}
