package no.OsloMET.AaaS.model;

import java.util.Date;
import java.util.List;

public class Note {
    private Date date;
    private String title;
    private String message;
    private List participants;

    public List getParticipants() {
        return participants;
    }

    public void setParticipants(List participants) {
        this.participants = participants;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
