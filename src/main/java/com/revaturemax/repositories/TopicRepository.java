package com.revaturemax.repositories;

import com.revaturemax.model.Topic;
import org.springframework.stereotype.Repository;

@Repository
public class TopicRepository {

    public Topic addTopic(Topic topic){
        return topic;
    }

}
