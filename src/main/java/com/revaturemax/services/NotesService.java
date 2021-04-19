package com.revaturemax.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revaturemax.models.Notes;
import com.revaturemax.repositories.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class NotesService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NotesRepository notesRepository;

    public ResponseEntity<String> setNotes(long employeeId, Notes notes) {
        validateNotes(notes);
        //long employeeId = pulled/passed from JWT
        //notes.setEmployee(new Employee(employeeId));
        if (notes.getNotes().equals("")) {
            notesRepository.delete(notes);
            return new ResponseEntity<String>(HttpStatus.OK);
        } else {
            notes = notesRepository.save(notes);
            return new ResponseEntity<String>(notes.getId().toString(), HttpStatus.OK);
        }
    }

    private void validateNotes(Notes notes) {
        //check topic set
    }

    public void deleteNotes(long id) { notesRepository.deleteById(id);
    }

   public Notes updateNotes(Long id, Notes newNotes){
       Notes updateN = notesRepository.getOne(id);
       updateN.setNotes(newNotes.getNotes());
//       updateN.setTopic(newNotes.getTopic());
       notesRepository.save(updateN);
       return updateN;
    }

}
