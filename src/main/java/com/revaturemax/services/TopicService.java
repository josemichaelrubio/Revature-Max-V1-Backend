package com.revaturemax.services;


import com.revaturemax.dto.TopicRequest;
import com.revaturemax.models.Topic;
import com.revaturemax.models.TopicTag;
import com.revaturemax.repositories.BatchRepository;
import com.revaturemax.repositories.CurriculumDayRepository;
import com.revaturemax.repositories.TopicRepository;
import com.revaturemax.repositories.TopicTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService{

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    TopicTagRepository topicTagRepository;

    @Autowired
    BatchRepository batchRepository;

    @Autowired
    CurriculumDayRepository curriculumDayRepo;

    // all '/tags' methods are implemented for testing features

    public TopicTag createTag(TopicTag tag){
        return topicTagRepository.save(new TopicTag(tag.getName()));
    }

    public void deleteTag(long id){
        topicTagRepository.deleteById(id);
    }

    public List<TopicTag> getTopicTags(){
        return topicTagRepository.findAll();
    }


    // Topic CRUD methods in the service layer


    public List<Topic> getAll(){
        return topicRepository.findAll();
    }

    public Topic create(Topic topic){
        return topicRepository.save(topic);
    }


    public Topic update(long id, TopicRequest topic){
        Topic updateTopic = topicRepository.getOne(id);
        updateTopic.setTag(topic.getTag());
        updateTopic.setName(topic.getTopicName());
        topicRepository.save(updateTopic);
        return updateTopic;
    }

    public void delete(long id){
        topicRepository.deleteById(id);
    }

}
