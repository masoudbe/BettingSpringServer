package com.vote.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false, updatable = false)
    private Long id;

    @Column
    private String voter;
    @Column
    private Date createDate;
    @Column
    private String selection;
    @Column
    private Boolean isDeleted;

    public Vote() {
    }

    public Vote(String voter, Date createDate, String selection) {
        this.voter = voter;
        this.createDate = createDate;
        this.selection = selection;
    }

    public String getVoter() {
        return voter;
    }

    public void setVoter(String voter) {
        this.voter = voter;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getSelection() {
        return selection;
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
