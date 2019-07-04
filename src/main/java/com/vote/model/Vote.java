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
    private String ip;
    @Column
    private Date createDate;
    @Column
    private String name;
    @Column
    private Boolean isDeleted;

    public Vote() {
    }

    public Vote(String ip, Date createDate, String name) {
        this.ip = ip;
        this.createDate = createDate;
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
