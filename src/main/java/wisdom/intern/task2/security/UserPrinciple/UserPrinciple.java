package wisdom.intern.task2.security.UserPrinciple;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import wisdom.intern.task2.entity.Account;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserPrinciple implements UserDetails {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String password;
    private String email;
    private String fullname;
    private Collection<? extends GrantedAuthority> roles;
    public UserPrinciple(Long id, String password, String username, String name, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.password = password;
        this.email = username;
        this.fullname = name;
        this.roles = authorities;
    }

    public static UserPrinciple build(Account user){
        List<GrantedAuthority> authorities = user.getRole().stream().map(role ->
                new SimpleGrantedAuthority(role.getCode())).collect(Collectors.toList());
        return new UserPrinciple(user.getId(),user.getPassword(),user.getEmail(), user.getFullname(), authorities);
    }

    public Long getId() {
        return id;
    }
    public String getFullname(){
        return fullname;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
}