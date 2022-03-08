package com.gdev.gwall.Activity

import android.app.VoiceInteractor
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.TextureView
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.gdev.gwall.Adapters.Adapter_rv1
import com.gdev.gwall.R
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import org.json.JSONArray
import org.json.JSONObject
import pl.droidsonroids.gif.GifImageView

class ActiityLoader_Images : AppCompatActivity() {
    lateinit var cardView_color : CardView
    lateinit var textview_cat: TextView
    lateinit var images : Array<String?>
    lateinit var cat : String
    lateinit var copycol : String
    lateinit var  recyclerView : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actiity_loader__images)
         recyclerView = findViewById(R.id.rv_loader)
        recyclerView.adapter = null
        cardView_color = findViewById(R.id.card_color_shower)
       textview_cat = findViewById(R.id.cat_type_shower)
        val intent : Intent = getIntent()
         cat = intent.extras?.get("key1") as String



        val type : String = intent.extras?.get("key2") as String

        if(type=="color"){
            cardView_color.visibility = View.VISIBLE
            cardView_color.background.setTint(Color.parseColor(cat))
            copycol = intent.extras?.get("key3") as String

            loadimages("https://pixabay.com/api/?key=22480270-96aa292bee392f8394661b22e&q=$copycol&image_type=photo")
        }
        else {
            cardView_color.visibility = View.INVISIBLE
           textview_cat.setText(cat)
            loadimages("https://pixabay.com/api/?key=22480270-96aa292bee392f8394661b22e&q=$cat&image_type=photo")

        }



    }

    private fun loadimages(url : String) {
        val gifimage : GifImageView = findViewById(R.id.GifImageView1)
        gifimage.visibility = View.VISIBLE
        var requestQueue : RequestQueue = Volley.newRequestQueue(this)
        var stringRequest : StringRequest = StringRequest(Request.Method.GET,url,{
            val jsonObject: JSONObject = JSONObject(it)
            val JsonArray: JSONArray = jsonObject.getJSONArray("hits")
            images = arrayOfNulls<String>(JsonArray.length())

            for (i in 0 until JsonArray.length()) {
                images[i] = JsonArray.getJSONObject(i).getString("largeImageURL")

            }
            val adapterRv = Adapter_rv1(images, applicationContext)

             recyclerView.layoutManager  =GridLayoutManager(applicationContext, 2)
            recyclerView.adapter = adapterRv
           gifimage.visibility = View.INVISIBLE
        },
            {

            })
        requestQueue.add(stringRequest)

    }
}