package ch.vivascu.dynamic.nativefeature

import android.os.Bundle
import ch.vivascu.dynamic.dynamicnativelib.BaseSplitActivity
import com.google.android.play.core.splitinstall.SplitInstallHelper
import org.pytorch.LiteModuleLoader

class NativeActivity : BaseSplitActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SplitInstallHelper.loadLibrary(this, "c++_shared")
        SplitInstallHelper.loadLibrary(this, "fbjni")
        SplitInstallHelper.loadLibrary(this, "pytorch_jni_lite")
        setContentView(R.layout.activity_native)

        LiteModuleLoader.load("model.pt")

    }
}