package com.revaturemax.repositories;

import com.revaturemax.models.Employee;
import com.revaturemax.models.Password;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordRepository extends JpaRepository<Password, Long> {

    Password findByEmployee(Employee employee);

}
