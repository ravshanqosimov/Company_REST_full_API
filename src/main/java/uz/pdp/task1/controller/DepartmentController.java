package uz.pdp.task1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task1.entity.Address;
import uz.pdp.task1.entity.Department;
import uz.pdp.task1.payload.AddressDto;
import uz.pdp.task1.payload.ApiResponse;
import uz.pdp.task1.payload.DepartmentDto;
import uz.pdp.task1.service.AddressService;
import uz.pdp.task1.service.DepartmentServise;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/department")
public class DepartmentController {
    @Autowired
    DepartmentServise departmentServise;



        //create
        @PostMapping
        public HttpEntity<ApiResponse> addDepartment(@Valid @RequestBody DepartmentDto departmentDto  ) {
        ApiResponse apiResponse = departmentServise.addDepartment(departmentDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }

        //read

        @GetMapping
        public HttpEntity<List<Department>> getAll() {

        List<Department> department = departmentServise.getDepartment();
        return ResponseEntity.ok(department);
    }

        @GetMapping("/{id}")
        public HttpEntity<Department> getDepartment(@PathVariable Integer id) {

        Department oneDepartment = departmentServise.getOneDepartment(id);
        return ResponseEntity.ok(oneDepartment);
    }

        //update
        @PutMapping("/{id}")
        public HttpEntity<ApiResponse> editDepartment(@PathVariable Integer id, @Valid @RequestBody    DepartmentDto departmentDto  ) {
        ApiResponse apiResponse = departmentServise.editingDepartment(id, departmentDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(apiResponse);
    }

        //delete
        @DeleteMapping("/{id}")
        public ResponseEntity<ApiResponse> deleteAddress(@PathVariable Integer id) {
        ApiResponse apiResponse = departmentServise.deleteDepartment(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409).body(apiResponse);

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
