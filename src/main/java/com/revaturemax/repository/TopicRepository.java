package com.revaturemax.repository;

import com.revaturemax.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    @Query("SELECT t FROM Topic t LEFT JOIN FETCH t.tag WHERE t.id = :topicId")
    public Topic getTopicById(@Param("topicId") long topicId);

}
