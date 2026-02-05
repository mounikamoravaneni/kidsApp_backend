package com.example.kidslearning.dto;

import com.example.kidslearning.entity.User;
import lombok.Data;

import java.util.List;
//Used for API request/response
@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private List<KidDto> kids;

    public static UserDto fromEntity(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        if (user.getKids() != null) {
            dto.setKids(
                    user.getKids()
                            .stream()
                            .map(KidDto::fromEntity)
                            .toList()
            );
        }
        return dto;
    }
}

