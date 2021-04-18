package com.revaturemax.services;


import com.revaturemax.models.CurriculumDay;
import com.revaturemax.models.Employee;
import com.revaturemax.models.EmployeeTopic;
import com.revaturemax.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.revaturemax.dto.BatchResponse;
import com.revaturemax.models.EmployeeQuiz;
import com.revaturemax.projections.BatchSummary;
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

    @Autowired
    private EmployeeTopicRepository employeeTopicRepository;

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
        Map<String, List<String>> averageQuizScores = getQuizAveragesInfo(id);
        for (String key : averageQuizScores.keySet()) {
            logger.info("key: " + key);
            logger.info("value: " + averageQuizScores.get(key));
        }

        // Get mapping of average competancies by topic for batch
        Map<String, List<String>> averageCompetenciesByTopic = getTopicCompetencyAveragesInfo(id);

        BatchResponse batchResponse = new BatchResponse(batchSummary);

       for (String key : averageQuizScores.keySet()) {
            List<String> values = averageQuizScores.get(key);
            batchResponse.addQuizAverage(key, values.get(0), values.get(1));
        }

        for (String key : averageCompetenciesByTopic.keySet()) {
            List <String> values = averageCompetenciesByTopic.get(key);
            batchResponse.addCompetencyAverage(key, values.get(0), values.get(1));
        }

        return batchResponse;
    }

    public BatchSummary getBasicBatchInfo(Long id) {
        logger.info("Hello");
        return batchRepository.getById(id);
    }

    public Map<String, List<String>> getQuizAveragesInfo(Long id) {

        Map<String, List<String>> quizAverages = new TreeMap<>(new SortAscendingComparatorId());

       List<EmployeeQuiz> employeeQuizzes = employeeQuizRepository.findEmployeeQuizzesByBatchIdAndSort(id);
       List<Float> scoresForQuiz = new ArrayList<>();
       long placeholder = 1;
       long count = 0;
       for (int i=0; i < employeeQuizzes.size() + 1; i++) {
           if (i == employeeQuizzes.size()) {
               float scoreSum = 0;
               for (int j = 0; j < scoresForQuiz.size(); j++) {
                   scoreSum += scoresForQuiz.get(j);
               }
               float averageForQuiz = scoreSum/(scoresForQuiz.size());
               String formatAverage = String.format("%.1f", averageForQuiz);
               String quizName = employeeQuizzes.get(i - 1).getQuiz().getName();
               String strCount = "" + count;
               List<String> quizInfo = new ArrayList<>();
               quizInfo.add(formatAverage);
               quizInfo.add(strCount);
               quizAverages.put(quizName, quizInfo);
               scoresForQuiz.clear();
           }
           else if (placeholder == employeeQuizzes.get(i).getQuiz().getId()) {
               scoresForQuiz.add(employeeQuizzes.get(i).getScore());
               count = count + 1;
           }
           else {
               float scoreSum = 0;
               for (int j = 0; j < scoresForQuiz.size(); j++) {
                   scoreSum += scoresForQuiz.get(j);
               }
               float averageForQuiz = scoreSum/(scoresForQuiz.size());
               String formatAverage = String.format("%.1f", averageForQuiz);
               String quizName = employeeQuizzes.get(i - 1).getQuiz().getName();
               String strCount = "" + count;
               List<String> quizInfo = new ArrayList<>();
               quizInfo.add(formatAverage);
               quizInfo.add(strCount);
               quizAverages.put(quizName, quizInfo);
               scoresForQuiz.clear();
               count = 0;
               placeholder = employeeQuizzes.get(i).getQuiz().getId();
               scoresForQuiz.add(employeeQuizzes.get(i).getScore());
               count = count + 1;
           }
       }
        
        return quizAverages;

    }

    public Map<String, List<String>> getTopicCompetencyAveragesInfo(Long id) {
        Map<String, List<String>> topicCompetencyAverages = new TreeMap<>(new SortAscendingComparatorId());
        List<EmployeeTopic> employeeTopics = employeeTopicRepository.getEmployeeTopicsByBatchIdAndSort(id);
        List<Float> competencyScoresForTopic = new ArrayList<>();
        long placeholder = 1;
        long count = 0;
        for (int i = 0; i < employeeTopics.size() + 1; i++) {
            if (i == employeeTopics.size()) {
                float sumCompetencies = 0;
                for (int j = 0; j < competencyScoresForTopic.size(); j ++) {
                    sumCompetencies += competencyScoresForTopic.get(j);
                }
                float averageForTopic = sumCompetencies/(competencyScoresForTopic.size());
                String formatAverage = String.format("%.1f", averageForTopic);
                String topicName = employeeTopics.get(i - 1).getTopic().getTag().getName();
                String strCount = "" + count;
                List<String> topicInfo = new ArrayList<>();
                topicInfo.add(formatAverage);
                topicInfo.add(strCount);
                topicCompetencyAverages.put(topicName, topicInfo);
                competencyScoresForTopic.clear();
            }

            else if (placeholder == employeeTopics.get(i).getTopic().getTag().getId()) {
                competencyScoresForTopic.add(employeeTopics.get(i).getCompetency());
                count = count + 1;
            }

            else {
                float sumCompetencies = 0;
                for (int j = 0; j < competencyScoresForTopic.size(); j++) {
                    sumCompetencies += competencyScoresForTopic.get(j);
                }
                float averageForTopic = sumCompetencies/(competencyScoresForTopic.size());
                String formatAverage = String.format("%.1f", averageForTopic);
                String topicName = employeeTopics.get(i - 1).getTopic().getTag().getName();
                String strCount = "" + count;
                List<String> topicInfo = new ArrayList<>();
                topicInfo.add(formatAverage);
                topicInfo.add(strCount);
                topicCompetencyAverages.put(topicName, topicInfo);
                competencyScoresForTopic.clear();
                count = 0;
                placeholder = employeeTopics.get(i).getTopic().getTag().getId();
                competencyScoresForTopic.add(employeeTopics.get(i).getCompetency());
                count = count + 1;

            }


        }

        return topicCompetencyAverages;
    }

    public class SortAscendingComparatorId implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }

    }


}
