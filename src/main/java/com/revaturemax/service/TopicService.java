package com.revaturemax.service;

import com.revaturemax.dto.TopicResponse;
import com.revaturemax.model.*;
import com.revaturemax.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TopicService {

    @Autowired
    TopicRepository topicRepository;
    @Autowired
    BatchRepository batchRepository;
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

}
