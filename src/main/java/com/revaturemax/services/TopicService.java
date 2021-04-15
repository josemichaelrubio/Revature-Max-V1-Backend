package com.revaturemax.services;


import com.revaturemax.dto.TopicRequest;
import com.revaturemax.dto.TopicUpdateRequest;
import com.revaturemax.model.Batch;
import com.revaturemax.model.CurriculumDay;
import com.revaturemax.model.Topic;
import com.revaturemax.model.TopicTag;
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

    public TopicTag createTag(String tag){
        return topicTagRepository.save(new TopicTag(tag));
    }

    public List<TopicTag> getTopicTags(){
        return topicTagRepository.findAll();
    }

    public Topic create(TopicRequest topic){
        Topic newTopic = new Topic(topic.getTag(), topic.getTopicName());
        return topicRepository.save(newTopic);
    }



    public Topic update(TopicUpdateRequest topic){
        TopicTag topicTag = topicTagRepository.findByName(topic.getTopicTagName());
        if(topicTag!=null){
            Topic newTopic = new Topic(topicTag, topic.getTopicName());
            Batch batch = batchRepository.getOne(topic.getBatchId());
            CurriculumDay curriculumDay = curriculumDayRepo.findByBatchAndDate(batch, topic.getDate());
            curriculumDay.addTopic(newTopic);
            curriculumDayRepo.save(curriculumDay);
            return topicRepository.save(newTopic);
        } else {
            TopicTag newTopicTag = topicTagRepository.save(new TopicTag(topic.getTopicTagName()));
            return topicRepository.save(new Topic(newTopicTag, topic.getTopicName()));
        }}

}
