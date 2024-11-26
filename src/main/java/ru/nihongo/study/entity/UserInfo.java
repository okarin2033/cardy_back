package ru.nihongo.study.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users") // Рекомендуется явно указать имя таблицы
@NoArgsConstructor
public class UserInfo implements UserDetails {

    public UserInfo(Long userId) {
        this.id = userId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "userInfos", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Deck> userDecks;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<UserCard> userCards;

    // Реализация методов UserDetails

    @Override
    public List<GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER")); // Можно расширить роли
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Можно добавить логику
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Можно добавить логику
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Можно добавить логику
    }

    @Override
    public boolean isEnabled() {
        return true; // Можно добавить логику
    }
}