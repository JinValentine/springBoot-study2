package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.Item;
import com.example.study.model.entity.User;
import com.example.study.model.enumclass.UserStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Optional;

public class UserRepositoryTest extends StudyApplicationTests {

    //Dependency Injection (DI)
    @Autowired
    private UserRepository userRepository;

    @Test
    public void create(){
        String account = "Test03";
        String password = "Test03";
        UserStatus status = UserStatus.REGISTERED;
        String email = "Test03@gmail.com";
        String phoneNumber = "010-1111-3333";
        LocalDateTime registeredAt = LocalDateTime.now();
        LocalDateTime createdAt = LocalDateTime.now();
        String createdBy = "AdminServer";

        User user = new User();
        user.setAccount(account);
        user.setPassword(password);
        user.setStatus(UserStatus.REGISTERED);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setRegisteredAt(registeredAt);

        User u =User.builder()
                .account(account)
                .password(password)
                .status(status).
                email(email).
                build();

        User newUser = userRepository.save(user);

        Assertions.assertNotNull(newUser);
    }

    @Test
    @Transactional
    public void read() {
        User user = userRepository.findFirstByPhoneNumberOrderByIdDesc("010-1111-2222");



        if(user != null){

        user.getOrderGroupList().stream().forEach(orderGroup -> {
            System.out.println("------------????????????-------------");
            System.out.println("????????? : "+orderGroup.getRevName());
            System.out.println("????????? : "+orderGroup.getRevAddress());
            System.out.println("????????? : "+orderGroup.getTotalPrice());
            System.out.println("????????? : "+orderGroup.getTotalQuantity());

            System.out.println("------------????????????-------------");
            orderGroup.getOrderDetailList().forEach(orderDetail -> {
                System.out.println("???????????? ?????? : "+orderDetail.getItem().getPartner().getName());
                System.out.println("???????????? ???????????? : "+orderDetail.getItem().getPartner().getCategory().getTitle());
                System.out.println("?????? ?????? : "+orderDetail.getItem().getName());
                System.out.println("???????????? ?????? : "+orderDetail.getItem().getPartner().getCallCenter());
                System.out.println("????????? ?????? : "+orderDetail.getStatus());
                System.out.println("?????????????????? : "+orderDetail.getArrivalDate());

                });

            });
        }

        Assertions.assertNotNull(user);
    }

    @Test
    public void update(){
        Optional<User> user = userRepository.findById(2L);

        user.ifPresent(selectUser -> {
            selectUser.setAccount("PPPP");
            selectUser.setUpdatedAt(LocalDateTime.now());
            selectUser.setUpdatedBy("update method()");

            userRepository.save(selectUser);
        });
    }

    @Test
    @Transactional
    public void delete() {
        Optional<User> user = userRepository.findById(3L);

        Assertions.assertTrue(user.isPresent()); // true

        user.ifPresent(selectUser -> {
            userRepository.delete(selectUser);
        });

        Optional<User> deleteUser = userRepository.findById(3L);

        Assertions.assertFalse(deleteUser.isPresent()); // false

    }
}
