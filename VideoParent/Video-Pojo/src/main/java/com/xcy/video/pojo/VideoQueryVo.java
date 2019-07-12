package com.xcy.video.pojo;

import java.util.List;

/*
    pojo,entry,javabean,domain  实体   传递数据之用，否则有何用？
 */
public class VideoQueryVo {


    private String title;
    private int speakerId;
    private int courseId;

    private int page = 1;    //当前页数
    private int rows = 5;    // 每页显示多少条
    private int begin = 0;  //limit 0,5

    private List<Integer> idList;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public List<Integer> getIdList() {
        return idList;
    }

    public void setIdList(List<Integer> idList) {
        this.idList = idList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSpeakerId() {
        return speakerId;
    }

    public void setSpeakerId(int speakerId) {
        this.speakerId = speakerId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
