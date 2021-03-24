package uz.pdp.task1.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task1.entity.Worker;
import uz.pdp.task1.payload.ApiResponse;
import uz.pdp.task1.payload.WorkerDto;
import uz.pdp.task1.service.WorkerService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/worker")


public class WorkerController {

    @Autowired
    WorkerService workerService;

    //create
    @PostMapping
    public HttpEntity<ApiResponse> addWorker(@Valid @RequestBody WorkerDto workerDto) {
        ApiResponse apiResponse = workerService.add(workerDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }

    //read

    @GetMapping
    public HttpEntity<List<Worker>> getAll() {

        List<Worker> workerList = workerService.getAll();
        return ResponseEntity.ok(workerList);
    }

    @GetMapping("/{id}")
    public HttpEntity<Worker> getDepartment(@PathVariable Integer id) {

        Worker worker = workerService.getOne(id);
        return ResponseEntity.ok(worker);
    }

    //update
    @PutMapping("/{id}")
    public HttpEntity<ApiResponse> editDepartment(@PathVariable Integer id, @Valid @RequestBody WorkerDto workerDto) {
        ApiResponse apiResponse = workerService.edit(id, workerDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(apiResponse);
    }

    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteAddress(@PathVariable Integer id) {
        ApiResponse apiResponse = workerService.delete(id);
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
