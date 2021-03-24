package uz.pdp.task1.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.task1.entity.Address;
import uz.pdp.task1.entity.Company;
import uz.pdp.task1.payload.ApiResponse;
import uz.pdp.task1.payload.CompanyDto;
import uz.pdp.task1.repository.AddressRepository;
import uz.pdp.task1.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    AddressRepository addressRepository;


    //METHODS
    //create
    public ApiResponse add(CompanyDto companyDto) {
        Company company = new Company();
        boolean name = companyRepository.existsByCorpName(companyDto.getCorpName());
        if (name) return new ApiResponse("bunday CorpName mavjud", false);
        else {
            company.setCorpName(companyDto.getCorpName());
            company.setDirectorName(companyDto.getDirectorName());
            Address address = new Address();
            address.setStreet(companyDto.getStreet());
            address.setHomeNumber(companyDto.getHomeNumber());

            addressRepository.save(address);
            company.setAddress(address);
            companyRepository.save(company);

            return new ApiResponse("yangi Company saqlandi", true);
        }

    }

    //read
    public List<Company> getAll() {
        return companyRepository.findAll();
    }

    //read by id
    public Company getOne(Integer id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        return optionalCompany.orElse(null);

    }

    //edit company by id
    public ApiResponse edit(Integer id, CompanyDto companyDto) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isPresent()) {
            boolean exists = companyRepository.existsByCorpNameAndIdNot(companyDto.getCorpName(), id);
            if (!exists) {
                Company editingcompany = optionalCompany.get();
                editingcompany.setCorpName(companyDto.getCorpName());
                editingcompany.setDirectorName(companyDto.getDirectorName());
                Address address = new Address();
                address.setStreet(companyDto.getStreet());
                address.setHomeNumber(companyDto.getHomeNumber());
                addressRepository.save(address);
                editingcompany.setAddress(address);
                companyRepository.save(editingcompany);
                return new ApiResponse("Company o`zgartirildi", true);
            } else
                return new ApiResponse("Bunday corpName avvaldan mavjud. Boshqa nom kiriting ", false);


        } else return new ApiResponse("Company topilmadi", false);


    }


    //delete by id
    public ApiResponse deleteById(Integer id) {
        try {
            companyRepository.deleteById(id);
            return new ApiResponse("Company o`chirildi", true);
        } catch (Exception e) {
            return new ApiResponse("XATOLIK", false);
        }
    }

}
