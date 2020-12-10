package com.numero.sojodia.ui.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.numero.sojodia.R
import com.numero.sojodia.extension.component
import com.numero.sojodia.repository.BusDataRepository
import com.numero.sojodia.repository.ConfigRepository
import com.numero.sojodia.ui.board.MainActivity
import com.numero.sojodia.ui.theme.SojoDiaTheme

class SplashActivity : AppCompatActivity() {

    private val configRepository: ConfigRepository
        get() = component.configRepository
    private val busDataRepository: BusDataRepository
        get() = component.busDataRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO support window inset
        setContent {
            SojoDiaTheme {
                SplashContent(
                    busDataRepository = busDataRepository,
                    configRepository = configRepository,
                    onSuccess = {
                        startActivity(MainActivity.createClearTopIntent(this))
                        finish()
                    }
                )
            }
        }
    }

    companion object {
        fun createIntent(context: Context): Intent = Intent(context, SplashActivity::class.java)
    }
}

@Composable
fun SplashContent(
    configRepository: ConfigRepository,
    busDataRepository: BusDataRepository,
    onSuccess: () -> Unit
) {
    var hasError by remember { mutableStateOf(false) }
    val presenter = SplashPresenterImpl(object : SplashView {
        override fun successDownloadedBusData() {
            onSuccess()
        }

        override fun onError(throwable: Throwable) {
            hasError = true
        }
    }, busDataRepository, configRepository)
    AmbientLifecycleOwner.current.lifecycle.addObserver(object : DefaultLifecycleObserver {
        override fun onResume(owner: LifecycleOwner) {
            super.onResume(owner)
            presenter.subscribe()
        }

        override fun onPause(owner: LifecycleOwner) {
            super.onPause(owner)
            presenter.unSubscribe()
        }
    })

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (hasError) {
            SplashErrorContent(
                modifier = Modifier.align(Alignment.Center),
                onRetry = {
                    presenter.subscribe()
                    hasError = false
                }
            )
        } else {
            SplashIndicatorContent(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun SplashIndicatorContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            imageVector = vectorResource(id = R.drawable.ic_launcher_foreground)
        )
        Spacer(modifier = Modifier.preferredHeight(64.dp))
        CircularProgressIndicator(
            modifier = Modifier
                .preferredSize(36.dp)
        )
    }
}

@Composable
fun SplashErrorContent(
    modifier: Modifier = Modifier,
    onRetry: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = AmbientContext.current
        Text(
            text = context.getString(R.string.error_load_bus_data_message),
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.subtitle1
        )
        Spacer(modifier = Modifier.preferredHeight(64.dp))
        Button(onClick = {
            onRetry()
        }) {
            Text(text = context.getString(R.string.error_retry_button))
        }
    }
}

@Preview("SplashIndicatorContent")
@Composable
fun SplashIndicatorContentPreview() {
    SojoDiaTheme {
        SplashIndicatorContent()
    }
}

@Preview("SplashErrorContent")
@Composable
fun SplashErrorContentPreview() {
    SojoDiaTheme {
        SplashErrorContent(onRetry = {})
    }
}