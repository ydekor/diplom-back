package com.ydekor.diplomback.model.user;

import com.ydekor.diplomback.model.note.Note;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)
public class SpaUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, unique = true, nullable = false)
    private String login;

    @ToString.Exclude
    @Column(nullable = false)
    private String password;

    @Column
    private String email;

    @ToString.Exclude
    @Column
    private Calendar created;

    @ToString.Exclude
    @Column
    private Calendar updated;

    @ToString.Exclude
    @Column
    private Calendar lastLogin;

    @Column
    private SpaUserStatus status;

    @ToString.Exclude
    @Column
    private String emailConfirmKey;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<SpaRole> roles;

    @ManyToMany
    @JoinTable(
            name = "note_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "note_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"note_id", "user_id"})}
    )
    private List<Note> notes;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(e -> new SimpleGrantedAuthority(e.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return login;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpaUser spaUser = (SpaUser) o;

        return id.equals(spaUser.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
