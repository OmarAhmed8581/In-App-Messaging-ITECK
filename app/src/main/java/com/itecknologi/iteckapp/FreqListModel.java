package com.itecknologi.iteckapp;

public class FreqListModel {
    private String start;
    private String end;
    private String totalTime;

    public FreqListModel(String start, String end, String totalTime) {
        this.start = start;
        this.end = end;
        this.totalTime = totalTime;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }


}
