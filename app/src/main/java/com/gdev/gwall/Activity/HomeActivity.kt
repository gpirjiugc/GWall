package com.gdev.gwall

import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import android.widget.LinearLayout
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.gdev.gwall.Activity.ActiityLoader_Images
import com.gdev.gwall.Adapters.Adapter_rv
import com.gdev.gwall.Adapters.Adapter_rv1
import com.gdev.gwall.Adapters.Adapter_rv2
import com.pixplicity.easyprefs.library.Prefs
import me.ibrahimsn.lib.SmoothBottomBar
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import pl.droidsonroids.gif.GifImageView
import java.io.File


class MainActivity : AppCompatActivity() {

    lateinit var image_top : Array<String?>
    lateinit var ImageLike: ImageView
    lateinit var cat_color : Array<String?>
    lateinit var recyclerView : RecyclerView
    lateinit var recyclerView2 : RecyclerView
    lateinit var recyclerView3 : RecyclerView
    lateinit var text_like_images : TextView
    lateinit var adapterRv : Adapter_rv
    lateinit var  frame2: FrameLayout
    lateinit var bottombar : SmoothBottomBar
    lateinit  var frame1 : FrameLayout
    lateinit var imageview_search : ImageView
    lateinit  var linearLayout : LinearLayout
    lateinit var  editText_serach : EditText
    lateinit var imageView_back : ImageView
    lateinit var imageview_d1: ImageView
    lateinit var image_cat_text : Array<String?>

    lateinit var cardex1 : CardView
    lateinit var cardex2 : CardView
    lateinit var cardex3 : CardView
    lateinit var cardex4 : CardView


    lateinit var imageview_d2: ImageView
    lateinit var imageview_d3: ImageView
    lateinit var imageview_d4: ImageView
    lateinit var aa : AlertDialog
    var anim: Animation = AlphaAnimation(0.1f, 1.0f)
    var i = 0
    var data = ""







    override fun onBackPressed() {
        if(i == 0){
           super.onBackPressed()
        }
        else {
            i = 0
            frame1.visibility = View.VISIBLE
            bottombar.visibility = View.VISIBLE
            frame2.visibility = View.VISIBLE
            linearLayout.visibility = View.INVISIBLE
            recyclerView2.visibility = View.INVISIBLE
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(editText_serach.windowToken, 0)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Methods
         getupdate()
         createfolderfordownload()
         warming_up()


         add_top_images()
         add_cat_and_data()
         add_color_data()

         Handler().postDelayed(Runnable { aa.dismiss() }, 5000)





        imageview_d1.setOnClickListener(View.OnClickListener {
            var intent = Intent(this, ActiityLoader_Images::class.java)
            intent.putExtra("key1", image_cat_text[0].toString())
            intent.putExtra("key2", "category")
            startActivity(intent)
        })
        imageview_d2.setOnClickListener(View.OnClickListener {
            var intent = Intent(this, ActiityLoader_Images::class.java)
            intent.putExtra("key1", image_cat_text[1].toString())
            intent.putExtra("key2", "category")
            startActivity(intent)

        })
        imageview_d3.setOnClickListener(View.OnClickListener {
            var intent = Intent(this, ActiityLoader_Images::class.java)
            intent.putExtra("key1", image_cat_text[2].toString())
            intent.putExtra("key2", "category")
            startActivity(intent)

        })
        imageview_d4.setOnClickListener(View.OnClickListener {
            var intent = Intent(this, ActiityLoader_Images::class.java)
            intent.putExtra("key1", image_cat_text[3].toString())
            intent.putExtra("key2", "category")
            startActivity(intent)

        })

        //Interface Calling on Object
        ImageLike.setOnClickListener({
            recyclerView2.adapter = null
            bottombar.itemActiveIndex = 2
            text_like_images.visibility = View.VISIBLE
            frame1.visibility = View.INVISIBLE
            frame2.visibility = View.INVISIBLE
            linearLayout.visibility = View.INVISIBLE
            recyclerView2.visibility = View.VISIBLE
            i = 2
            getlikedimages()
        })
        imageview_search.setOnClickListener(View.OnClickListener {
            recyclerView2.adapter = null
            bottombar.itemActiveIndex = 1
            frame1.visibility = View.INVISIBLE
            frame2.visibility = View.INVISIBLE
            i = 2
            linearLayout.visibility = View.VISIBLE
            editText_serach.animation = anim
            editText_serach.requestFocus()
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText_serach, InputMethodManager.SHOW_IMPLICIT)
            recyclerView2.visibility = View.VISIBLE

        })
        imageView_back.setOnClickListener(View.OnClickListener {
            bottombar.itemActiveIndex = 0
            bottombar.visibility = View.VISIBLE
            i = 0
            text_like_images.visibility = View.INVISIBLE
            frame1.visibility = View.VISIBLE
            frame2.visibility = View.VISIBLE
            linearLayout.visibility = View.INVISIBLE
            recyclerView2.visibility = View.INVISIBLE

            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(editText_serach.windowToken, 0)
        })
        bottombar.setOnItemSelectedListener {
            if(it == 0 ){
                i = 0
                frame1.visibility = View.VISIBLE
                bottombar.visibility = View.VISIBLE
                frame2.visibility = View.VISIBLE
                linearLayout.visibility = View.INVISIBLE
                text_like_images.visibility = View.INVISIBLE
                recyclerView2.visibility = View.INVISIBLE
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(editText_serach.windowToken, 0)

            }
            if(it == 1 ){
                recyclerView2.adapter = null
                frame1.visibility = View.INVISIBLE
                frame2.visibility = View.INVISIBLE
                  i = 2
                text_like_images.visibility = View.INVISIBLE

                linearLayout.visibility = View.VISIBLE

                editText_serach.requestFocus()
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(editText_serach, InputMethodManager.SHOW_IMPLICIT)
                recyclerView2.visibility = View.VISIBLE
                recyclerView2.adapter = null
            }
            if(it == 2 ){
                text_like_images.setText("Liked Wallpapers")
                text_like_images.visibility = View.VISIBLE
                bottombar.itemActiveIndex = 2
                frame1.visibility = View.INVISIBLE
                frame2.visibility = View.INVISIBLE
                linearLayout.visibility = View.INVISIBLE
                recyclerView2.adapter = null
                recyclerView2.visibility = View.VISIBLE
                getlikedimages()

                i = 2

            }
            if(it == 3){
                frame1.visibility = View.INVISIBLE
                frame2.visibility = View.INVISIBLE
                linearLayout.visibility = View.INVISIBLE
                text_like_images.visibility = View.VISIBLE
                text_like_images.setText("Downloads Wallpapers")
                recyclerView2.adapter = null
                recyclerView2.visibility = View.VISIBLE
               getimagesfromfolder()
            }

        }
        editText_serach.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                searchdata(s.toString())
                data = s.toString()


            }

