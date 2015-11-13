package com.neufrin.standingsick;

/**
 * Created by neufrin on 13.11.2015.
 */
public class Question {

    private Long Id;
    private String Content;

    public Question()
    {

    }
    public Question(String content)
    {
        Content = content;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
