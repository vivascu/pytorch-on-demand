package ch.vivascu.dynamic.nativefeature

import android.os.Bundle
import ch.vivascu.dynamic.dynamicnativelib.BaseSplitActivity
import com.google.android.play.core.splitinstall.SplitInstallHelper
import org.pytorch.Module

class NativeActivity : BaseSplitActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val newContext = this.createPackageContext(this.packageName, 0)
        SplitInstallHelper.loadLibrary(newContext, "libc++_shared")
        SplitInstallHelper.loadLibrary(newContext, "libfbjni")
        SplitInstallHelper.loadLibrary(newContext, "libpytorch_jni_lite")
        setContentView(R.layout.activity_native)
        Module.load("unexistingModel.pt")
    }
}