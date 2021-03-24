package uz.pdp.task1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.task1.entity.Address;
import uz.pdp.task1.entity.Company;
import uz.pdp.task1.entity.Department;
import uz.pdp.task1.entity.Worker;
import uz.pdp.task1.payload.ApiResponse;
import uz.pdp.task1.payload.WorkerDto;
import uz.pdp.task1.repository.AddressRepository;
import uz.pdp.task1.repository.DepartmentRepository;
import uz.pdp.task1.repository.WorkerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {

    @Autowired
    WorkerRepository workerRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    AddressRepository addressRepository;

    //create
    public ApiResponse add(WorkerDto workerDto) {
        Worker worker = new Worker();
        boolean b = workerRepository.existsByPhoneNumber(workerDto.getPhoneNumber());
        if (b) {
            return new ApiResponse("bunday tel number bazada bor", false);
        }

        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (!optionalDepartment.isPresent()) {
            return new ApiResponse("department topilmadi", false);

        } else worker.setName(workerDto.getName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());

        Address address = new Address();
        address.setHomeNumber(workerDto.getHomeNumber());
        address.setStreet(workerDto.getStreet());
        addressRepository.save(address);

        worker.setAddress(address);

        worker.setDepartment(optionalDepartment.get());
        workerRepository.save(worker);
        return new ApiResponse("yangi worker saqlandi", true);
    }

    //read
    public List<Worker> getAll() {
        return workerRepository.findAll();
    }

    //read by id
    public Worker getOne(Integer id) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        return optionalWorker.orElse(null);

    }


    //edit
    public ApiResponse edit(Integer id, WorkerDto workerDto) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);

        if (optionalWorker.isPresent()) {
            Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
            if (optionalDepartment.isPresent()) {
                boolean b = workerRepository.existsByPhoneNumberAndIdNot(workerDto.getPhoneNumber(), id);
                if (!b) {
                    Worker editingworker = optionalWorker.get();


                    editingworker.setName(workerDto.getName());
                    editingworker.setPhoneNumber(workerDto.getPhoneNumber());
                    Address address = new Address();
                    address.setHomeNumber(workerDto.getHomeNumber());
                    address.setStreet(workerDto.getStreet());
                    addressRepository.save(address);

                    editingworker.setAddress(address);
                    editingworker.setDepartment(optionalDepartment.get());
                    workerRepository.save(editingworker);
                    return new ApiResponse("worker o`zgartirildi", true);
                } else return new ApiResponse("bunday tel Number oldin kiritilgan", false);
            } else return new ApiResponse("department topilmadi", false);
        } else return new ApiResponse("bunday worker topilmadi", false);
    }

    //delete by id
    public ApiResponse delete(Integer id) {
        try {
            workerRepository.deleteById(id);
            return new ApiResponse("Worker o`chirildi", true);
        } catch (Exception e) {
            return new ApiResponse("XATOLIK", false);
        }
    }


}
