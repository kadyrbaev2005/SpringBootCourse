package com.medieval.taxcollector.service;

import com.medieval.taxcollector.domain.AppUser;
import com.medieval.taxcollector.domain.Role;
import com.medieval.taxcollector.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = findUserByUsername(username);

        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : user.getRoles()) {
            String roleName = "ROLE_" + role.getName();
            authorities.add(new SimpleGrantedAuthority(roleName));
        }

        return new User(user.getUsername(), user.getPasswordHash(), authorities);
    }

    public AppUser findUserByUsername(String username) {
        Optional<AppUser> optional = userRepository.findByUsername(username);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new UsernameNotFoundException("User not fount: " + username);
        }
    }

    public boolean checkPassword(AppUser user, String rawPassword) {
        return passwordEncoder.matches(rawPassword, user.getPasswordHash());
    }

    public List<String> getUserRoles(AppUser user) {
        List<String> roles = new ArrayList<>();
        for (Role role : user.getRoles()) {
            roles.add(role.getName());
        }
        return roles;
    }
}
