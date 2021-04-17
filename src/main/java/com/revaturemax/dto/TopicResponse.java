package com.revaturemax.dto;

import com.revaturemax.models.Employee;
import com.revaturemax.models.Topic;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
@Scope("prototype")
public class TopicResponse {

    private Topic topic;
    private Float competency;
    private List<NotesDetails> notes = new ArrayList<>();

    public TopicResponse(Topic topic, Float competency) {
        this.topic = topic;
        this.competency = competency;
    }

    public void addNotesDetails(Employee employee, boolean starred, int timesStarred, String content) {
        this.notes.add(new NotesDetails(employee, starred, timesStarred, content));
    }

    /**
     * Sorts the notes by timesStarred descending. If a notes entry is starred, it is placed at the front.
     */
    public void sortNotes() {
        NotesDetails starred = null;
        for (NotesDetails nd : notes) {
            if (nd.starred) {
                starred = nd;
                break;
            }
        }
        if (starred != null) {
            notes.remove(starred);
            notes.sort(Comparator.comparing(NotesDetails::getTimesStarred).reversed());
            notes.add(0, starred);
        } else {
            notes.sort(Comparator.comparing(NotesDetails::getTimesStarred).reversed());
        }
    }

    public Topic getTopic() {
        return topic;
    }

    public Float getCompetency() {
        return competency;
    }

    public List<NotesDetails> getNotes() {
        return notes;
    }

    private class NotesDetails {

        private Employee employee;
        private boolean starred;
        private int timesStarred;
        private String content;

        public NotesDetails(Employee employee, boolean starred, int timesStarred, String content) {
            this.employee = employee;
            this.starred = starred;
            this.timesStarred = timesStarred;
            this.content = content;
        }

        public Employee getEmployee() {
            return employee;
        }

        public boolean isStarred() {
            return starred;
        }

        public int getTimesStarred() {
            return timesStarred;
        }

        public String getContent() {
            return content;
        }

    }

}
