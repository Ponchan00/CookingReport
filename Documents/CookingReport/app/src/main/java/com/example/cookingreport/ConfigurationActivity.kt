package com.example.cookingreport

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_configuration.*
import android.view.*
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import io.realm.Realm
import io.realm.kotlin.createObject


class ConfigurationActivity : AppCompatActivity() {

    private lateinit var realm:Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuration)

        realm=Realm.getDefaultInstance()
        val titles = realm.where<TitleRealm>(TitleRealm::class.java).findAll()
        //println("here")
        //println(titles)
        val adapter=ConfigurationAdapter(this,titles)
        val itemDecoration = DividerItemDecoration(this,DividerItemDecoration.VERTICAL) //区切り線
        list.addItemDecoration(itemDecoration) //区切り線
        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.configurationmenu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.add -> {
                val myedit = EditText(this)
                val dialog = AlertDialog.Builder(this)
                dialog.setTitle("追加するカテゴリーを入力してください")
                dialog.setView(myedit)
                dialog.setPositiveButton("OK", {_, _ ->
                    //OKボタンを押したときの処理
                    val userText = myedit.getText().toString()
                    realm.executeTransaction{
                        val title = it.createObject<TitleRealm>(userText)
                    }
                })
                dialog.setNegativeButton("キャンセル", null)
                dialog.show()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }


}
