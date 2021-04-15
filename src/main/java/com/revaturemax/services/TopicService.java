package com.revaturemax.services;


import com.revaturemax.dto.TopicRequest;
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



    public Topic create(Topic topic){
        return topicRepository.save(topic);
    }



    public Topic update(TopicRequest topic){
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
