package com.example.cookingreport

import android.os.Bundle
import android.view.View
import io.realm.Realm
import io.realm.kotlin.createObject
import kotlinx.android.synthetic.main.activity_writing.*
import java.util.*
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.widget.*
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.activity_writing.dateText
import java.io.IOException

import android.app.Activity
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_configuration.*


class WritingActivity : FragmentActivity(), DatePickerDialog.OnDateSetListener {

    private lateinit var realm: Realm
    private var date = ""
    private var picUri = ""

    private var textView: TextView? = null
    private var imageView: ImageView? = null

    private var ThisId:String? = ""
    private var ThisTitle:String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_writing)
        var title = ""
        realm = Realm.getDefaultInstance()

        val intent = getIntent()
        val thisArticle = intent.extras?.getString("article")
        val thisDate = intent.extras?.getString("date")
        val thisUri = intent.extras?.getString("uri")
        val thisTitle = intent.extras?.getString("title")
        val thisId = intent.extras?.getString("id")

        ThisId = thisId

        if (thisId != null){
            picUri = thisUri!!
            date = thisDate!!
            title = thisTitle!!
        }

        articleEdit.setText(thisArticle)
        dateText.text = thisDate

        if(thisUri != null){
            var pfDescriptor: ParcelFileDescriptor? = null
            var pic_uri: Uri? = null
            pic_uri = Uri.parse(thisUri)

            pfDescriptor = contentResolver.openFileDescriptor(pic_uri!!, "r")
            if (pfDescriptor != null) {
                val fileDescriptor = pfDescriptor.fileDescriptor
                val bmp = BitmapFactory.decodeFileDescriptor(fileDescriptor)
                pfDescriptor.close()
                image_view.setImageBitmap(bmp)
            }

            try {
                pfDescriptor?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        val spinnerItems = realm.where<TitleRealm>(TitleRealm::class.java).findAll()
        val arrayListString = arrayListOf<String>()

        if (thisTitle != null){
            arrayListString.add(thisTitle)
        }

        spinnerItems.forEach {
            arrayListString.add(it.title.toString())
        }

        val ArrayListString = ArrayList<String>(LinkedHashSet(arrayListString))
        val array = ArrayListString.toArray()

        val adapter = ArrayAdapter(
            applicationContext,
            android.R.layout.simple_spinner_item,
            array
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerBox.adapter = adapter

        spinnerBox.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val spinnerParent = parent as Spinner
                var item = ""

                item = spinnerParent.selectedItem as String
                title = item
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        save.setOnClickListener { view: View ->
            val userText = articleEdit.getText().toString()
            var uniqueID = ""

            if(picUri != ""){
                if(thisId != null){
                    uniqueID = thisId
                    val test  = WritingRealm()
                    realm.executeTransaction{

                        test.id = uniqueID
                        test.article = userText
                        test.title = title
                        test.date = date
                        test.uri = picUri
                        realm.insertOrUpdate(test)
                        println("chooo" + title)
                    }
                }else {
                    uniqueID = UUID.randomUUID().toString()

                    realm.executeTransaction {
                        val article = it.createObject<WritingRealm>(uniqueID)

                        article.article = userText
                        article.title = title
                        article.date = date
                        article.uri = picUri
                        println("abc")
                        println(article.uri)
                    }
                }
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }else{
                val dialog = AlertDialog.Builder(this)
                dialog.setTitle("写真を選択してください")
                dialog.setPositiveButton("OK"){ dialog, which -> }
                dialog.show()
            }

        }

        imageView = findViewById(R.id.image_view)

        val button: Button = findViewById(R.id.button2)
        button2.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)

            intent.addCategory(Intent.CATEGORY_OPENABLE)

            intent.type = "*/*"

            startActivityForResult(intent, RESULT_PICK_IMAGEFILE)
        })


    }

    override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int) {

        val str = String.format(Locale.US, "%d/%d/%d", year, monthOfYear+1, dayOfMonth)
        dateText.text = str
        date = str
        println("here")
        println(str)

    }

    fun showDatePickerDialog(v: View) {
        val newFragment = DatePickerFragment()
        newFragment.show(supportFragmentManager, "datePicker")

    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)

        if (requestCode == RESULT_PICK_IMAGEFILE && resultCode == Activity.RESULT_OK) {
            if (resultData!!.data != null) {

                var pfDescriptor: ParcelFileDescriptor? = null
                try {
                    val uri = resultData.data
                    picUri = uri.toString()
                    println(picUri)

                    pfDescriptor = contentResolver.openFileDescriptor(uri!!, "r")
                    if (pfDescriptor != null) {
                        val fileDescriptor = pfDescriptor.fileDescriptor
                        val bmp = BitmapFactory.decodeFileDescriptor(fileDescriptor)
                        pfDescriptor.close()
                        imageView!!.setImageBitmap(bmp)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                } finally {
                    try {
                        pfDescriptor?.close()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }

            }
        }
    }

    companion object {

        private val RESULT_PICK_IMAGEFILE = 1001
    }

}