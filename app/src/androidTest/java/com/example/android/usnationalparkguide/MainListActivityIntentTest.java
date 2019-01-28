package com.example.android.usnationalparkguide;


import android.app.Activity;
import android.app.Instrumentation;

import com.sjani.usnationalparkguide.R;
import com.sjani.usnationalparkguide.UI.MainList.MainListActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
public class MainListActivityIntentTest {

    @Rule
    public IntentsTestRule<MainListActivity> mActivityRule = new IntentsTestRule<>(
            MainListActivity.class);

    @Before
    public void stubAllExternalIntents() {
        // By default Espresso Intents does not stub any Intents. Stubbing needs to be setup before
        // every test run. In this case all external Intents will be blocked.
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }

    @Test
    public void clickOnRecyclerViewItem_runsRecipeDetailsActivityIntent() {

        onView(withId(R.id.rv_main)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

//        intended(
//                hasExtra("position", 0)
//        );
    }
}
