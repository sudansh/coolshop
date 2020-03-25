package com.sudansh.coolshop

import com.sudansh.coolshop.data.network.ApiService
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.core.IsNull.notNullValue
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class ApiServiceTest {
    private lateinit var service: ApiService

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun `test login`() = runBlocking {
        enqueueResponse("login.json")
        val response = service.login("sudhanshu@gmail.com", "password")

        assertThat(response, notNullValue())
        assertThat(response.userId, `is`(100))
        assertThat(response.token, `is`("1234567"))
    }

    @Test
    fun `test user detail`() = runBlocking {
        enqueueResponse("user_details.json")
        val response = service.userDetail(100)

        assertThat(response, notNullValue())
        assertThat(response.email, `is`("sudhanshu@gmail.com"))
        assertThat(response.avatarUrl, `is`("http://www.gmail.com"))
    }

    @Test
    fun `test avatar upload`() = runBlocking {
        enqueueResponse("upload_avatar.json")
        val loginResponse = service.uploadAvatar(100, "base64 data")

        assertThat(loginResponse, notNullValue())
        assertThat(loginResponse.avatarUrl, `is`("http://www.gmail.com"))
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader!!
            .getResourceAsStream("api-response/$fileName")
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse
                .setBody(source.readString(Charsets.UTF_8))
        )
    }
}