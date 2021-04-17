package com.revaturemax.services;


import com.revaturemax.models.CurriculumDay;
import com.revaturemax.models.Employee;
import com.revaturemax.repositories.BatchRepository;
import com.revaturemax.repositories.CurriculumDayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.revaturemax.dto.BatchResponse;
import com.revaturemax.models.EmployeeQuiz;
import com.revaturemax.projections.BatchSummary;
import com.revaturemax.repositories.EmployeeQuizRepository;
import com.revaturemax.repositories.QuizRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.*;

@Service
public class BatchService {

    Logger logger = LogManager.getLogger(BatchService.class);

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private CurriculumDayRepository curriculumDayRepository;

    @Autowired
    private EmployeeQuizRepository employeeQuizRepository;

    public List<CurriculumDay> getCurriculum(long batchId) {
        long employeeId = 1; //TODO: pull id from JWT or passed as param
        List<CurriculumDay> curriculum = curriculumDayRepository.findCurriculumByBatchId(batchId);
        curriculum.sort(Comparator.comparing(CurriculumDay::getDate));
        return curriculum;
    }

    public long getByAssociate(Employee emp){
        return batchRepository.findBatchIdByEmployeeId(emp.getId());
    }

    public BatchResponse getBatchInfoAndAveragesById(long id) {
        // Get name, description and instructor of batch -- will later be passed to BatchResponse object
        BatchSummary batchSummary = getBasicBatchInfo(id);

        //Get mapping of average quiz score to quiz name for batch
        Map<String, Float> averageQuizScores = getQuizAveragesInfo(id);

        // Get mapping of average competancies by topic for batch
        Map<String, Float> averageCompetenciesByTopic = getTopicCompetencyAveragesInfo(id);

        BatchResponse batchResponse = new BatchResponse(batchSummary);

        for (String key : averageQuizScores.keySet()) {
            batchResponse.addQuizAverage(key, averageQuizScores.get(key));
        }

       /* for (String key : averageCompetenciesByTopic.keySet()) {
            batchResponse.addCompetencyAverage(key, averageCompetenciesByTopic.get(key));
        } */

        return batchResponse;
    }

    public BatchSummary getBasicBatchInfo(Long id) {
        logger.info("Hello");
        return batchRepository.getById(id);
    }

    public Map<String, Float> getQuizAveragesInfo(Long id) {

        Map<String, Float> quizAverages = new TreeMap<String, Float>(new SortAscendingComparatorId());

       List<EmployeeQuiz> employeeQuizzes = employeeQuizRepository.findEmployeeQuizzesByBatchIdAndSort(id);
       List<Float> scoresForQuiz = new ArrayList<>();
       long placeholder = 1;
       for (int i=0; i < employeeQuizzes.size() + 1; i++) {
           if (i == employeeQuizzes.size()) {
               float scoreSum = 0;
               for (int j = 0; j < scoresForQuiz.size(); j++) {
                   scoreSum += scoresForQuiz.get(j);
               }
               float averageForQuiz = scoreSum/(scoresForQuiz.size());
               String quizName = employeeQuizzes.get(i - 1).getQuiz().getName();
               quizAverages.put(quizName, averageForQuiz);
               scoresForQuiz.clear();
               placeholder = placeholder + 1;
           }
           else if (placeholder == employeeQuizzes.get(i).getQuiz().getId()) {
               scoresForQuiz.add(employeeQuizzes.get(i).getScore());
           }
           else {
               float scoreSum = 0;
               for (int j = 0; j < scoresForQuiz.size(); j++) {
                   scoreSum += scoresForQuiz.get(j);
               }
               float averageForQuiz = scoreSum/(scoresForQuiz.size());
               String quizName = employeeQuizzes.get(i - 1).getQuiz().getName();
               quizAverages.put(quizName, averageForQuiz);
               scoresForQuiz.clear();
               placeholder = placeholder + 1;
               scoresForQuiz.add(employeeQuizzes.get(i).getScore());
           }
       }
        
        return quizAverages;
    }

    public Map<String, Float> getTopicCompetencyAveragesInfo(Long id) {
        return null;
    }

    public class SortAscendingComparatorId implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }

    }


}
