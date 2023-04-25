package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.io.entity.AddressEntity;
import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import com.appsdeveloperblog.app.ws.repository.AddressRepository;
import com.appsdeveloperblog.app.ws.repository.UserRepository;
import com.appsdeveloperblog.app.ws.service.AddressService;
import com.appsdeveloperblog.app.ws.shared.dto.AddressDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AddressRepository addressRepository;
    @Override
    public List<AddressDTO> getAddresses(String id) {
       List<AddressDTO> returnValue = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
       UserEntity userEntity = userRepository.findByUserId(id);
       Iterable<AddressEntity> addresses = addressRepository.findAllByUserDetails(userEntity);
        for (AddressEntity address:addresses) {
            returnValue.add(modelMapper.map(address, AddressDTO.class));
        }
       return  returnValue;
    }

    @Override
    public AddressDTO getAddress(String id, String addressId) {
        AddressDTO returnValue = new AddressDTO();
        ModelMapper modelMapper = new ModelMapper();
        UserEntity userEntity = userRepository.findByUserId(id);
        AddressEntity address = addressRepository.findByAddressIdAndUserDetails(addressId, userEntity);
        returnValue = modelMapper.map(address, AddressDTO.class);
        return returnValue;
    }
}
