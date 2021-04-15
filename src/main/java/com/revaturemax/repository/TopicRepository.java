package com.revaturemax.repository;

import com.revaturemax.model.Topic;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository {

    public Topic getTopicById(long topicId);
}
