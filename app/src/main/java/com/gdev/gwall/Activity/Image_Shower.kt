package com.gdev.gwall.Activity

import android.app.WallpaperManager
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.gdev.gwall.R
import com.pixplicity.easyprefs.library.Prefs
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


class Image_Shower : AppCompatActivity() {
    lateinit var bitmap: Bitmap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image__shower)


        var image: ImageView = findViewById(R.id.image_add_to_like)
        var image_set_as_wall : ImageView = findViewById(R.id.setaswalll)
        val image_download : ImageView = findViewById(R.id.image_download)




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window // in Activity's onCreate() for instance
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build();

        val intent :Intent = getIntent()
        val string : String = intent.extras?.get("key") as String
        getimage()


        image_download.setOnClickListener(View.OnClickListener {


            val dNow = Date()
            val ft = SimpleDateFormat("yyMMddhhmmssMs")
            val datetime: String = ft.format(dNow)
            val fname = datetime + ".jpeg"

            val root = Environment.getExternalStorageDirectory().toString()  + "/" + "g_wall_downloader"
            val myDir = File(root,fname)


            try {
                val out = FileOutputStream(myDir)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
                Toast.makeText(applicationContext,"Image Saved ",Toast.LENGTH_LONG).show()
                out.flush()
                out.close()
            } catch (e: java.lang.Exception) {
               Toast.makeText(this,e.message,Toast.LENGTH_LONG).show()
            }})
        image_set_as_wall.setOnClickListener(View.OnClickListener {
            val linearLayout: LinearLayout = findViewById(R.id.linear_back_set_as)
            linearLayout.visibility = View.VISIBLE
            var anim: Animation = AlphaAnimation(0.1f, 1.0f)
            anim.duration = 1000
            linearLayout.animation = anim


            var image_set_as_lock: ImageView = findViewById(R.id.set_as_lock)
            var image_set_as_home: ImageView = findViewById(R.id.set_as_home)

            image_set_as_home.setOnClickListener(View.OnClickListener {


                var wallpaperManager: WallpaperManager = WallpaperManager.getInstance(
                    applicationContext
                );

                Glide.with(this)
                    .asBitmap()
                    .load(string)
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            wallpaperManager.setBitmap(resource)

                        }

                        override fun onLoadCleared(placeholder: Drawable?) {

                        }
                    })
            })
            image_set_as_lock.setOnClickListener(View.OnClickListener {
                Glide.with(this)
                    .asBitmap()
                    .load(string)
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {

                            var wm = WallpaperManager.getInstance(applicationContext)
                            try {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    wm.setBitmap(
                                        resource,
                                        null,
                                        true,
                                        WallpaperManager.FLAG_LOCK
                                    );//For Lock screen
                                    Toast.makeText(
                                        applicationContext,
                                        "done",
                                        Toast.LENGTH_SHORT
                                    ).show();
                                } else {
                                    Toast.makeText(
                                        applicationContext,
                                        "Lock screen walpaper not supported",
                                        Toast.LENGTH_SHORT
                                    ).show();
                                }
                            } catch (e: Exception) {
                                Toast.makeText(
                                    applicationContext,
                                    e.message,
                                    Toast.LENGTH_SHORT
                                ).show();
                            }
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {

                        }
                    })


            })


        })
        image.setOnClickListener(View.OnClickListener {
            if (Prefs.getString("like", "").equals("") || Prefs.getString("like", "") == null) {
                Prefs.putString("like", string)
                Toast.makeText(this, "Added To Like ", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Added To Like ", Toast.LENGTH_LONG).show()
                Prefs.putString("like", Prefs.getString("like", "") + "," + string)
            }
        })


    }

    private fun getimage() {
        val imageView : ImageView = findViewById(R.id.image_shower_image)
        val intent :Intent = getIntent()
        val str : String = intent.extras?.get("key") as String
        Glide.with(this).load(str).into(imageView)
        Glide.with(this)
            .asBitmap()
            .load(str)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    bitmap = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    // this is called when imageView is cleared on lifecycle call or for
                    // some other reason.
                    // if you are referencing the bitmap somewhere else too other than this imageView
                    // clear it here as you can no longer have the bitmap
                }
            })
    }
}