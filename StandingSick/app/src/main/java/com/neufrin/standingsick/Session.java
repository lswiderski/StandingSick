package com.neufrin.standingsick;

import java.util.Date;

/**
 * Created by neufrin on 13.11.2015.
 */
public class Session {
    private Long Id;
    private Date Date;

    public  Session()
    {

    }
    public Session(Date date)
    {
        Date = date;
    }
    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }
}
