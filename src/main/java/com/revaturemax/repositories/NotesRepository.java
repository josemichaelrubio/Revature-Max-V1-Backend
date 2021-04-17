package com.revaturemax.repositories;

import com.revaturemax.models.Notes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotesRepository extends JpaRepository<Notes, Long> {

    @Query("SELECT n FROM Notes n LEFT JOIN FETCH n.employee LEFT JOIN FETCH n.topic WHERE n.employee.id = :employeeId AND n.topic.id = :topicId")
    public Notes getNotesByEmployeeIdAndTopicId(@Param("employeeId") long employeeId, @Param("topicId") long topicId);

}
