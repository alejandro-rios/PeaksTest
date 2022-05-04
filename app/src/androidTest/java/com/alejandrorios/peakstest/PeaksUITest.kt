package com.alejandrorios.peakstest

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alejandrorios.peakstest.presentation.MainActivity
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PeaksUITest {

    @Test
    fun validateContentIsLoaded() {
        ActivityScenario.launch(MainActivity::class.java)

        Espresso.onView(withId(R.id.clContent)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
