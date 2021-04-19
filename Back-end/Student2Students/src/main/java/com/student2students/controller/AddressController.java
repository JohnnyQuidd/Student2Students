package com.student2students.controller;

import com.student2students.dto.AddressDTO;
import com.student2students.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
public class AddressController {
    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public ResponseEntity createNewAddress(@RequestBody AddressDTO addressDTO) {
        return addressService.createNewAddress(addressDTO);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity deleteAddress(@PathVariable("addressId") Long addressId) {
        return addressService.deleteAddressById(addressId);
    }
}
