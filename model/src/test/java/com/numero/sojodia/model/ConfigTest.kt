package com.numero.sojodia.model

import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

class ConfigTest {

    private lateinit var config: Config

    @Before
    fun setUp() {
        config = Config(LatestVersion(1))
    }

    @Test
    fun `checkUpdate_新しいバージョンがある時trueが返って来ること`() {
        val currentVersion = CurrentVersion(0)
        assertTrue(config.checkUpdate(currentVersion))
    }
}