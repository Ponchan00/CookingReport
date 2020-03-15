package com.example.cookingreport

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Spinner
import androidx.recyclerview.widget.GridLayoutManager
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_configuration.list
import kotlinx.android.synthetic.main.activity_reading.*


class ReadingActivity : AppCompatActivity() {

    private lateinit var realm: Realm
    var title = ""

    lateinit var uris:RealmResults<WritingRealm>
    lateinit var searchedUris:RealmResults<WritingRealm>
    lateinit var articles:RealmResults<WritingRealm>
    lateinit var adapter:ReadingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reading)

        realm=Realm.getDefaultInstance()
        val intent = getIntent()

        realm.executeTransaction{
            articles = realm.where<WritingRealm>().findAll()
        }

        val test:String = "aiueo"

        realm.executeTransaction{
            uris = realm.where<WritingRealm>().findAll()
        }
        println(uris)
        adapter=ReadingAdapter(this,uris)

        list.adapter = adapter
        list.layoutManager = GridLayoutManager(this, 3)

        val spinnerItems = realm.where<TitleRealm>(TitleRealm::class.java).findAll()
        val arrayListString = arrayListOf<String>()
        arrayListString.add("全記事")
        spinnerItems.forEach {
            arrayListString.add(it.title.toString())
        }
        val array = arrayListString.toArray()

        val adapter2 = ArrayAdapter(
            applicationContext,
            android.R.layout.simple_spinner_item,
            array
        )

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner2.adapter = adapter2

        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val spinnerParent = parent as Spinner
                val item = spinnerParent.selectedItem as String

                title = item
                realm.executeTransaction{
                    if(title == "全記事"){
                        uris = realm.where<WritingRealm>().findAll()
                    }else{
                        uris = realm.where<WritingRealm>().equalTo("title",title).findAll()
                    }

                }
                adapter = ReadingAdapter(this@ReadingActivity,uris)
                list.adapter = adapter
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.readingmenu, menu)
        if (menu != null) {
            val searchView = menu.findItem(R.id.menu_search).actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(text: String?): Boolean {
                    val searchText = searchView.getQuery().toString()
                    realm.executeTransaction{
                        if(searchText == ""){
                            uris = realm.where<WritingRealm>().findAll()
                            adapter = ReadingAdapter(this@ReadingActivity,uris)
                        }else{
                            articles.forEach{
                                searchedUris = realm.where<WritingRealm>().contains("article", searchText).findAll()
                                println("debuguri" + searchedUris)
                            }
                            adapter = ReadingAdapter(this@ReadingActivity,searchedUris)
                        }
                    }
                    list.adapter = adapter

                    fun onNothingSelected(parent: AdapterView<*>?) {

                    }
                    return false
                }

                    override fun onQueryTextChange(text: String?): Boolean {
                        return false
                    }

            })
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}


