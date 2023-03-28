package com.example.demo.services.serviceimpl;

import com.example.demo.models.AppUser;
import com.example.demo.models.TrainerRating;
import com.example.demo.repositories.TrainerRatingRepo;
import com.example.demo.repositories.UsersRepository;
import com.example.demo.services.EmailService;
import com.example.demo.services.JwtService;
import com.example.demo.services.PasswordService;
import com.example.demo.services.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
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

    private EmailService emailService;

    private TrainerRatingRepo trainerRatingRepo;

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

    public AppUser trainerHiring(AppUser user) {
        String password = passwordService.genRandomPassword();
        emailService.sendEmail(user.getEmail(), password, "your login password");
        AppUser userToSave = usersRepository.save(new AppUser(user.getEmail(), passwordService.hashPassword(password), user.getLevel()));
        TrainerRating trainer = new TrainerRating(0, 0, 0, 0, 0, userToSave.getId());
        trainerRatingRepo.save(trainer);
        return userToSave;
    }

    public void forgetPassword(String email) {

        Optional<AppUser> user = usersRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("invalid credentials");
        }

        int pinCode = (int) Math.floor(1000 + Math.random() * 9000);
        emailService.sendEmail(email, pinCode + "", "Verification code");
        user.get().setPin(pinCode);
        user.get().setNumberOfInvalidAttempt(0);
        usersRepository.save(user.get());
    }

    public boolean isTruePin(int pin, String email) {
        Optional<AppUser> user = usersRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("invalid credentials");
        } else if (user.get().getPin() == pin) {
            return true;
        } else if (user.get().getNumberOfInvalidAttempt() == 3) {
            user.get().setPin(0);
            user.get().setNumberOfInvalidAttempt(0);
            usersRepository.save(user.get());
            throw new BadCredentialsException("you exceeds number of allowed attempts");
        } else {
            user.get().setNumberOfInvalidAttempt(user.get().getNumberOfInvalidAttempt().intValue() + 1);
            usersRepository.save(user.get());
            return false;
        }
    }

    public float returnTrainerAvgRate(int id) {
        Optional<TrainerRating> trainer = trainerRatingRepo.findByTrainerId(id);
        if (trainer.isEmpty()) {
            throw new UsernameNotFoundException("invalid credentials");
        }
        return trainer.get().getAvgRate();
    }

    public void updateTrainerRate(int id, int numberOfStars) {
        Optional<TrainerRating> trainer = trainerRatingRepo.findByTrainerId(id);
        switch (Math.round(numberOfStars)) {
            case 1:
                trainer.get().setStar1(trainer.get().getStar1() + 1);
                trainerRatingRepo.save(trainer.get());
                break;

            case 2:
                trainer.get().setStar2(trainer.get().getStar2() + 1);
                trainerRatingRepo.save(trainer.get());
                break;

            case 3:
                trainer.get().setStar3(trainer.get().getStar3() + 1);
                trainerRatingRepo.save(trainer.get());
                break;


            case 4:
                trainer.get().setStar4(trainer.get().getStar4() + 1);
                trainerRatingRepo.save(trainer.get());
                break;

            case 5:
                trainer.get().setStar5(trainer.get().getStar5() + 1);
                trainerRatingRepo.save(trainer.get());
                break;
        }
        float sum = trainer.get().getStar1() + trainer.get().getStar2() + trainer.get().getStar3() + trainer.get().getStar4() + trainer.get().getStar5();
        trainer.get().setAvgRate(
                (5 * trainer.get().getStar5() +
                        4 * trainer.get().getStar4() +
                        3 * trainer.get().getStar3() +
                        2 * trainer.get().getStar2() +
                        1 * trainer.get().getStar1()) / sum
        );
        trainerRatingRepo.save(trainer.get());
    }
}
