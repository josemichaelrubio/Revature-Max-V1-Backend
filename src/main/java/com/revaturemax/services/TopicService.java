package com.revaturemax.services;


import com.revaturemax.dto.TopicRequest;
import com.revaturemax.dto.TopicResponse;
import com.revaturemax.models.*;
import com.revaturemax.repositories.BatchRepository;
import com.revaturemax.repositories.CurriculumDayRepository;
import com.revaturemax.repositories.TopicRepository;
import com.revaturemax.repositories.TopicTagRepository;
import com.revaturemax.repositories.EmployeeTopicRepository;
import com.revaturemax.repositories.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TopicService {

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    TopicTagRepository topicTagRepository;

    @Autowired
    BatchRepository batchRepository;

    @Autowired
    CurriculumDayRepository curriculumDayRepo;

    @Autowired
    EmployeeTopicRepository employeeTopicRepository;

    @Autowired
    NotesRepository notesRepository;

    public TopicResponse getTopic(long batchId, long topicId) {
        long employeeId = 1; //TODO: pull id from JWT or passed as param
        Topic topic = topicRepository.getTopicById(topicId);
        if (topic == null) {
            return null; //TODO: throw 404
        }

        //TODO: refactor...
        Float competency = null;
        Notes starredNotes = null;
        EmployeeTopic et = employeeTopicRepository.getEmployeeTopicById(new EmployeeTopicId(employeeId, topicId));
        if (et != null) {
            competency = et.getCompetency();
            starredNotes = et.getFavNotes();
        }
        TopicResponse topicResponse = new TopicResponse(topic, competency);
        Map<Notes, Integer> timesStarred = new HashMap<>();
        for (Employee associate : batchRepository.getBatchById(batchId).getAssociates()) {
            Notes notes = notesRepository.getNotesByEmployeeIdAndTopicId(associate.getId(), topicId);
            if (notes != null && !timesStarred.containsKey(notes)) {
                timesStarred.put(notes, 0);
            }
            et = employeeTopicRepository.getEmployeeTopicById(new EmployeeTopicId(associate.getId(), topicId));
            if (et != null && et.getFavNotes() != null) {
                timesStarred.put(et.getFavNotes(), timesStarred.getOrDefault(et.getFavNotes(), 0) + 1);
            }
        }
        for (Map.Entry<Notes, Integer> entry : timesStarred.entrySet()) {
            Notes notes = entry.getKey();
            topicResponse.addNotesDetails(notes.getEmployee(), notes.equals(starredNotes),
                    entry.getValue(), notes.getNotes());
        }
        topicResponse.sortNotes();

        return topicResponse;
    }

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
