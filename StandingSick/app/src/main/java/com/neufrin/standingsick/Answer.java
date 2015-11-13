package com.neufrin.standingsick;

/**
 * Created by neufrin on 13.11.2015.
 */
public class Answer {
    private Long Id;
    private Long QId;
    private String Content;

    public Answer()
    {

    }
    public Answer(String content, Long qId)
    {
        Content = content;
        QId = qId;
    }

    public Long getQId() {
        return QId;
    }

    public void setQId(Long QId) {
        this.QId = QId;
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
