package com.example.kidslearning.service.impl;

import com.example.kidslearning.dto.KidDto;
import com.example.kidslearning.dto.LoginRequestDto;
import com.example.kidslearning.dto.UserDto;
import com.example.kidslearning.entity.User;
import com.example.kidslearning.exception.ResourceNotFoundException;
import com.example.kidslearning.repository.UserRepository;
import com.example.kidslearning.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;



    @Override
    public UserDto createUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        User saved = userRepository.save(user);
        return mapToDto(saved);
    }



    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        return mapToDto(user);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserDto login(LoginRequestDto loginRequestDto) {

        // 1️⃣ Find user by email
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found "+loginRequestDto.getEmail()));

        // 2️⃣ Check password
        if (!user.getPassword().equals(loginRequestDto.getPassword())) {
            throw new ResourceNotFoundException("Invalid password");
        }

        // 3️⃣ Convert Entity → DTO and return
        return UserDto.fromEntity(user);
    }

    //api response object

    private UserDto mapToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        if (user.getKids() != null) {
            dto.setKids(user.getKids().stream()
                    .map(KidDto::fromEntity)
                    .collect(Collectors.toList()));
        }
        return dto;
    }
}


