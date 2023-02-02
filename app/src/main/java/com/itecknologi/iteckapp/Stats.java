package com.itecknologi.iteckapp;

import java.util.List;

public class Stats {
    List<Integer> PastSeven;
    List<Integer> Yesterday;


    public Stats(List<Integer> pastSeven, List<Integer> yesterday) {
        PastSeven = pastSeven;
        Yesterday = yesterday;
    }

    public List<Integer> getPastSeven() {
        return PastSeven;
    }

    public void setPastSeven(List<Integer> pastSeven) {
        PastSeven = pastSeven;
    }

    public List<Integer> getYesterday() {
        return Yesterday;
    }

    public void setYesterday(List<Integer> yesterday) {
        Yesterday = yesterday;
    }
}
