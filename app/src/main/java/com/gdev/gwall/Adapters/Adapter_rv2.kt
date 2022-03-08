package com.gdev.gwall.Adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gdev.gwall.Activity.ActiityLoader_Images
import com.gdev.gwall.Activity.Image_Shower
import com.gdev.gwall.R


class Adapter_rv2(var colorss: Array<String?>, var context: Context) : RecyclerView.Adapter<Adapter_rv2.abc>()
{



    inner class abc(itemView: View) : RecyclerView.ViewHolder(itemView){
              var cardview : CardView = itemView.findViewById(R.id.card_1_color)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): abc {
        val l = LayoutInflater.from(parent.context)
        val v: View = l.inflate(R.layout.instanceofcolor, parent, false)
        return abc(v)
    }

    override fun onBindViewHolder(holder: abc, position: Int) {
        var str = "#"
         val c : Int = Color.parseColor(str +colorss[position].toString())
        holder.cardview.background.setTint(c)
        holder.cardview.setOnClickListener{
            var intent: Intent = Intent(context, ActiityLoader_Images::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            intent.putExtra("key1",str +colorss[position].toString())
            intent.putExtra("key2","color")


           when(position){
               0 ->  intent.putExtra("key3","Brown")
               1 -> intent.putExtra("key3","Black")
               2 -> intent.putExtra("key3","Pink")
               3 -> intent.putExtra("key3","Green")
               4 ->intent.putExtra("key3","Purple")
               5 ->intent.putExtra("key3","Red")
               6 ->intent.putExtra("key3","Green")
               7 ->intent.putExtra("key3","Blue")
               8 ->intent.putExtra("key3","Orange")
           }


            context.startActivity(intent)
        }


    }
    override fun getItemCount(): Int {
      return colorss.size
    }
}