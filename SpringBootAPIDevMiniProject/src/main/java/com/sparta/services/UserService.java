package com.sparta.services;



import com.sparta.entities.AppUser;
import com.sparta.repositories.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = repository.findByUsername(username);


        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        if (user.getUsername() == null || user.getUsername().isBlank()) {
            throw new UsernameNotFoundException("DB username is null/blank for lookup: " + username);
        }

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new UsernameNotFoundException("DB password is null/blank for user: " + user.getUsername());
        }

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword()) // should already be encoded
                .authorities("ROLE_" + user.getRole()) // ADD AUTHORITIES
                .build();
    }
}