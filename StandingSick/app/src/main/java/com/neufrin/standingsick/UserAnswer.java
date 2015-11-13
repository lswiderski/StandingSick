package com.neufrin.standingsick;

/**
 * Created by neufrin on 13.11.2015.
 */
public class UserAnswer {
    private Long Id;
    private Long Session;
    private Long AId;
    private Long QId;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Long getSession() {
        return Session;
    }

    public void setSession(Long session) {
        Session = session;
    }

    public Long getAId() {
        return AId;
    }

    public void setAId(Long AId) {
        this.AId = AId;
    }

    public Long getQId() {
        return QId;
    }

    public void setQId(Long QId) {
        this.QId = QId;
    }
}
