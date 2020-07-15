package com.example.valeriarocac196;

public class Term {
    private long termId;
    private String title;
    private String startDate;
    private String endDate;
    private int current;

    public Term() {
        this.title = "";
        this.startDate = "";
        this.endDate = "";
        this.current = 0;
    }

    public Term(long termId, String title, String startDate, String endDate, int current) {
        this.termId = termId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.current = current;
    }

    public long getTermId() {
        return termId;
    }

    public void setTermId(long termId) {
        this.termId = termId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

}

