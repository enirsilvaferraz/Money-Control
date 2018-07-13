package com.system.moneycontrol.ui.transactionmanager

import android.support.test.espresso.Espresso
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.system.moneycontrol.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TransactionManagerActivityTest {

    @Rule
    var mActivityRule: ActivityTestRule<TransactionManagerActivity> = ActivityTestRule(TransactionManagerActivity::class.java)

    @Test
    fun init() {

        Espresso.onView(ViewMatchers.withId(R.id.paymentType))
                .check(ViewAssertions.matches(ViewMatchers.withText(R.string.system_empty_field)))

        Espresso.onView(ViewMatchers.withId(R.id.purchaseDate))
                .check(ViewAssertions.matches(ViewMatchers.withText(R.string.system_empty_field)))

        Espresso.onView(ViewMatchers.withId(R.id.paymentDate))
                .check(ViewAssertions.matches(ViewMatchers.withText(R.string.system_empty_field)))

        Espresso.onView(ViewMatchers.withId(R.id.price))
                .check(ViewAssertions.matches(ViewMatchers.withText(R.string.system_empty_field)))

        Espresso.onView(ViewMatchers.withId(R.id.refund))
                .check(ViewAssertions.matches(ViewMatchers.withText(R.string.system_empty_field)))

        Espresso.onView(ViewMatchers.withId(R.id.content))
                .check(ViewAssertions.matches(ViewMatchers.withText(R.string.system_empty_field)))
    }
}