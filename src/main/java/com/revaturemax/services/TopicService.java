package com.revaturemax.services;


import com.revaturemax.dto.TopicRequest;
import com.revaturemax.model.Topic;
import com.revaturemax.repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicService{

    @Autowired
    TopicRepository topicRepository;

    public Topic create(TopicRequest topic){
        return null;
    }

}
