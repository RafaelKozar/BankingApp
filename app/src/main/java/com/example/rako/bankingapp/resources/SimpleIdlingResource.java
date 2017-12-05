package com.example.rako.bankingapp.resources;

import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by rako on 01/12/2017.
 */

public class SimpleIdlingResource implements IdlingResource {

    @Nullable private volatile ResourceCallback mCallback;
    private AtomicBoolean atomicBoolean = new AtomicBoolean(true);

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return atomicBoolean.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.mCallback = callback;
    }

    public void setIdlState(boolean isIdlNow) {
        atomicBoolean.set(isIdlNow);
        if (isIdlNow && mCallback != null) {
            mCallback.onTransitionToIdle();
        }
    }
}
