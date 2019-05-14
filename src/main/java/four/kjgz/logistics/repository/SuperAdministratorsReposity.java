package four.kjgz.logistics.repository;


import four.kjgz.logistics.bean.SuperAdministrators;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SuperAdministratorsReposity extends JpaRepository<SuperAdministrators,Integer> {
    public List<SuperAdministrators> findSuperAdministratorsBySuperAdministratorsNumAndPassword(String num,String password);
    @Transactional
    public List<SuperAdministrators> deleteSuperAdministratorsBySuperAdministratorsNum(String num);

    public List<SuperAdministrators>findSuperAdministratorsBySuperAdministratorsNum(String num);

}
