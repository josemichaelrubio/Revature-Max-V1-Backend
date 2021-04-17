package com.revaturemax.repositories;

import com.revaturemax.models.Quiz;
import com.revaturemax.projections.QuizNameOnly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    QuizNameOnly getById(long id);
}
