package com.numero.sojodia.ui.settings

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.contentColor
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.VectorAsset
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.core.view.WindowCompat
import androidx.ui.tooling.preview.Preview
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.numero.sojodia.BuildConfig
import com.numero.sojodia.R
import com.numero.sojodia.databinding.ActivitySettingsBinding
import com.numero.sojodia.extension.applyApplication
import com.numero.sojodia.extension.getTitle
import com.numero.sojodia.extension.module
import com.numero.sojodia.model.AppTheme
import com.numero.sojodia.repository.ConfigRepository
import com.numero.sojodia.ui.theme.SojoDiaTheme
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding

class SettingsActivity : AppCompatActivity() {

    private val configRepository: ConfigRepository
        get() = module.configRepository

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var appUpdateManager: AppUpdateManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        appUpdateManager = AppUpdateManagerFactory.create(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupInset()
        setupViews()
    }

    override fun onResume() {
        super.onResume()
        checkHasUpdate()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun checkHasUpdate() {
        appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
            when (appUpdateInfo.updateAvailability()) {
                UpdateAvailability.UPDATE_AVAILABLE -> {
                    binding.appVersionSettingsItemView.apply {
                        setVisibleIcon(true)
                        setSummary(getString(R.string.settings_newer_version_available))
                        setOnClickListener {
                            appUpdateManager.startUpdateFlowForResult(
                                appUpdateInfo,
                                AppUpdateType.IMMEDIATE,
                                this@SettingsActivity,
                                UPDATE_REQUEST_CODE
                            )
                        }
                    }
                }
                UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS -> {
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        AppUpdateType.IMMEDIATE,
                        this,
                        UPDATE_REQUEST_CODE
                    )
                }
                else -> {
                    binding.appVersionSettingsItemView.apply {
                        setVisibleIcon(false)
                        setSummary(BuildConfig.VERSION_NAME)
                        setOnClickListener(null)
                    }
                }
            }
        }
    }

    private fun setupInset() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding.root.applySystemWindowInsetsToPadding(left = true, right = true)
        binding.scrollView.applySystemWindowInsetsToPadding(bottom = true)
    }

    private fun setupViews() {
        binding.selectThemeSettingsItemView.apply {
            val currentTheme = configRepository.appTheme
            setSummary(getString(currentTheme.textRes))
            setOnClickListener {
                showSelectThemeMenu(it.findViewById(R.id.title_text_view))
            }
        }

        binding.busDataSettingsItemView.setSummary(configRepository.currentVersion.value.toString())

        binding.appVersionSettingsItemView.apply {
            setVisibleIcon(false)
            setSummary(BuildConfig.VERSION_NAME)
        }

        binding.viewSourceSettingsItemView.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, SOURCE_URL.toUri()))
        }

        binding.licensesSettingsItemView.setOnClickListener {
            OssLicensesMenuActivity.setActivityTitle(getString(R.string.licenses_label))
            startActivity(Intent(this, OssLicensesMenuActivity::class.java))
        }
    }

    private fun showSelectThemeMenu(anchorView: View) {
        val popupMenu = PopupMenu(this, anchorView).apply {
            menuInflater.inflate(R.menu.menu_select_theme, menu)
            setOnMenuItemClickListener {
                val theme = when (it.itemId) {
                    R.id.theme_light -> AppTheme.LIGHT
                    R.id.theme_dark -> AppTheme.DARK
                    R.id.theme_system -> AppTheme.SYSTEM_DEFAULT
                    R.id.theme_auto_battery -> AppTheme.SYSTEM_DEFAULT
                    else -> throw Exception()
                }
                configRepository.appTheme = theme
                binding.selectThemeSettingsItemView.setSummary(getString(theme.textRes))
                theme.applyApplication()
                true
            }
        }
        popupMenu.show()
    }

    private val AppTheme.textRes: Int
        get() {
            return when (this) {
                AppTheme.LIGHT -> R.string.theme_light
                AppTheme.DARK -> R.string.theme_dark
                AppTheme.SYSTEM_DEFAULT -> if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    R.string.theme_auto_battery
                } else {
                    R.string.theme_system_default
                }
            }
        }

    companion object {
        private const val UPDATE_REQUEST_CODE = 1

        private const val SOURCE_URL = "https://github.com/NUmeroAndDev/SojoDia-android"

        fun createIntent(context: Context): Intent = Intent(context, SettingsActivity::class.java)
    }
}

