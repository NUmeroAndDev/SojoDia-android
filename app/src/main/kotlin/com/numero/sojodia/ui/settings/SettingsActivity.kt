package com.numero.sojodia.ui.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LifecycleOwnerAmbient
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.ui.tooling.preview.Preview
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.numero.sojodia.BuildConfig
import com.numero.sojodia.R
import com.numero.sojodia.extension.applyApplication
import com.numero.sojodia.extension.component
import com.numero.sojodia.extension.getTitle
import com.numero.sojodia.model.AppTheme
import com.numero.sojodia.repository.ConfigRepository
import com.numero.sojodia.ui.theme.SojoDiaTheme

class SettingsActivity : AppCompatActivity() {

    private val configRepository: ConfigRepository
        get() = component.configRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appUpdateManager = AppUpdateManagerFactory.create(this)

        setContent {
            Providers(
                AppUpdateManagerAmbient provides appUpdateManager
            ) {
                SojoDiaTheme {
                    SettingsScreen(
                        configRepository = configRepository,
                        onBack = {
                            onBackPressed()
                        },
                        onUpdate = {
                            appUpdateManager.startUpdateFlowForResult(
                                it,
                                AppUpdateType.IMMEDIATE,
                                this,
                                UPDATE_REQUEST_CODE
                            )
                        }
                    )
                }
            }
        }
    }

    companion object {
        private const val UPDATE_REQUEST_CODE = 1

        const val SOURCE_URL = "https://github.com/NUmeroAndDev/SojoDia-android"

        fun createIntent(context: Context): Intent = Intent(context, SettingsActivity::class.java)
    }
}

val AppUpdateManagerAmbient = staticAmbientOf<AppUpdateManager>()

@Composable
fun SettingsScreen(
    configRepository: ConfigRepository,
    onBack: () -> Unit,
    onUpdate: (AppUpdateInfo) -> Unit
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
                },
                backgroundColor = MaterialTheme.colors.surface
            )
        },
        bodyContent = { innerPadding ->
            val modifier = Modifier.padding(innerPadding)
            SettingsContent(
                modifier = modifier,
                configRepository = configRepository,
                onUpdate = onUpdate
            )
        }
    )
}

@Composable
fun SettingsContent(
    modifier: Modifier = Modifier,
    configRepository: ConfigRepository,
    onUpdate: (AppUpdateInfo) -> Unit
) {
    val context = ContextAmbient.current
    Column(
        modifier = modifier
    ) {
        SelectThemeItem(configRepository = configRepository)
        SettingsItem(
            title = context.getString(R.string.settings_data_version_title),
            subtitle = configRepository.currentVersion.value.toString()
        )
        AppVersionItem(
            onUpdate = onUpdate
        )
        SettingsItem(
            icon = vectorResource(id = R.drawable.ic_github),
            title = context.getString(R.string.settings_view_source_title),
            onClick = {
                context.startActivity(
                    Intent(Intent.ACTION_VIEW, SettingsActivity.SOURCE_URL.toUri())
                )
            }
        )
        SettingsItem(
            title = context.getString(R.string.settings_licenses_title),
            onClick = {
                OssLicensesMenuActivity.setActivityTitle(context.getString(R.string.licenses_label))
                context.startActivity(Intent(context, OssLicensesMenuActivity::class.java))
            }
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
fun AppVersionItem(
    onUpdate: (AppUpdateInfo) -> Unit
) {
    val context = ContextAmbient.current
    val appUpdateManager = AppUpdateManagerAmbient.current
    var versionState by remember { mutableStateOf<VersionState>(VersionState.NoUpdate) }
    LifecycleOwnerAmbient.current.lifecycle.addObserver(object : DefaultLifecycleObserver {
        override fun onResume(owner: LifecycleOwner) {
            super.onResume(owner)
            appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
                when (appUpdateInfo.updateAvailability()) {
                    UpdateAvailability.UPDATE_AVAILABLE -> {
                        versionState = VersionState.AvailableUpdate(appUpdateInfo)
                    }
                    else -> versionState = VersionState.NoUpdate
                }
            }
        }
    })
    SettingsItem(
        icon = when (versionState) {
            is VersionState.AvailableUpdate -> {
                vectorResource(id = R.drawable.ic_attention)
            }
            is VersionState.NoUpdate -> null
        },
        iconTint = MaterialTheme.colors.error,
        title = context.getString(
            when (versionState) {
                is VersionState.AvailableUpdate -> R.string.settings_newer_version_available
                is VersionState.NoUpdate -> R.string.settings_app_version_title
            }
        ),
        subtitle = BuildConfig.VERSION_NAME,
        onClick = {
            val state = versionState
            if (state is VersionState.AvailableUpdate) {
                onUpdate(state.appUpdateInfo)
            }
        }
    )
}

private sealed class VersionState {
    data class AvailableUpdate(
        val appUpdateInfo: AppUpdateInfo
    ) : VersionState()

    object NoUpdate : VersionState()
}

@Composable
fun SettingsItem(
    icon: VectorAsset? = null,
    iconTint: Color = AmbientContentColor.current,
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
        Spacer(modifier = Modifier.preferredWidth(32.dp))
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