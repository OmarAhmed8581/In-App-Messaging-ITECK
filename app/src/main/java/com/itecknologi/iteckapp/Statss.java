package com.itecknologi.iteckapp;

public class Statss {
    Stats Stats;
    boolean Success;

    public Statss(Stats stats, boolean success) {
        Stats = stats;
        Success = success;
    }

    public Stats getStats() {
        return Stats;
    }

    public void setStats(Stats stats) {
        Stats = stats;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }
}
