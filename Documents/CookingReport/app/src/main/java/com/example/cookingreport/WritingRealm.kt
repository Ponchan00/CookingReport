package com.example.cookingreport

import android.net.Uri
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*
import java.util.UUID.randomUUID



open class WritingRealm : RealmObject() {
    @PrimaryKey
    var id: String? = null
    var title: String? = null
    var date: String? = null
    var article: String? = null
    var uri: String? = null
//    var searchedResult: Boolean? = null
}

