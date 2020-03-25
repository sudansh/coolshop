package com.sudansh.coolshop.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sudansh.coolshop.R
import com.sudansh.coolshop.ui.detail.MainActivity
import com.sudansh.coolshop.ui.detail.MainViewModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.test.KoinTest
import org.koin.test.mock.declare
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class MainActivityTest : KoinTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rule = IntentsTestRule(MainActivity::class.java, true, true)

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun loadResults() {
        declare {
            viewModel(override = true) {
                mockk<MainViewModel>(relaxUnitFun = true) {
                    every { avatarUrl } returns MutableLiveData<String>().apply {
                        value = "www.gmail.com"
                    }
                    every { email } returns "sudhanshu@gmail.com"
                    every { password } returns "password"
                }
            }
        }

        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.email)).check(matches(withText("sudhanshu@gmail.com")))
        onView(withId(R.id.password)).check(matches(withText("password")))
    }

}