package com.challenge.patagonian.repository.local

import com.challenge.patagonian.model.response.CacheEntry
import com.challenge.patagonian.util.extention.convertToModel
import com.challenge.patagonian.util.parsing.ParsingHelper
import java.lang.reflect.Type
import java.util.*

interface BaseLocalRepo {
     fun getCashedObject(type: Type): Any? {
        val entry: CacheEntry<Any>? = SecureSharedPref.getObject(type)
        val cachedObject: Any? = entry?.obj
        return if (cachedObject != null) {
            val mCachedObject: Any? =
                ParsingHelper.gson?.toJson(cachedObject)?.convertToModel(type)
            mCachedObject
        } else null
    }
    fun <T> saveObject(instance: T, type: Type, lastModifiedDate: Long = Date().time) =
        SecureSharedPref.Editor.putObject(CacheEntry(instance, lastModifiedDate), type).apply()
}