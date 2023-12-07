package com.example.dishdash.component;

import com.example.dishdash.entity.Cart;
import com.example.dishdash.entity.Users;
import com.example.dishdash.service.ServiceCart;
import com.example.dishdash.service.ServiceUsers;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@AllArgsConstructor
public class MyScheduledTask {
    private final ServiceUsers serviceUsers;
    @Scheduled(cron = "0 */10 * * * ?")
    public void myTask() {
        List<Users> users = serviceUsers.findAll();
        for (Users user : users) {
            if (user.getPassword() == null) {
                serviceUsers.delete(user);
            }
        }
    }
}