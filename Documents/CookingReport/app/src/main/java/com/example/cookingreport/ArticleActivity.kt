package com.example.cookingreport

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.view.Menu
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_article.*
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_article.articleText
import kotlinx.android.synthetic.main.activity_article.dateText
import kotlinx.android.synthetic.main.activity_reading.*
import kotlinx.android.synthetic.main.activity_writing.*
import java.util.*

class ArticleActivity : AppCompatActivity() {

    private lateinit var realm: Realm
    private var ThisArticle:String? = ""
    private var ThisDate:String? = ""
    private var ThisUri:String? = ""
    private var ThisId:String? = ""
    private var ThisTitle:String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)
        realm = Realm.getDefaultInstance()

//        getSupportActionBar()?.setDisplayShowHomeEnabled(true);
//        getSupportActionBar()?.setIcon(R.id.menu_delete);

        edit.setOnClickListener { onEditButtonTapped(it) }

        val intent = getIntent()
        val thisPosition = intent.extras?.getString("position")
        val thisArticle = intent.extras?.getString("article")
        val thisDate = intent.extras?.getString("date")
        val thisUri = intent.extras?.getString("uri")
        val thisId = intent.extras?.getString("id")
        ThisTitle = intent.extras?.getString("title")
        articleText.text = thisArticle
        dateText.text = thisDate

        ThisArticle = thisArticle
        ThisDate = thisDate
        ThisUri = thisUri
        ThisId = thisId

        var pfDescriptor: ParcelFileDescriptor? = null
        var pic_uri: Uri? = null
        pic_uri = Uri.parse(thisUri)

        pfDescriptor = contentResolver.openFileDescriptor(pic_uri!!, "r")
        if (pfDescriptor != null) {
            val fileDescriptor = pfDescriptor.fileDescriptor
            val bmp = BitmapFactory.decodeFileDescriptor(fileDescriptor)
            pfDescriptor.close()
            imageView.setImageBitmap(bmp)
        }

        try {
            pfDescriptor?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        deleteButton.setOnClickListener {

            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("この記事を削除しますか？")
            dialog.setPositiveButton("OK", { _, _ ->
                //OKボタンを押したときの処理
                realm.executeTransaction {
                    realm.where<WritingRealm>().equalTo("id", thisId).findFirst()?.deleteFromRealm()
                }

                finish()
            })
            dialog.setNegativeButton("キャンセル", null)

            dialog.show()

            realm.executeTransaction {
                val afterUris = realm.where<WritingRealm>().findAll()
                println("puuu" + afterUris + "end")

            }
        }
    }

        fun onEditButtonTapped(view: View?) {
            val intent = Intent(this, WritingActivity::class.java)
            intent.putExtra("article", ThisArticle)
            intent.putExtra("date", ThisDate)
            intent.putExtra("uri", ThisUri)
            intent.putExtra("title", ThisTitle)
            intent.putExtra("id", ThisId)
            startActivity(intent)
        }

}
