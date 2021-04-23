package com.revaturemax.dto;

import com.revaturemax.models.Employee;
import com.revaturemax.models.Topic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TopicResponse {

    private Topic topic;
    private Float competency;
    private Long starredNotesId;
    private List<NotesDetails> notes = new ArrayList<>();

    public TopicResponse(Topic topic, Float competency) {
        this.topic = topic;
        this.competency = competency;
    }

    public void addNotesDetails(Employee employee, long notesId, int timesStarred, String content) {
        this.notes.add(new NotesDetails(employee, notesId, timesStarred, content));
    }

    public void sortNotes() {
        notes.sort(Comparator.comparing(NotesDetails::getTimesStarred).reversed());
    }

    public Topic getTopic() {
        return topic;
    }

    public Float getCompetency() {
        return competency;
    }

    public Long getStarredNotesId() {
        return starredNotesId;
    }

    public void setStarredNotesId(Long starredNotesId) {
        this.starredNotesId = starredNotesId;
    }

    public List<NotesDetails> getNotes() {
        return notes;
    }

    private class NotesDetails {

        private Employee employee;
        private long notesId;
        private int timesStarred;
        private String content;

        public NotesDetails(Employee employee, long notesId, int timesStarred, String content) {
            this.employee = employee;
            this.notesId = notesId;
            this.timesStarred = timesStarred;
            this.content = content;
        }

        public Employee getEmployee() {
            return employee;
        }

        public long getNotesId() {
            return notesId;
        }

        public int getTimesStarred() {
            return timesStarred;
        }

        public String getContent() {
            return content;
        }

    }

}
