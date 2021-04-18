package com.revaturemax.repositories;

import com.revaturemax.models.Password;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRepository extends JpaRepository<Password, Long> {}
