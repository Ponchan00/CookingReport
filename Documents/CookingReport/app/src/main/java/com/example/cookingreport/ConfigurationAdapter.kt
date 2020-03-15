package com.example.cookingreport

import android.app.Application
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.res.Configuration
import android.provider.CalendarContract
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import io.realm.Realm
import io.realm.RealmObject.deleteFromRealm
import io.realm.kotlin.createObject
import io.realm.kotlin.where

class ConfigurationAdapter(context:Context,data:OrderedRealmCollection<TitleRealm>):RealmRecyclerViewAdapter<TitleRealm, ConfigurationAdapter.ViewHolder>(data, true) {
    init {
        setHasStableIds(true)
    }
    private lateinit var realm : Realm
    private var context:Context = context

    class ViewHolder(cell: View):RecyclerView.ViewHolder(cell){
        val title:TextView? = cell.findViewById(R.id.textview2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val View = inflater.inflate(R.layout.title_item_component,parent,false)
        val holder = ViewHolder(View)
        realm = Realm.getDefaultInstance()
        holder.itemView.setOnClickListener{
            val position = holder.adapterPosition
            //println(data?.get(position)?.title)
            val dialog = AlertDialog.Builder(context)
                    dialog.setTitle("このカテゴリーを削除しますか？")
                    dialog.setPositiveButton("OK", {_, _ ->
                        //OKボタンを押したときの処理
                        realm.executeTransaction{
                            it.where<TitleRealm>().equalTo("title",data?.get(position)?.title).findFirst()?.deleteFromRealm()
                        }
                    })
                    dialog.setNegativeButton("キャンセル", null)
                    dialog.show()

        }

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val title:TitleRealm? = getItem(position)
        holder.title?.text = title?.title
    }

}