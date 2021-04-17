package com.revaturemax.services;

import com.revaturemax.dto.EmployeeQuizResponse;
import com.revaturemax.dto.EmployeeTopicResponse;
import com.revaturemax.models.Employee;
import com.revaturemax.models.EmployeeQuiz;
import com.revaturemax.models.EmployeeTopic;
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
}
