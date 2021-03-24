package uz.pdp.task1.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.task1.entity.Company;
import uz.pdp.task1.entity.Department;
import uz.pdp.task1.payload.ApiResponse;
import uz.pdp.task1.payload.DepartmentDto;
import uz.pdp.task1.repository.CompanyRepository;
import uz.pdp.task1.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServise {

    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    CompanyRepository companyRepository;

    //METHODS
//create
    public ApiResponse addDepartment(DepartmentDto departmentDto) {
        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (optionalCompany.isPresent()) {
            Department department = new Department();
            department.setName(departmentDto.getName());
            department.setCompany(optionalCompany.get());
            departmentRepository.save(department);
            return new ApiResponse("department saqlandi", true);
        } else return new ApiResponse("Xatolik", false);

    }

    //get
    public List<Department> getDepartment() {
        return departmentRepository.findAll();
    }

    public Department getOneDepartment(Integer id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        return optionalDepartment.orElse(null);

    }

    //update
    public ApiResponse editingDepartment(Integer id, DepartmentDto departmentDto) {
        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (optionalCompany.isPresent()) {
            Optional<Department> optionalDepartment = departmentRepository.findById(id);
            if (optionalDepartment.isPresent()) {
                Department editingdepartment = optionalDepartment.get();
                editingdepartment.setName(departmentDto.getName());
                editingdepartment.setCompany(optionalCompany.get());
                departmentRepository.save(editingdepartment);
                return new ApiResponse("Department tahrirlandi", true);
            } else return new ApiResponse("department id topilmadi  ", false);
        } else return new ApiResponse("company da Xatolik", false);


    }

    //delete
    public ApiResponse deleteDepartment(Integer id) {
        try {
            departmentRepository.deleteById(id);
            return new ApiResponse("Department o`chirildi", true);
        } catch (Exception e) {
            return new ApiResponse("XATOLIK", false);
        }
    }

}
