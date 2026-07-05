package com.app.ecom.Service;

import com.app.ecom.Model.Address;
import com.app.ecom.Model.Dto.AddressDto;
import com.app.ecom.Model.Dto.UserRequestDto;
import com.app.ecom.Model.Dto.UserResponseDto;
import com.app.ecom.Repository.UserRepository;
import com.app.ecom.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService  {

    private final UserRepository userRepository;

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    public void addUser(UserRequestDto userRequestDto) {
        User user = toEntity(userRequestDto);
        userRepository.save(user);
    }

    public Optional<UserResponseDto> getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::toResponseDto);
    }

    public boolean updateUser(Long id, UserRequestDto userRequestDto) {
        return userRepository.findById(id)
                .map(user -> {

                    user.setFirstName(userRequestDto.getFirstName());
                    user.setLastName(userRequestDto.getLastName());
                    user.setEmail(userRequestDto.getEmail());
                    user.setPhone(userRequestDto.getPhone());
                    user.setRole(userRequestDto.getRole());

                    if(user.getAddress() != null) {
                        Address address = user.getAddress();
                        address.setStreet(userRequestDto.getAddress().getStreet());
                        address.setCity(userRequestDto.getAddress().getCity());
                        address.setState(userRequestDto.getAddress().getState());
                        address.setCountry(userRequestDto.getAddress().getCountry());
                        address.setZipcode(userRequestDto.getAddress().getZipcode());
                        user.setAddress(address);
                    }
                    userRepository.save(user);
                    return true;
                })
                .orElse(false);
    }

    private UserResponseDto toResponseDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setFirstName(user.getFirstName());
        userResponseDto.setLastName(user.getLastName());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setPhone(user.getPhone());
        userResponseDto.setRole(user.getRole());
        userResponseDto.setCreatedAt(user.getCreatedAt());
        userResponseDto.setUpdatedAt(user.getUpdatedAt());

        if(user.getAddress() != null) {
            AddressDto addressDto = new AddressDto();
            addressDto.setId(user.getAddress().getId());
            addressDto.setStreet(user.getAddress().getStreet());
            addressDto.setCity(user.getAddress().getCity());
            addressDto.setState(user.getAddress().getState());
            addressDto.setCountry(user.getAddress().getCountry());
            addressDto.setZipcode(user.getAddress().getZipcode());
            userResponseDto.setAddress(addressDto);
        }
        return userResponseDto;
    }

    private User toEntity(UserRequestDto userRequestDto) {
        User user = new User();
        user.setFirstName(userRequestDto.getFirstName());
        user.setLastName(userRequestDto.getLastName());
        user.setEmail(userRequestDto.getEmail());
        user.setPhone(userRequestDto.getPhone());
        user.setRole(userRequestDto.getRole());

        if(userRequestDto.getAddress() != null) {
            Address address = new Address();
            address.setStreet(userRequestDto.getAddress().getStreet());
            address.setCity(userRequestDto.getAddress().getCity());
            address.setState(userRequestDto.getAddress().getState());
            address.setCountry(userRequestDto.getAddress().getCountry());
            address.setZipcode(userRequestDto.getAddress().getZipcode());
            user.setAddress(address);
        }
        return user;
    }
}
