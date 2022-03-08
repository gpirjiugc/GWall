package com.gdev.gwall.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gdev.gwall.Activity.Image_Shower
import com.gdev.gwall.R
import com.google.android.flexbox.AlignSelf
import com.google.android.flexbox.FlexboxLayoutManager


class Adapter_rv1(var images: Array<String?>, var context: Context) : RecyclerView.Adapter<Adapter_rv1.abc>()
{



    inner class abc(itemView: View) : RecyclerView.ViewHolder(itemView){
              var imageView : ImageView = itemView.findViewById(R.id.image_viewi_1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): abc {
        val l = LayoutInflater.from(parent.context)
        val v: View = l.inflate(R.layout.instanceofimage1, parent, false)

        return abc(v)
    }

    override fun onBindViewHolder(holder: abc, position: Int) {
        Glide.with(context).load(images.get(position)).error(R.drawable.errorimage).into(holder.imageView)
        holder.imageView.setOnClickListener {
            val intent  = Intent(context, Image_Shower::class.java)


            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("key", images[position])
            context.startActivity(intent)






        }
    }
    override fun getItemCount(): Int {
      return images.size
    }
}