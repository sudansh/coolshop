package com.sudansh.coolshop.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sudansh.coolshop.R
import com.sudansh.coolshop.ui.login.LoginActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations


@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    @get:Rule
    val rule = IntentsTestRule(LoginActivity::class.java, true, true)

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `testLoginWithValidCredentials`() {
        onView(withId(R.id.et_username)).perform(replaceText("sudhanshu@gmail.com"))
        onView(withId(R.id.et_password)).perform(replaceText("password"))
        onView(withId(R.id.btn_login)).check(matches(isEnabled()))
    }

    @Test
    fun `testLoginWithInValidCredentials`() {
        onView(withId(R.id.et_username)).perform(replaceText("sudhanshu@gmail.com"))
        onView(withId(R.id.et_password)).perform(replaceText("pord"))
        onView(withId(R.id.btn_login)).perform(click())
        onView(withId(R.id.password)).check(matches(hasTextInputLayoutErrorText("Password must be >5 characters")))
    }

}