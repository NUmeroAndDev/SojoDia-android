package com.numero.sojodia.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ConfigRepositoryTest {
    private lateinit var configRepository: ConfigRepository

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().context
        configRepository = ConfigRepository(context)
    }

    @Test
    fun `Hoge`() {
        assertEquals(configRepository.versionCode, 20170401L)
    }
}