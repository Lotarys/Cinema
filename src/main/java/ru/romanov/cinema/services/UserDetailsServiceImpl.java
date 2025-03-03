package ru.romanov.cinema.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.romanov.cinema.entites.Users;
import ru.romanov.cinema.repositories.UsersRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersRepository usersRepository;

    public UserDetailsServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public Users loadUserByUsername(String login) throws UsernameNotFoundException {
        return usersRepository.findByEmail(login)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
