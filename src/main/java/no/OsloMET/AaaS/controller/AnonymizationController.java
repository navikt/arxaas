package no.OsloMET.AaaS.controller;

import no.OsloMET.AaaS.model.Note;
import no.OsloMET.AaaS.service.AnonymizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AnonymizationController {

    @Autowired
    AnonymizationService anonymizationService;

    @RequestMapping("/sihei/")
    public Note presetKAnonymization(@RequestBody Note payload){
        List<String> newParticipantList = new ArrayList<String>();
        payload.getParticipants().forEach( p->{
            newParticipantList.add("Hei " + p);
        });

        payload.setParticipants(newParticipantList);
        System.out.println("Triggered");
        return payload;
    }
}
