package uz.pdp.task1.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task1.entity.Company;
import uz.pdp.task1.payload.ApiResponse;
import uz.pdp.task1.payload.CompanyDto;
import uz.pdp.task1.service.CompanyService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/company")
public class CompanyController {
    @Autowired
    CompanyService companyService;

    //CREATE
    @PostMapping
    public HttpEntity<ApiResponse> addCom(@Valid @RequestBody CompanyDto companyDto) {

        ApiResponse apiResponse = companyService.add(companyDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);

    }

    //READ ALL
    @GetMapping
    public ResponseEntity<List<Company>> getAll() {
        List<Company> list = companyService.getAll();
        return ResponseEntity.ok(list);
    }

    //READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<Company> getOne(@PathVariable Integer id) {
        Company company = companyService.getOne(id);
        return ResponseEntity.ok(company);
    }

    //update
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> edit(@PathVariable Integer id,@Valid @RequestBody CompanyDto companyDto) {
        ApiResponse apiResponse = companyService.edit(id, companyDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(apiResponse);
    }

    //delete
    @DeleteMapping("/{id}")

    public ResponseEntity<?> delete(@PathVariable Integer id) {
        ApiResponse apiResponse = companyService.deleteById(id);
        return ResponseEntity.status(apiResponse.isSuccess()?204:209).body(apiResponse);
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
