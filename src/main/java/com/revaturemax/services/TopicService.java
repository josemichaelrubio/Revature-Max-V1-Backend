package com.revaturemax.services;

import com.revaturemax.model.Topic;
import com.revaturemax.repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    public Topic postNewTopic(Topic topic){
        return topicRepository.addTopic(topic);
    }
}
