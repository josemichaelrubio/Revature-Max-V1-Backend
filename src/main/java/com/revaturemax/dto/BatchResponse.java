package com.revaturemax.dto;

import com.revaturemax.models.Batch;
import com.revaturemax.models.CompetencyAverage;
import com.revaturemax.models.QuizAverage;

import java.io.Serializable;
import java.util.List;

public class BatchResponse implements Serializable {
    private Batch batch;
    private List<QuizAverage> quizAverage;
    private List<CompetencyAverage> competencyAverage;

    public BatchResponse() {}

    public BatchResponse(Batch batch, List<QuizAverage> quizAverage, List<CompetencyAverage> competencyAverage) {
        this.batch = batch;
        this.quizAverage = quizAverage;
        this.competencyAverage = competencyAverage;
    }

}
