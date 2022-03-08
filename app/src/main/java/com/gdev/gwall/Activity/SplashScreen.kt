package com.gdev.gwall.Activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gdev.gwall.MainActivity
import com.gdev.gwall.R
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.util.jar.Manifest


class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window // in Activity's onCreate() for instance
            w.setFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        var anim: Animation = AlphaAnimation(0.1f, 1.0f)
        anim.duration = 1000
        var imageView : ImageView = findViewById(R.id.image_splash)

        imageView.animation = anim
        getruntimeper()


    }

    private fun getruntimeper() {
        Dexter.withContext(this)
                .withPermissions(
                       android.Manifest.permission.READ_EXTERNAL_STORAGE,
                       android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        if(report.areAllPermissionsGranted()){
                          Handler().postDelayed(Runnable {  var intent : Intent = Intent(applicationContext,MainActivity::class.java)
                              startActivity(intent)
                              finish()  },5000)
                        }
                        else {
                            Toast.makeText(applicationContext,"Pls Give Permission ",Toast.LENGTH_LONG).show()
                                    startActivity(Intent(this@SplashScreen,SplashScreen::class.java))
                                     finish()

                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(permissions: List<PermissionRequest>, token: PermissionToken) { /* ... */
                        token.continuePermissionRequest()
                    }
                }).check()
    }
}