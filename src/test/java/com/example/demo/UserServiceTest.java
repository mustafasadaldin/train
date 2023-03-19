package com.example.demo;

import com.example.demo.models.AppUser;
import com.example.demo.repositories.UsersRepository;
import com.example.demo.services.JwtService;
import com.example.demo.services.PasswordService;
import com.example.demo.services.UsersService;
import com.example.demo.services.serviceimpl.JwtServiceImpl;
import com.example.demo.services.serviceimpl.PassServiceImpl;
import com.example.demo.services.serviceimpl.UsersServiceImpl;
import lombok.NoArgsConstructor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RunWith(SpringRunner.class)
@NoArgsConstructor
public class UserServiceTest {

    private UsersService usersService;

    private JwtService jwtService;

    private PasswordService passwordService;

    private AppUser appUser;

    private List<GrantedAuthority> authorities = new ArrayList<>();

    @MockBean
    private UsersRepository usersRepository;

    @TestConfiguration
    class UserServiceContextConfiguration {

        @Bean
        public JwtService jwtService() {
            return new JwtServiceImpl();
        }

        @Bean
        public PasswordService passwordService() {
            return new PassServiceImpl();
        }

        @Bean
        public UsersService usersService() {
            return new UsersServiceImpl(usersRepository, jwtService(), passwordService());
        }
    }

    @Before
    public void setUp() {
        usersService = new UserServiceContextConfiguration().usersService();
        appUser = new AppUser("mustafa001063@gmail.com", "0597633980$$Mm", "user");
        authorities.add(new SimpleGrantedAuthority("user"));
        passwordService = new UserServiceContextConfiguration().passwordService();
    }

    @Test
    public void getUserTest() {
        BDDMockito.given(usersRepository.findById(102)).willReturn(Optional.ofNullable(appUser));
        assertEquals(appUser.toString(), usersService.getUser(102).toString());
    }

    @Test
    public void loadByUsernameTest() {
        BDDMockito.given(usersRepository.findByEmail("mustafa001063@gmail.com")).willReturn(Optional.ofNullable(appUser));
        assertEquals(appUser.getEmail(), usersService.loadUserByUsername("mustafa001063@gmail.com").getUsername());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadByUsernameTestWithException() {
        BDDMockito.given(usersRepository.findByEmail("flllllf@gmail.com")).willReturn(Optional.empty());
        usersService.loadUserByUsername("flllllf@gmail.com");
    }

    @Test
    public void updateUserTest() {
        BDDMockito.given(usersRepository.findByEmail("mustafa001063@gmail.com")).willReturn(Optional.ofNullable(appUser));
        BDDMockito.given(usersRepository.save(appUser)).willReturn(appUser);
        assertEquals(true, passwordService.isTruePass("0597633980$$Mm", usersService.updateUser(appUser, "mustafa001063@gmail.com").getPassword()));
    }

}
