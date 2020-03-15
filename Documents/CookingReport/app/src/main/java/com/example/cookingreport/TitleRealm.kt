package com.example.cookingreport

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class TitleRealm:RealmObject() {
    @PrimaryKey
    var title: String? = null
}