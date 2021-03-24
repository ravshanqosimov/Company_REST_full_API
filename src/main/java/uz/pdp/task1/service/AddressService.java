package uz.pdp.task1.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.task1.entity.Address;
import uz.pdp.task1.payload.AddressDto;
import uz.pdp.task1.payload.ApiResponse;
import uz.pdp.task1.repository.AddressRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    //METHODS
//create
    public ApiResponse addAddress(AddressDto addressDto) {
        Address address = new Address();
        address.setStreet(addressDto.getStreet());
        address.setHomeNumber(addressDto.getHomeNumber());
        addressRepository.save(address);
        return new ApiResponse("address saqlandi", true);

    }

    //get
    public List<Address> getAddresses() {
        return addressRepository.findAll();
    }

    public Address getOneAddress(Integer id) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        return optionalAddress.orElse(null);

    }

    //update
    public ApiResponse editingAddress(Integer id, AddressDto addressDto) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isPresent()) {
            Address editingaddress = optionalAddress.get();
            editingaddress.setStreet(addressDto.getStreet());
            editingaddress.setHomeNumber(addressDto.getHomeNumber());
            addressRepository.save(editingaddress);
            return new ApiResponse("address tahrirlandi", true);
        } else return new ApiResponse("bunday id'li address mavjud emas", false);


    }

    //delete
    public ApiResponse deleteAddress(Integer id) {
        try {
            addressRepository.deleteById(id);
            return new ApiResponse("address o`chirildi", true);
        } catch (Exception e) {
            return new ApiResponse("XATOLIK", false);
        }
    }

}
