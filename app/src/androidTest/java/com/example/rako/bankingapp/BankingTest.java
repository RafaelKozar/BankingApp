package com.example.rako.bankingapp;

import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.rako.bankingapp.activitys.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by rako on 01/12/2017.
 */

@RunWith(AndroidJUnit4.class)
public class BankingTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class);

    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
    }

    @Test
    public void idlingResourceTest() {
    }

    @After
    public void unregisterIdlinResource() {
    }
}
