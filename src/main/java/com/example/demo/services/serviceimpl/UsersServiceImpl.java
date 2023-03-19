package com.example.demo.services.serviceimpl;

import com.example.demo.models.AppUser;
import com.example.demo.repositories.UsersRepository;
import com.example.demo.services.JwtService;
import com.example.demo.services.PasswordService;
import com.example.demo.services.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class UsersServiceImpl implements UsersService {

    private UsersRepository usersRepository;

    private JwtService jwtService;

    private PasswordService passwordService;


    public String insert(AppUser appUser) {
        appUser.setPassword(passwordService.hashPassword(appUser.getPassword()));
        usersRepository.save(appUser);
        return jwtService.generateJwtToken(appUser.getEmail());
    }

    public void deleteUser(String email) {
        Optional<AppUser> userFromDB = usersRepository.findByEmail(email);
        usersRepository.deleteById(userFromDB.get().getId());
    }

    public AppUser getUser(int id) {
        Optional<AppUser> user = usersRepository.findById(id);
        if (user.isEmpty()) {
            throw new NoSuchElementException("not founded");
        }
        return usersRepository.findById(id).get();
    }

    public AppUser updateUser(AppUser appUser, String email) {
        Optional<AppUser> appUserFromDb = usersRepository.findByEmail(email);
        appUserFromDb.get().setPassword(passwordService.hashPassword(appUser.getPassword()));
        appUserFromDb.get().setEmail(appUser.getEmail());
        return usersRepository.save(appUserFromDb.get());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<AppUser> user = usersRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("invalid credentials");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.get().getLevel()));
        return new User(user.get().getEmail(), user.get().getPassword(), authorities);
    }

    public AppUser trainerHiring (String email) {

        return null;
    }
}
