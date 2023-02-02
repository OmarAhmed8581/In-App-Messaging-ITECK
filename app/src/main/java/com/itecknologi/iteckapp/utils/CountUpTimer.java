package com.itecknologi.iteckapp.utils;

import android.os.CountDownTimer;

public abstract class CountUpTimer extends CountDownTimer {

    private static final long INTERVAL_MS = 1000;
    private final long duration;


    protected CountUpTimer(long durationInMillis) {
        super(durationInMillis, INTERVAL_MS);
        this.duration = durationInMillis;
    }

    public abstract void onTick(int second);

    @Override
    public void onTick(long msUntilFinished) {
        int second = (int) ((duration - msUntilFinished) / 1000);
        onTick(second);
    }

    @Override
    public void onFinish() {
        onTick(duration / 1000);
    }
}