@Composable
fun SettingsScreen(
    configRepository: ConfigRepository,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    val context = ContextAmbient.current
                    Text(text = context.getString(R.string.settings_label))
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack)
                    }
                }
            )
        },
        bodyContent = { innerPadding ->
            val modifier = Modifier.padding(innerPadding)
            SettingsContent(
                modifier = modifier,
                configRepository = configRepository
            )
        }
    )
}

@Composable
fun SettingsContent(
    modifier: Modifier = Modifier,
    configRepository: ConfigRepository
) {
    val context = ContextAmbient.current
    Column(
        modifier = modifier
    ) {
        SelectThemeItem(configRepository = configRepository)
        SettingsItem(
            title = context.getString(R.string.settings_data_version_title)
        )
        SettingsItem(
            icon = vectorResource(id = R.drawable.ic_attention),
            iconTint = MaterialTheme.colors.error,
            title = context.getString(R.string.settings_app_version_title),
            subtitle = BuildConfig.VERSION_NAME
        )
        SettingsItem(
            icon = vectorResource(id = R.drawable.ic_github),
            title = context.getString(R.string.settings_view_source_title)
        )
        SettingsItem(
            title = context.getString(R.string.settings_licenses_title)
        )
    }
}

@Composable
fun SelectThemeItem(
    configRepository: ConfigRepository
) {
    var isShownDropdown by remember { mutableStateOf(false) }
    var currentAppTheme by remember { mutableStateOf(configRepository.appTheme) }

    val context = ContextAmbient.current

    DropdownMenu(
        toggle = {
            SettingsItem(
                title = context.getString(R.string.settings_select_app_theme_title),
                subtitle = currentAppTheme.getTitle(context),
                onClick = {
                    isShownDropdown = true
                }
            )
        },
        expanded = isShownDropdown,
        onDismissRequest = {
            isShownDropdown = false
        },
        dropdownContent = {
            listOf(
                AppTheme.LIGHT, AppTheme.DARK, AppTheme.SYSTEM_DEFAULT
            ).forEach {
                DropdownMenuItem(
                    onClick = {
                        configRepository.appTheme = it
                        currentAppTheme = it
                        currentAppTheme.applyApplication()
                        isShownDropdown = false
                    }
                ) {
                    // TODO show icon if current selected
                    Text(text = it.getTitle(context))
                }
            }
        },
        dropdownModifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun SettingsItem(
    icon: VectorAsset? = null,
    iconTint: Color = contentColor(),
    title: String,
    subtitle: String? = null,
    onClick: () -> Unit = {}
) {
    val modifier = Modifier.preferredHeightIn(min = 64.dp)
        .clickable(onClick = onClick)
        .padding(horizontal = 16.dp)
    Row(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.preferredSize(24.dp)
                .align(alignment = Alignment.CenterVertically)
        ) {
            if (icon != null) {
                Icon(
                    asset = icon,
                    tint = iconTint,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        Spacer(modifier = Modifier.preferredWidth(16.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .align(alignment = Alignment.CenterVertically)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onBackground
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.54f)
                )
            }
        }
    }
}

@Preview("Settings Item")
@Composable
fun SettingsItemPreview() {
    SojoDiaTheme() {
        SettingsItem(
            icon = vectorResource(id = R.drawable.ic_github),
            title = "Title",
            subtitle = "Subtitle"
        )
    }
}