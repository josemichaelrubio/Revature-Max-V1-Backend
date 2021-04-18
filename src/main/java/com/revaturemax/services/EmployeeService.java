package com.revaturemax.services;

import com.revaturemax.dto.EmployeeQuizResponse;
import com.revaturemax.dto.EmployeeTopicResponse;
import com.revaturemax.dto.TopicRequest;
import com.revaturemax.models.*;
import com.revaturemax.repositories.EmployeeQuizRepository;
import com.revaturemax.repositories.EmployeeRepository;
import com.revaturemax.repositories.EmployeeTopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository empRepo;

    @Autowired
    EmployeeQuizRepository empQuizRepo;

    @Autowired
    EmployeeTopicRepository empTopicRepo;


    public Employee getById(long id){
        return empRepo.getOne(id);
    }

    public Employee add(Employee employee) {
        return empRepo.save(employee);
    }

    public void deleteEmployee(long id) {
        empRepo.deleteById(id);
    }

//    public void updateEmployee(Employee employee){return employeeRepository.save(employee);}

    public List<EmployeeQuizResponse> getQuizzesById(Employee emp){
        List<EmployeeQuiz> quizzes = empQuizRepo.findByEmployeeEquals(emp);
        List<EmployeeQuizResponse> quizResponses = new ArrayList<>();
        for(EmployeeQuiz q : quizzes){
            quizResponses.add(new EmployeeQuizResponse(q.getId().getQuizId(), q.getScore()));
        }
        return quizResponses;
    }

    public List<EmployeeTopicResponse> getTopicsById(Employee emp){
        List<EmployeeTopic> topics =  empTopicRepo.findByEmployeeEquals(emp);
        List<EmployeeTopicResponse> topicResponses = new ArrayList<>();
        for(EmployeeTopic t : topics){
            topicResponses.add(new EmployeeTopicResponse(t.getTopic().getName(), t.getTopic().getTag().getName(), t.getCompetency()));
        }
        return topicResponses;
    }
//
//    public EmployeeTopic update(EmployeeTopicId id, EmployeeTopicResponse empTopic){
//        EmployeeTopic updateTopic = empTopicRepo.getOne(id);
//        updateTopic.setCompetency(empTopic.getCompetency());
//        updateTopic.setFavNotes(empTopic.;
//        empTopicRepo.save(updateTopic);
//        return updateTopic;
//    }

}
