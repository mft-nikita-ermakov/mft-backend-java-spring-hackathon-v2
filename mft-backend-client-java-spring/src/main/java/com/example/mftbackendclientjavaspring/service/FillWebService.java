package com.example.mftbackendclientjavaspring.service;

import com.example.mftbackendclientjavaspring.entity.Role;
import com.example.mftbackendclientjavaspring.entity.User;
import com.example.mftbackendclientjavaspring.repository.RoleRepository;
import com.example.mftbackendclientjavaspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class FillWebService {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;

    int ADMIN = 0;
    public  void  FillTables() {
        if (ADMIN < 1) {

            /** Создаем объекты классов для таблиц Role, User и заполняем их **/

            Role role_worker = new Role(1L, "ROLE_WORKER");
            Role role_admin = new Role(2L, "ROLE_ADMIN");

            // пароль  = "admin". Записал его как Хэш
            User u123 = new User(1L, "123", "40bd001563085fc35165329ea1ff5c5ecbdbbeef");
//            User admin = new User(1L, "123", "$2a$10$diL8cHQPMH0jkvpBY/XOEeWcR9ZLVBT/zN6DWRjS9PBx8p3SCXmHK.nMy");
            User u111 = new User(2L, "111", "6216f8a75fd5bb3d5f22b6f9958cdede3fc086c2");
            /** u1234 пароль 111  **/
            User u1234 = new User(3L, "1234", "6216f8a75fd5bb3d5f22b6f9958cdede3fc086c2");
            /** Сохраняем объекты в БД  **/

            /** После сохранения в БД ролей, устанавливаем User-у : admin роль - role_admin **/

            u123.setRole_set(Collections.singleton(role_worker));
            u111.setRole_set(Collections.singleton(role_worker));


            roleRepository.save(role_admin);
            roleRepository.save(role_worker);

            userRepository.save(u123);
            userRepository.save(u111);
            userRepository.save(u1234);

            ADMIN++;
        }
    }

}
