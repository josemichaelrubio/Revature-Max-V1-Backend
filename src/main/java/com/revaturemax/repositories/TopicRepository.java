package com.revaturemax.repositories;


import com.revaturemax.models.Topic;
import com.revaturemax.projections.Topics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface TopicRepository extends JpaRepository<Topic, Long> {

    @Query("SELECT t FROM Topic t LEFT JOIN FETCH t.tag WHERE t.id = :topicId")
    public Topic getTopicById(@Param("topicId") long topicId);


    @Query("SELECT t FROM Topic t LEFT JOIN FETCH t.tag")
    public List<Topics> getAllTopics();

}