            override fun afterTextChanged(s: Editable) {

            }
        })
        editText_serach.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                recyclerView2.adapter = null

                searchdata(editText_serach.text)
                return@OnEditorActionListener true
            }
            false
        })


                                                                                         }

    private fun getimagesfromfolder() {
        val root = Environment.getExternalStorageDirectory().toString()  + "/" + "g_wall_downloader"
        val directory = File(root)
        val files = directory.listFiles()
       image_top =  arrayOfNulls(files.size)
        for (i in files.indices) {
            image_top[i] = files[i].absolutePath


        }
        val adapterRv: Adapter_rv1 = Adapter_rv1(image_top, applicationContext)

        recyclerView2.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView2.adapter = adapterRv

    }

    private fun createfolderfordownload()
    {
         val ch = File(
                 Environment.getExternalStorageDirectory()
                         .toString() + File.separator + "/" + "g_wall_downloader"
         )
        if (ch.exists()) {
            Toast.makeText(this, "Welcome Back", Toast.LENGTH_SHORT).show() }
        else { val   file = File(
                Environment.getExternalStorageDirectory()
                        .toString() + File.separator + "/" + "g_wall_downloader"
        )
            file.mkdirs() }
            }

    // Get Liked Images
    private fun getlikedimages() {
        if( Prefs.getString("like", "").equals("")||Prefs.getString("like", "") == null){
          Toast.makeText(this, "No Images You Liked ", Toast.LENGTH_LONG).show()
        }
        else {
          val str = Prefs.getString("like", "")
            val stringimages : Array<String?> =   str.split("[,]".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            val adapterRv: Adapter_rv1 = Adapter_rv1(stringimages, applicationContext)
            recyclerView2.layoutManager = GridLayoutManager(applicationContext, 2)
            recyclerView2.adapter = adapterRv   }
    }


    //Get Custom Updates  From  PHP Server
    private fun getupdate() {
        val resque : RequestQueue = Volley.newRequestQueue(this)
        val request1 = JsonObjectRequest(
                "https://stethoscopic-instan.000webhostapp.com/check_update.php",
                {
                    if (it.getString("key_api").equals("no_update")) {
                        Toast.makeText(this, "Welcome ,Hello", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "New Update is Available ", Toast.LENGTH_LONG).show()
                        val url = "http://www.example.com"
                        val i = Intent(Intent.ACTION_VIEW)
                        i.data = Uri.parse(it.getString("key_api"))
                        startActivity(i)

                    }
                },
                com.android.volley.Response.ErrorListener {
                    Toast.makeText(this, "Some Error Occurs Restart The Application or Check Your Internet Speed ,Team Gwall", Toast.LENGTH_LONG).show()
                })
        resque.add(request1)
    }

      //warming Declare variable and Some Initializing  Feature
    private fun warming_up() {
         imageview_d1  = findViewById(R.id.image_view_d_1)
         imageview_d2  = findViewById(R.id.image_view_d_2)
         imageview_d3  = findViewById(R.id.image_view_d_3)
         imageview_d4  = findViewById(R.id.image_view_d_4)
          cardex1 = findViewById(R.id.card_ex1)
          cardex2 = findViewById(R.id.card_ex2)
          cardex3 = findViewById(R.id.card_ex3)
          cardex4 = findViewById(R.id.card_ex4)

          var displaymatrix = DisplayMetrics()
          windowManager.defaultDisplay.getMetrics(displaymatrix)
          var width = displaymatrix.widthPixels / 2 - 20 - 40

          var params = cardex1.layoutParams
          params.height = 210
          params.width = width
          cardex1.requestLayout()

          params = cardex2.layoutParams
          params.height = 210
          params.width = width
          cardex2.requestLayout()


          params = cardex3.layoutParams
          params.height = 210
          params.width = width
          cardex3.requestLayout()

          params = cardex4.layoutParams
          params.height = 210
          params.width = width
          cardex4.requestLayout()








        imageview_search  = findViewById(R.id.image_serach)
        editText_serach  = findViewById(R.id.et_search)
        ImageLike = findViewById(R.id.image_like)
        text_like_images = findViewById(R.id.liked_w_text)
        imageView_back = findViewById(R.id.image_back)
        frame1  = findViewById(R.id.frame1)
        recyclerView  = findViewById(R.id.rv_top_images)
        recyclerView2  = findViewById(R.id.rv2)
        recyclerView3  = findViewById(R.id.rv3)

        frame2 = findViewById(R.id.frame2)
        bottombar = findViewById(R.id.bottomBar)
        linearLayout = findViewById(R.id.linearlayout_up)

        //Full Screen
        window.decorView.apply { systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION }
        // inside your activity (if you did not enable transitions in your theme)
        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.colorPrimaryDark1)
        }
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimaryDark1));
        }

        anim.duration = 1000
        //For Showing Loading
        val bb = AlertDialog.Builder(this@MainActivity)
        val ch: View = layoutInflater.inflate(R.layout.loadingbar, null)
        bb.setView(ch)
        aa = bb.create()
        aa.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        aa.show()


        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build();


    }

    //Add Color From Server
    private fun add_color_data() {





        val requestQueue = Volley.newRequestQueue(this)

        val url = "https://stethoscopic-instan.000webhostapp.com/gwall_color_cat.php"
        val request = StringRequest(Request.Method.GET, url, { response ->
            try {
                var jsonArray: JSONArray = JSONArray(response)
                cat_color = arrayOfNulls<String>(jsonArray.length())


                for (i in 0 until jsonArray.length()) {
                    cat_color[i] = jsonArray.getJSONObject(i).getString("color")
                }

                val adapterRv2 = Adapter_rv2(cat_color, applicationContext)
                val mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
                mLayoutManager.reverseLayout = true
                mLayoutManager.stackFromEnd = true

                recyclerView3.setLayoutManager(mLayoutManager)
                recyclerView3.adapter = adapterRv2

            } catch (e: JSONException) {
                Toast.makeText(this, "Some Error Occurs Restart The Application or Check Your Internet Speed ,Team Gwall", Toast.LENGTH_LONG).show()
            }
        }, { error -> Toast.makeText(this, "Some Error Occurs Restart The Application or Check Your Internet Speed ,Team Gwall", Toast.LENGTH_LONG).show() })




        requestQueue.add(request)




    }



    //Search Data
    fun searchdata(text: CharSequence)
    {

        var viewGIf : GifImageView = findViewById(R.id.GifImageView)
        viewGIf.visibility = View.VISIBLE
   var images : Array<String?>
   var ff = "true"
   val url =  "https://pixabay.com/api/?key=22480270-96aa292bee392f8394661b22e&q=$text&image_type=photo&safesearch=$ff"

   val requestQueue : RequestQueue = Volley.newRequestQueue(this)
        val request = StringRequest(Request.Method.GET, url, { response ->
            try {
                val jsonObject: JSONObject = JSONObject(response)
                val JsonArray: JSONArray = jsonObject.getJSONArray("hits")
                images = arrayOfNulls<String>(JsonArray.length())

                for (i in 0 until JsonArray.length()) {
                    images[i] = JsonArray.getJSONObject(i).getString("largeImageURL")

                }

                val view: View = layoutInflater.inflate(R.layout.instanceofimage, null)
                val linearLayout: LinearLayout = view.findViewById(R.id.linearlayout_instance)
                linearLayout.layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                )
                val adapterRv = Adapter_rv1(images, applicationContext)
                recyclerView2.layoutManager = GridLayoutManager(applicationContext, 2)
                recyclerView2.adapter = adapterRv
                viewGIf.visibility = View.INVISIBLE

            } catch (e: JSONException) {
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            }

        }, { error ->
            Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()

        })
        requestQueue.add(request)

    }

    // Add Top Images
    private fun add_top_images() {
        //Adding_top_images
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.cache.clear()
        val url = "https://stethoscopic-instan.000webhostapp.com/gwall_data.php"
        val request = StringRequest(Request.Method.GET, url, { response ->
            try {
                var jsonArray: JSONArray = JSONArray(response)
                image_top = arrayOfNulls<String>(jsonArray.length())

                for (i in 0 until jsonArray.length()) {
                    image_top[i] = jsonArray.getJSONObject(i).getString("top_images")
                }

                adapterRv = Adapter_rv(image_top, applicationContext)
                val mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
                mLayoutManager.reverseLayout = true
                mLayoutManager.stackFromEnd = true

                recyclerView.setLayoutManager(mLayoutManager)
                recyclerView.adapter = adapterRv

            } catch (e: JSONException) {
                Toast.makeText(this, "Some Error Occurs Restart The Application or Check Your Internet Speed ,Team Gwall", Toast.LENGTH_LONG).show()
            }
        }, { error -> Toast.makeText(this, "Some Error Occurs Restart The Application or Check Your Internet Speed ,Team Gwall", Toast.LENGTH_LONG).show() })

        requestQueue.add(request)

    }

    //Add Category and Category Images
    private fun add_cat_and_data() {


        var image_cat : Array<String?>



        //Sending Request
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.cache.clear()
        val url = "https://stethoscopic-instan.000webhostapp.com/gwall_cat_images.php"
        val request = StringRequest(Request.Method.GET, url, { response ->
            try {
                var jsonArray: JSONArray = JSONArray(response)
                image_cat = arrayOfNulls<String>(jsonArray.length())

                for (i in 0 until jsonArray.length()) {
                    image_cat[i] = jsonArray.getJSONObject(i).getString("image_cat")

                }





                Glide.with(this)
                        .load(image_cat[0])
                        .error(R.drawable.errorimage)
                        .into(imageview_d1)

                Glide.with(this)
                        .load(image_cat[1])
                        .error(R.drawable.errorimage)
                        .into(imageview_d2)

                Glide.with(this)
                        .load(image_cat[2])
                        .error(R.drawable.errorimage)
                        .into(imageview_d3)

                Glide.with(this)
                        .load(image_cat[3])
                        .error(R.drawable.errorimage)
                        .into(imageview_d4)


            } catch (e: JSONException) {
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            }
        }, { error -> Toast.makeText(this, "Some Error Occurs Restart The Application or Check Your Internet Speed ,Team Gwall", Toast.LENGTH_LONG).show() })
        requestQueue.add(request)


        //Sending Request
        val requestQueue1 = Volley.newRequestQueue(this)
        requestQueue1.cache.clear()
        val url1 = "https://stethoscopic-instan.000webhostapp.com/gwall_cat_text.php"
        val request1 = StringRequest(Request.Method.GET, url1, { response ->
            try {
                var jsonArray: JSONArray = JSONArray(response)
                image_cat_text = arrayOfNulls<String>(jsonArray.length())

                for (i in 0 until jsonArray.length()) {
                    image_cat_text[i] = jsonArray.getJSONObject(i).getString("image_cat_text")

                }

                val text_cat_1: TextView = findViewById(R.id.text_cat_1)
                val text_cat_2: TextView = findViewById(R.id.text_cat_2)
                val text_cat_3: TextView = findViewById(R.id.text_cat_3)
                val text_cat_4: TextView = findViewById(R.id.text_cat_4)



                text_cat_1.setText(image_cat_text[0])
                text_cat_2.setText(image_cat_text[1])
                text_cat_3.setText(image_cat_text[2])
                text_cat_4.setText(image_cat_text[3])


            } catch (e: JSONException) {
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            }
        }, { error -> Toast.makeText(this, error.message, Toast.LENGTH_LONG).show() })
        requestQueue1.add(request1)



    }


}