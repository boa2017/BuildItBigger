package com.udacity.gradle.builditbigger;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)

public class JokesAsyncTaskTest {

    @Rule
    public final ActivityTestRule<MainActivity> mTestRule
            = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testAsyncTaskLoadsJoke() throws Exception {

        // Assign
        JokesAsyncTask testTask = new JokesAsyncTask(mTestRule.getActivity());

        // Action
        testTask.execute();
        String joke = testTask.get();

        // Assert
        assertNotNull(joke);
        assertFalse(joke.isEmpty());
    }
}