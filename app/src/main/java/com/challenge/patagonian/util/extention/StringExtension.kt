package com.challenge.patagonian.util.extention

import com.challenge.patagonian.util.parsing.ParsingHelper
import java.lang.reflect.Type

fun <T> String.convertToModel(type: Type): T? {
    return ParsingHelper.gson?.fromJson<T>(this, type)}


