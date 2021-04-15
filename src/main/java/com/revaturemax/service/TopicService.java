package com.revaturemax.service;

import com.revaturemax.dto.TopicResponse;
import com.revaturemax.model.Employee;
import com.revaturemax.model.EmployeeTopic;
import com.revaturemax.model.Notes;
import com.revaturemax.model.Topic;
import com.revaturemax.repository.BatchRepository;
import com.revaturemax.repository.EmployeeTopicRepository;
import com.revaturemax.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TopicService {

    @Autowired
    TopicRepository topicRepository;
    @Autowired
    NotesRepository notesRepository;
    @Autowired
    EmployeeTopicRepository employeeTopicRepository;
    @Autowired
    BatchRepository batchRepository;

    public TopicResponse getTopic(long batchId, long topicId) {
        TopicResponse topicResponse = null;
        Topic topic = topicRepository.getTopicById(topicId);
        //if null, throw 404
        long employeeId = 1;
        EmployeeTopic et = employeeTopicRepository.getEmployeeTopicById(employeeId, topicId);
        Float competency = et.getCompetency();
        Notes starred = et.getFavNotes();
        topicResponse = new TopicResponse(topic, competency);

        Map<Notes, Integer> timesStarred = new HashMap<>();
        for (Employee associate : batchRepository.getBatchById(batchId).getAssociates()) {
            et = employeeTopicRepository.getEmployeeTopicById(associate.getId(), topicId);
        }
        return topicResponse;
    }
}
