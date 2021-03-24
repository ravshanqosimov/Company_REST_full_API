package uz.pdp.task1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task1.entity.Address;
import uz.pdp.task1.payload.AddressDto;
import uz.pdp.task1.payload.ApiResponse;
import uz.pdp.task1.service.AddressService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/address")
public class AddressController {
    @Autowired
    AddressService addressService;

    //create
    @PostMapping
    public HttpEntity<ApiResponse> addAddress(@Valid @RequestBody AddressDto addressDto) {
        ApiResponse apiResponse = addressService.addAddress(addressDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }

    //read

    @GetMapping
    public HttpEntity<List<Address>> getAll() {

        List<Address> addresses = addressService.getAddresses();
        return ResponseEntity.ok(addresses);
    }

    @GetMapping("/{id}")
    public HttpEntity<Address> getAddress(@PathVariable Integer id) {

        Address oneAddress = addressService.getOneAddress(id);
        return ResponseEntity.ok(oneAddress);
    }

    //update
    @PutMapping("/{id}")
    public HttpEntity<ApiResponse> editAddress(@PathVariable Integer id, @Valid @RequestBody AddressDto addressDto) {
        ApiResponse apiResponse = addressService.editingAddress(id, addressDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(apiResponse);
    }

    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteAddress(@PathVariable Integer id) {
        ApiResponse apiResponse = addressService.deleteAddress(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 204 : 409).body(apiResponse);

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
