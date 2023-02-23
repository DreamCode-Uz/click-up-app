package uz.pdp.clickupsecondpart.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.pdp.clickupsecondpart.entity.enums.SystemRoleName;
import uz.pdp.clickupsecondpart.entity.template.AbsUUIDEntity;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "users")
public class User extends AbsUUIDEntity implements UserDetails {

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column
    private String color;

    private Timestamp lastActivity;

    @OneToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Attachment avatar;

    @Enumerated(EnumType.STRING)
    private SystemRoleName systemRoleName;

    private String activationCode;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = false;

    @Column(name = "account_non_expired", nullable = false)
    private boolean accountNonExpired = true;

    @Column(name = "account_non_locked", nullable = false)
    private boolean accountNonLocked = true;

    @Column(name = "credentials_non_expired", nullable = false)
    private boolean credentialsNonExpired = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(new SimpleGrantedAuthority(this.systemRoleName.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    public User(String fullName, String email, String password, SystemRoleName systemRoleName) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.systemRoleName = systemRoleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
