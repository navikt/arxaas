package no.OsloMET.AaaS.service;

import no.OsloMET.AaaS.model.Note;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AnonymizationService {

    public Note sihei(){
        System.out.println("Hei");

        Note note = new Note();
        note.setDate(new Date(System.currentTimeMillis()));
        note.setTitle("Hei dette er et spring kurs");
        note.setMessage("Velkommen til spring");

        List<String> part = new ArrayList<String>();
        part.add("Jeremiah");
        part.add("Sondre");

        note.setParticipants(part);

        return note;
    }
}
