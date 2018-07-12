package com.system.moneycontrol.ui.transactionmanager

import android.support.test.espresso.Espresso
import android.support.test.espresso.ViewAssertion
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.system.moneycontrol.R

@RunWith(AndroidJUnit4::class)
class TransactionManagerActivityTest {

    @Rule
    var mActivityRule: ActivityTestRule<TransactionManagerActivity> = ActivityTestRule(TransactionManagerActivity::class.java)

    @Test
    fun init() {
        Espresso.onView(ViewMatchers.withId(R.id.paymentTypeContainer)).perform(ViewActions.click())
    }
}