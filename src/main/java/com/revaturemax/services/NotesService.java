package com.revaturemax.services;

import com.revaturemax.models.Notes;
import com.revaturemax.repositories.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotesService {

    @Autowired
    private NotesRepository notesRepository;

    public Notes setNotes(long employeeId, Notes notes) {
        validateNotes(notes);
        //long employeeId = pulled/passed from JWT
        //notes.setEmployee(new Employee(employeeId));
        if (notes.getNotes().equals("")) {
            notesRepository.delete(notes);
            return null;
        } else {
            notesRepository.save(notes);
            return notes;
        }
    }

    private void validateNotes(Notes notes) {
        //check topic set
    }

}
