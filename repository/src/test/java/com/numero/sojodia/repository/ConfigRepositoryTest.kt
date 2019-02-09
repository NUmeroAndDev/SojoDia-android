package com.numero.sojodia.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.TestCase.*
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
    fun `versionCode_デフォルトのバージョンコードが返って来ること`() {
        assertEquals(configRepository.versionCode, ConfigRepository.DEFAULT_DATA_VERSION)
    }

    @Test
    fun `versionCode_保存されること`() {
        val versionCode = 20180101L
        configRepository.versionCode = versionCode
        assertEquals(configRepository.versionCode, versionCode)
    }

    @Test
    fun `isTodayUpdateChecked_初めての更新チェックでfalseが返ってくること`() {
        assertFalse(configRepository.isTodayUpdateChecked)
    }

    @Test
    fun `isTodayUpdateChecked_日付を更新するとtrueが返ってくること`() {
        configRepository.updateCheckUpdateDate()
        assertTrue(configRepository.isTodayUpdateChecked)
    }

}