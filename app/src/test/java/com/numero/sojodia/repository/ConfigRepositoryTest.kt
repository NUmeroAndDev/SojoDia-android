package com.numero.sojodia.repository

import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ConfigRepositoryTest {
    private lateinit var configRepository: ConfigRepository

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getTargetContext()
        configRepository = ConfigRepository(context)
    }

    @Test
    fun `Hoge`() {
        assertEquals(configRepository.versionCode, 20170401L)
    }
}