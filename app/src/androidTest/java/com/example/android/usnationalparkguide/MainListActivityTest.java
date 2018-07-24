package com.example.android.usnationalparkguide;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import android.support.test.espresso.contrib.RecyclerViewActions;


import com.sjani.usnationalparkguide.R;
import com.sjani.usnationalparkguide.UI.MainList.MainListActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainListActivityTest {

    private IdlingResource idlingResource;

    @Rule
    public ActivityTestRule<MainListActivity> activityTestRule =  new ActivityTestRule<>(MainListActivity.class);

    @Before
    public void registerIdlingResource() {
        idlingResource = activityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(idlingResource);
    }

    @After
    public void unregisterIdlingResource() {
        if (idlingResource != null) {
            Espresso.unregisterIdlingResources(idlingResource);
        }
    }

    @Test
    public void clickListItemToOpenActivity (){
        onView(withId(R.id.rv_main)).perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));
        onView(withId(R.id.detail_photo)).check(matches(isDisplayed()));
    }

}
