package com.example.demo.Services;

import com.example.demo.DTO.AddressDTO;
import com.example.demo.DTO.UserRequest;
import com.example.demo.DTO.UserResponse;
import com.example.demo.Entities.Address;
import com.example.demo.Entities.User;
import com.example.demo.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserResponse> getAllUsers(){
        return userRepository.findAll().stream()
                .map(this :: maptoUserResponse)
                .collect(Collectors.toList());
    }

    public UserResponse getUserById(Long id){
        return userRepository.findById(id).
                map(this :: maptoUserResponse)
                .orElse(null);
    }

    public UserResponse addUser(UserRequest user){
        User userEntity = maptoUserEntity(user);
        return maptoUserResponse(userRepository.save(userEntity));
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    public UserResponse updateUser(Long id, UserRequest updatedUser){
       return userRepository.findById(id)
               .map(existingUser -> {
                   existingUser.setUsername(updatedUser.getUsername());
                   existingUser.setPassword(updatedUser.getPassword());
                   existingUser.setFirstName(updatedUser.getFirstName());
                   existingUser.setLastName(updatedUser.getLastName());
//                   existingUser.setRole(updatedUser.getRole());
                   userRepository.save(existingUser);
                   return maptoUserResponse(existingUser);
               }).orElse(null);
    }
    private User maptoUserEntity(UserRequest user){
        User userEntity = new User();
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(user.getPassword());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        if(user.getAddress() != null) {
            Address address = new Address();
            address.setCity(user.getAddress().getCity());
            address.setState(user.getAddress().getState());
            address.setZip(user.getAddress().getZip());
            address.setStreet(user.getAddress().getStreet());
            address.setCountry(user.getAddress().getCountry());
            userEntity.setAddress(address);
        }
        return userEntity;
    }
    private UserResponse maptoUserResponse(User user){
        UserResponse response = new UserResponse();
        response.setId(String.valueOf(user.getId()));
        response.setUsername(user.getUsername());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setRole(user.getRole());
//        response.setPassword(user.getPassword());
        if(user.getAddress() != null){
            AddressDTO adr = new AddressDTO();
            adr.setStreet(user.getAddress().getStreet());
            adr.setCity(user.getAddress().getCity());
            adr.setState(user.getAddress().getState());
            adr.setZip(user.getAddress().getZip());
            adr.setCountry(user.getAddress().getCountry());
            response.setAddress(adr);
        }
        return response;
    }
}
