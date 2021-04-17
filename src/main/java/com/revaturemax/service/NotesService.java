package com.revaturemax.service;

import com.revaturemax.model.Notes;
import com.revaturemax.repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotesService {

    @Autowired
    private NotesRepository notesRepository;

    public void setNotes(long batchId, Notes notes) {
        validateNotes(notes);
        //long employeeId = pulled/passed from JWT
        //notes.setEmployee(new Employee(employeeId));
        if (notes.getNotes().equals("")) {
            notesRepository.delete(notes);
        } else {
            notesRepository.save(notes);
        }

    }

    private void validateNotes(Notes notes) {
        //check topic set
    }

}
