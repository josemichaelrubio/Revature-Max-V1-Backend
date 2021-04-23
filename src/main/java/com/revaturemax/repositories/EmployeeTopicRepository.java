package com.revaturemax.repositories;

import com.revaturemax.models.Employee;
import com.revaturemax.models.EmployeeTopic;
import com.revaturemax.models.EmployeeTopicId;
import com.revaturemax.models.Notes;
import com.revaturemax.projections.TagAverage;
import com.revaturemax.projections.TopicAverage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeTopicRepository extends JpaRepository<EmployeeTopic, EmployeeTopicId> {

    @Query("SELECT et FROM EmployeeTopic et LEFT JOIN FETCH et.topic t LEFT JOIN FETCH t.tag WHERE et.employee = :employee")
    List<EmployeeTopic> findByEmployeeEquals(@Param("employee") Employee employee);

    @Query("SELECT et FROM EmployeeTopic et LEFT JOIN FETCH et.employee WHERE et.id = :id")
    EmployeeTopic getEmployeeTopicById(@Param("id") EmployeeTopicId id);

    @Query("SELECT eq FROM EmployeeTopic eq, Batch b LEFT JOIN FETCH eq.topic AS t LEFT JOIN FETCH t.tag WHERE b.id = :batchId AND eq.employee MEMBER OF b.associates ORDER BY t.tag.id")
    List<EmployeeTopic> getEmployeeTopicsByBatchIdAndSort(@Param("batchId") long batchId);

    @Query("SELECT et.favNotes FROM EmployeeTopic et, Batch b" +
            " WHERE b.id = :batchId AND et.employee MEMBER OF b.associates" +
            " AND et.topic.id = :topicId AND et.favNotes IS NOT NULL")
    List<Notes> getStarredNotesByBatchAndTopic(@Param("batchId") long batchId, @Param("topicId") long topicId);

    @Query("SELECT new com.revaturemax.projections.TopicAverage(et.topic.id, AVG(et.competency), COUNT(et.topic.id))" +
            " FROM EmployeeTopic et, Batch b" +
            " WHERE b.id = :batchId AND et.employee MEMBER OF b.associates" +
            " GROUP BY et.topic")
    List<TopicAverage> findTopicAveragesByBatch(@Param("batchId") long batchId);

    @Query("SELECT new com.revaturemax.projections.TagAverage(et.topic.tag.id, AVG(et.competency), COUNT(et.topic.tag.id))" +
            " FROM EmployeeTopic et, Batch b" +
            " WHERE b.id = :batchId AND et.employee MEMBER OF b.associates" +
            " GROUP BY et.topic.tag")
    List<TagAverage> findTagAveragesByBatch(@Param("batchId") long batchId);

}
