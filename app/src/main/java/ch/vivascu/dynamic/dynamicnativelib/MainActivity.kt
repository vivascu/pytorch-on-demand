package ch.vivascu.dynamic.dynamicnativelib

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import kotlin.math.roundToInt

class MainActivity : BaseSplitActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val splitInstallManager = SplitInstallManagerFactory.create(this)

        val button = findViewById<Button>(R.id.button)
        var mySessionId = -1

        val intent = Intent()
        intent.setClassName(
            "ch.vivascu.dynamic.dynamicnativelib",
            "ch.vivascu.dynamic.nativefeature.NativeActivity"
        )

        button.setOnClickListener {

            if (splitInstallManager.installedModules.contains("nativefeature")) {
                startActivity(intent)
            } else {
                val request =
                    SplitInstallRequest
                        .newBuilder()
                        .addModule("nativefeature")
                        .build()

                splitInstallManager
                    .startInstall(request)
                    .addOnSuccessListener { sessionId -> mySessionId = sessionId }
                    .addOnFailureListener { exception ->
                        exception.toString()
                    }
            }
        }


        val listener = SplitInstallStateUpdatedListener { state ->
            if (state.sessionId() == mySessionId) {
                // Read the status of the request to handle the state update.

                var textState = when (state.status()) {

                    SplitInstallSessionStatus.CANCELED -> "Canceled"
                    SplitInstallSessionStatus.CANCELING -> "Canceling"
                    SplitInstallSessionStatus.DOWNLOADED -> "Downloaded"
                    SplitInstallSessionStatus.DOWNLOADING -> "Downloading"
                    SplitInstallSessionStatus.FAILED -> "Failed"
                    SplitInstallSessionStatus.INSTALLED -> "Installed"
                    SplitInstallSessionStatus.INSTALLING -> "Installing"
                    SplitInstallSessionStatus.PENDING -> "Pending"
                    SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> "Requires user confirmation"
                    else -> "Unknown Status"
                }


                if (state.status() == SplitInstallSessionStatus.DOWNLOADING) {
                    val totalBytes = state.totalBytesToDownload()
                    val progress = state.bytesDownloaded()
                    val percent = progress * 100f / totalBytes
                    textState += " ${(percent * 100).roundToInt() / 100}%"
                }

                button.text = textState

                if (state.status() == SplitInstallSessionStatus.INSTALLED) {
                    button.text = "Open the native feature"
                }

            }
        }

        splitInstallManager.registerListener(listener)
    }


}