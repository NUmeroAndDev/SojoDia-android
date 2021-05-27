package com.numero.sojodia.ui.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.activity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
import com.numero.sojodia.ui.Screen
import com.numero.sojodia.ui.theme.SojoDiaTheme

class SettingsActivity : AppCompatActivity() {

    private val configRepository: ConfigRepository
        get() = component.configRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appUpdateManager = AppUpdateManagerFactory.create(this)

        setContent {
            CompositionLocalProvider(
                LocalAppUpdateManager provides appUpdateManager
            ) {
                val navController = rememberNavController()
                SojoDiaTheme {
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Settings.name
                    ) {
                        composable(
                            route = Screen.Settings.route
                        ) {
                            SettingsScreen(
                                configRepository = configRepository,
                                navController = navController,
                                onBack = {
                                    onBackPressed()
                                },
                                onUpdate = {
                                    appUpdateManager.startUpdateFlowForResult(
                                        it,
                                        AppUpdateType.IMMEDIATE,
                                        this@SettingsActivity,
                                        UPDATE_REQUEST_CODE
                                    )
                                }
                            )
                        }
                        activity(
                            id = Screen.License.routeId
                        ) {
                            activityClass = OssLicensesMenuActivity::class
                        }
                    }
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

val LocalAppUpdateManager = staticCompositionLocalOf<AppUpdateManager> {
    error("CompositionLocal AppUpdateManager not present")
}

@Composable
fun SettingsScreen(
    configRepository: ConfigRepository,
    navController: NavController,
    onBack: () -> Unit,
    onUpdate: (AppUpdateInfo) -> Unit
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = context.getString(R.string.settings_label))
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                    }
                },
                backgroundColor = MaterialTheme.colors.surface
            )
        },
        content = { innerPadding ->
            val modifier = Modifier.padding(innerPadding)
            SettingsContent(
                modifier = modifier,
                configRepository = configRepository,
                onUpdate = onUpdate,
                onClickLicenses = {
                    OssLicensesMenuActivity.setActivityTitle(context.getString(R.string.licenses_label))
                    navController.navigate(Screen.License.routeId)
                }
            )
        }
    )
}

@Composable
fun SettingsContent(
    modifier: Modifier = Modifier,
    configRepository: ConfigRepository,
    onUpdate: (AppUpdateInfo) -> Unit,
    onClickLicenses: () -> Unit
) {
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current
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
            icon = painterResource(id = R.drawable.ic_github),
            title = context.getString(R.string.settings_view_source_title),
            onClick = {
                uriHandler.openUri(SettingsActivity.SOURCE_URL)
            }
        )
        SettingsItem(
            title = context.getString(R.string.settings_licenses_title),
            onClick = onClickLicenses
        )
    }
}

@Composable
fun SelectThemeItem(
    configRepository: ConfigRepository
) {
    var isShownDropdown by remember { mutableStateOf(false) }
    var currentAppTheme by remember { mutableStateOf(configRepository.appTheme) }

    val context = LocalContext.current
    Box {
        SettingsItem(
            title = context.getString(R.string.settings_select_app_theme_title),
            subtitle = currentAppTheme.getTitle(context),
            onClick = {
                isShownDropdown = true
            }
        )
        DropdownMenu(
            expanded = isShownDropdown,
            onDismissRequest = {
                isShownDropdown = false
            },
            content = {
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
            }
        )
    }
}

@Composable
fun AppVersionItem(
    onUpdate: (AppUpdateInfo) -> Unit
) {
    val context = LocalContext.current
    val appUpdateManager = LocalAppUpdateManager.current
    var versionState by remember { mutableStateOf<VersionState>(VersionState.NoUpdate) }
    LocalLifecycleOwner.current.lifecycle.addObserver(object : DefaultLifecycleObserver {
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
                painterResource(id = R.drawable.ic_attention)
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
    icon: Painter? = null,
    iconTint: Color = LocalContentColor.current,
    title: String,
    subtitle: String? = null,
    onClick: () -> Unit = {}
) {
    val modifier = Modifier
        .heightIn(min = 64.dp)
        .clickable(onClick = onClick)
        .padding(horizontal = 16.dp)
    Row(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .align(alignment = Alignment.CenterVertically)
        ) {
            if (icon != null) {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        Spacer(modifier = Modifier.width(32.dp))
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
            icon = painterResource(id = R.drawable.ic_github),
            title = "Title",
            subtitle = "Subtitle"
        )
    }
}