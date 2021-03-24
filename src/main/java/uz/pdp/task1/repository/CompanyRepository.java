package uz.pdp.task1.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.task1.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    boolean existsByCorpName(String corpName);

    boolean existsByCorpNameAndIdNot(String corpName, Integer id);
}
