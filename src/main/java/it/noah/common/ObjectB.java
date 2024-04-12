/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.noah.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 *
 * @author NATCRI
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ObjectB implements Serializable {

    private static final long serialVersionUID = -2352051722101179746L;

    private Long id;
    private Long entityA;
    private String title;
    private boolean enabled;
    private Integer weight;
    private OffsetDateTime createtime;
    private OffsetDateTime dbtime;
    private Long optlock;
    private String state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEntityA() {
        return entityA;
    }

    public void setEntityA(Long entityA) {
        this.entityA = entityA;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    /*    public String getCreatetime() {
        return createtime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public void setCreatetime(String createtime) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        this.createtime = OffsetDateTime.parse(createtime, formatter);
    }

    public String getDbtime() {
        return dbtime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public void setDbtime(String dbtime) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        this.dbtime = OffsetDateTime.parse(dbtime, formatter);
    }*/
    public OffsetDateTime getCreatetime() {
        return createtime;
    }

    public void setCreatetime(OffsetDateTime createtime) {
        this.createtime = createtime;
    }

    public OffsetDateTime getDbtime() {
        return dbtime;
    }

    public void setDbtime(OffsetDateTime dbtime) {
        this.dbtime = dbtime;
    }

    public Long getOptlock() {
        return optlock;
    }

    public void setOptlock(Long optlock) {
        this.optlock = optlock;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}