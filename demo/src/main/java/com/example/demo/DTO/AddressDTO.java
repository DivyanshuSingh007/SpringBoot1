package com.example.demo.DTO;

import lombok.Data;

@Data
public class AddressDTO {
//    private Long id;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String country;
}
