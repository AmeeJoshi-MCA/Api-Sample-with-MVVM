package com.chainels.application.view

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import com.chainels.application.model.TimeLine
import org.junit.After
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class DetailActivityUITestCase {

    private lateinit var scenario: ActivityScenario<DetailActivity>

    @After
    fun cleanup() {
        scenario.close()
    }

    @Test
    fun testDetailActivity() {
        // Your test code goes here.

        val intent = Intent(ApplicationProvider.getApplicationContext(), DetailActivity::class.java)
        .putExtra("msgId", 141618920126771574)
        scenario = launchActivity(intent)

    }

}