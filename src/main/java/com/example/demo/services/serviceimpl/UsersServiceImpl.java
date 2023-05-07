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
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

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

    public AppUser changePassword(AppUser appUser) {
        Optional<AppUser> appUserFromDb = usersRepository.findByEmail(appUser.getEmail());
        appUserFromDb.get().setPassword(passwordService.hashPassword(appUser.getPassword()));
        return usersRepository.save(appUserFromDb.get());
    }

    public AppUser updateUserInfo(String email, int age, float height, float weight) {
        Optional<AppUser> appUser = usersRepository.findByEmail(email);
        if (appUser.isEmpty()) {
            throw new NoSuchElementException("not founded");
        } else {
            if (age != 1) {
                appUser.get().setAge(age);
            }

            if (height != 1) {
                appUser.get().setHeight(height);
            }

            if (weight != 1) {
                appUser.get().setWeight(weight);
            }
            return usersRepository.save(appUser.get());
        }
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

    public float returnTrainerAvgRate(String email) {
        Optional<AppUser> user = usersRepository.findByEmail(email);
        Optional<TrainerRating> trainer = trainerRatingRepo.findByTrainerId(user.get().getId());
        if (trainer.isEmpty()) {
            throw new UsernameNotFoundException("invalid credentials");
        }
        return trainer.get().getAvgRate();
    }

    public void updateTrainerRate(String email, int numberOfStars) {
        Optional<AppUser> user = usersRepository.findByEmail(email);
        Optional<TrainerRating> trainer = trainerRatingRepo.findByTrainerId(user.get().getId());
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

    public void uploadImage(MultipartFile file, String email) throws IOException {
        Optional<AppUser> appUser = usersRepository.findByEmail(email);
        if (appUser.isEmpty()) {
            throw new NoSuchElementException("not founded");
        } else {
            appUser.get().setImage(compressBytes(file.getBytes()));
            usersRepository.save(appUser.get());
        }
    }

    public byte[] getImage(String email) {
        Optional<AppUser> appUser = usersRepository.findByEmail(email);
        if (appUser.isEmpty()) {
            throw new NoSuchElementException("not founded");
        } else {
            if (appUser.get().getImage() == null) {
                throw new NoSuchElementException("not founded");
            }
            return decompressBytes(appUser.get().getImage());
        }
    }


    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }

        return outputStream.toByteArray();
    }


    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }

    public Map<String, String> getUserInfo(String email) {
        Optional<AppUser> appUser = usersRepository.findByEmail(email);
        if (appUser.isEmpty()) {
            throw new NoSuchElementException("not founded");
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("age", appUser.get().getAge().toString());
            response.put("height", appUser.get().getHeight().toString());
            response.put("weight", appUser.get().getWeight().toString());
            return response;
        }
    }

    public String getAllTrainersNames() {
        List<AppUser> users = usersRepository.findAllByLevel("trainer");
        String names = "";
        for (int i = 0; i < users.size(); i++) {
            names = names + users.get(i).getEmail() + ",";
        }
        return names;
    }

    public String getAllUsersNames() {
        List<AppUser> users = usersRepository.findAllByLevel("user");
        String names = "";
        for (int i = 0; i < users.size(); i++) {
            names = names + users.get(i).getEmail() + ",";
        }
        return names;
    }

}
