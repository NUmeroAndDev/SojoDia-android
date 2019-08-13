package com.numero.sojodia.repository

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.numero.sojodia.model.CurrentVersion
import com.numero.sojodia.model.LatestVersion
import junit.framework.TestCase.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])  // FIXME とりあえずの対応
class ConfigRepositoryTest {

    private lateinit var configRepository: ConfigRepositoryImpl

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().context
        configRepository = ConfigRepositoryImpl(context)
    }

    @Test
    fun `currentVersion_デフォルトのバージョンコードが返って来ること`() {
        assertEquals(configRepository.currentVersion, CurrentVersion(ConfigRepositoryImpl.DEFAULT_DATA_VERSION))
    }

    @Test
    fun `updateBusDataVersion_保存されること`() {
        val versionCode = 20180101L
        configRepository.updateBusDataVersion(LatestVersion(versionCode))
        assertEquals(configRepository.currentVersion, CurrentVersion(versionCode))
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