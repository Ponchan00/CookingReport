package com.example.cookingreport

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.Realm
import io.realm.Realm.init
import io.realm.RealmRecyclerViewAdapter
import io.realm.kotlin.where
import android.widget.ImageView
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.provider.AlarmClock.EXTRA_MESSAGE
import androidx.core.content.ContextCompat.startActivity


class ReadingAdapter (context: Context, data: OrderedRealmCollection<WritingRealm>):
    RealmRecyclerViewAdapter<WritingRealm, ReadingAdapter.ViewHolder>(data, true) {
    init {
        setHasStableIds(true)
    }
    private lateinit var realm : Realm
    private var context:Context = context

    class ViewHolder(cell: View): RecyclerView.ViewHolder(cell){

        val uri1: ImageView? = cell.findViewById(R.id.textview)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val View = inflater.inflate(R.layout.item_component,parent,false)
        val holder = ViewHolder(View)
        realm = Realm.getDefaultInstance()

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val writing:WritingRealm? = data!![position]

        var pfDescriptor: ParcelFileDescriptor? = null
        var pic_uri:Uri? = null
        pic_uri =Uri.parse(writing?.uri)

        pfDescriptor = context.contentResolver.openFileDescriptor(pic_uri!!, "r")
        if (pfDescriptor != null) {
            val fileDescriptor = pfDescriptor.fileDescriptor
            val bmp = BitmapFactory.decodeFileDescriptor(fileDescriptor)
            pfDescriptor.close()
            holder.uri1!!.setImageBitmap(bmp)
        }

        try {
            pfDescriptor?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

  holder.itemView.setOnClickListener{
            val position = holder.adapterPosition

            val intent = Intent(context, ArticleActivity::class.java)
            intent.putExtra("position", position)
            intent.putExtra("article", data?.get(position)?.article)
            intent.putExtra("date", data?.get(position)?.date)
            intent.putExtra("uri", data?.get(position)?.uri)
            intent.putExtra("id", data?.get(position)?.id)
            intent.putExtra("title", data?.get(position)?.title)
            context.startActivity(intent)

        }

    }

}