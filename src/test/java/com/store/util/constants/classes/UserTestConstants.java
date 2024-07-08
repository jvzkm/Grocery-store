package com.store.util.constants.classes;

import com.store.model.dto.user.AdminRegisterDto;
import com.store.model.dto.user.LoginUserDto;
import com.store.model.dto.user.RegularUserDto;
import com.store.model.security.User;

import static com.store.model.security.Role.USER;

public class UserTestConstants {
    public static final String USERNAME1 = "belingham@gmail.com";
    public static final User USER1 =
            User.builder()
                    .fullName("Jude")
                    .email("belingham@gmail.com")
                    .build();
    public static final User USER2 =
            User.builder()
                    .fullName("TEST")
                    .email("TEST@gmail.com")
                    .build();

    public static final LoginUserDto loginDto = new LoginUserDto("belingham@gmail.com","1234");
    public static final LoginUserDto unknownDto = new LoginUserDto("djfajfi@gmail.com","1234");
    public static final AdminRegisterDto dto1 = new AdminRegisterDto(USER);
    public static final RegularUserDto dto2 = new RegularUserDto();
    public static final RegularUserDto dto3 = new RegularUserDto();

    public static RegularUserDto regularUserDto(){
        if (dto2.getFullName() == null){
            dto2.setFullName("TEST");
            dto2.setEmail("TEST@gmail.com");
            dto2.setPassword("pass");
        }
        return dto2;
    }

    public static RegularUserDto usedEmailUserDto(){
        if (dto3.getFullName() == null){
            dto3.setFullName("TEST");
            dto3.setEmail("belingham@gmail.com");
            dto3.setPassword("pass");
        }
        return dto3;
    }

    public static AdminRegisterDto registerDto1() {
        if (dto1.getFullName() == null) {
            dto1.setFullName("TEST");
            dto1.setEmail("tresf@gmial.com");
            dto1.setPassword("$2a$10$Fc.OLKCdc.aAwJJqaPeXnuOvJQvCDSBd4EAzOC93dgGHWQFlDLAZa");
        }
        return dto1;
    }


}
