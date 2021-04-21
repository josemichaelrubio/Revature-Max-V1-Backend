package com.revaturemax.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.revaturemax.dto.TopicResponse;
import com.revaturemax.models.*;
import com.revaturemax.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TopicService {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    TopicRepository topicRepository;
    @Autowired
    TopicTagRepository topicTagRepository;
    @Autowired
    BatchRepository batchRepository;
    @Autowired
    EmployeeTopicRepository employeeTopicRepository;
    @Autowired
    NotesRepository notesRepository;

    public ResponseEntity<String> setEmployeeTopic(Token token, long employeeId, long topicId,
                                                   EmployeeTopic employeeTopic)
    {
        if (token.getEmployeeId() != employeeId) return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
        if (!topicRepository.existsById(topicId)) return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        employeeTopic.setEmployee(new Employee(employeeId));
        employeeTopic.setTopic(new Topic(topicId));
        employeeTopicRepository.save(employeeTopic);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    public ResponseEntity<String> getTopic(Token token, long batchId, long topicId) {
        long employeeId = token.getEmployeeId();
        Topic topic = topicRepository.getTopicById(topicId);
        if (topic == null) return new ResponseEntity<String>(HttpStatus.NOT_FOUND);

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
            topicResponse.addNotesDetails(notes.getEmployee(), notes.getId(),
                    entry.getValue(), notes.getNotes());
        }
        topicResponse.sortNotes();

        try {
            SimpleFilterProvider filter = new SimpleFilterProvider();
            filter.addFilter("Topic", SimpleBeanPropertyFilter.serializeAll());
            return new ResponseEntity<String>(objectMapper.writer(filter).writeValueAsString(topicResponse),
                    HttpStatus.OK);
        } catch (JsonProcessingException exception) {
            exception.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
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


    public Topic update(long id, Topic topic){
        Topic updateTopic = topicRepository.getOne(id);
        updateTopic.setTag(topic.getTag());
        updateTopic.setName(topic.getTag().getName());
        topicRepository.save(updateTopic);
        return updateTopic;
    }

    public void delete(long id){
        topicRepository.deleteById(id);
    }

}
