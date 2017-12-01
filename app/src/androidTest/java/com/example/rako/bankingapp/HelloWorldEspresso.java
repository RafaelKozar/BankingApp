package com.example.rako.bankingapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.rako.bankingapp.activitys.MainActivity;

import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * Created by rako on 30/11/2017.
 */

@RunWith(AndroidJUnit4.class)
public class HelloWorldEspresso {
    @Rule
    ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);
}
