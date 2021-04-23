package com.revaturemax.repositories;

import com.revaturemax.models.Notes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotesRepository extends JpaRepository<Notes, Long> {

    @Query("SELECT n FROM Notes n, Batch b LEFT JOIN FETCH n.employee" +
          " WHERE b.id = :batchId AND n.employee MEMBER OF b.associates AND n.topic.id = :topicId")
    public List<Notes> findNotesByBatchAndTopic(@Param("batchId") long batchId, @Param("topicId") long topicId);

    @Query("SELECT n FROM Notes n LEFT JOIN FETCH n.employee LEFT JOIN FETCH n.topic WHERE n.employee.id = :employeeId AND n.topic.id = :topicId")
    public Notes getNotesByEmployeeIdAndTopicId(@Param("employeeId") long employeeId, @Param("topicId") long topicId);


}
