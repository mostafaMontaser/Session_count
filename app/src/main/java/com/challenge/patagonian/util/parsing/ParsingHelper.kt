package com.challenge.patagonian.util.parsing

import com.google.gson.*
import java.lang.reflect.Type

object ParsingHelper {
    var gson: Gson? = null
        get() {
            if (field == null) {
                val gsonBuilder = GsonBuilder()
                gsonBuilder.registerTypeAdapter(
                    CharSequence::class.java,
                    CharSequenceDeserializer()
                )
                field = gsonBuilder.create()
            }
            return field
        }
        private set

    fun <T> jsonParsing(string: String?, tClass: Class<T>?): T? {
        return try {
            gson!!.fromJson(string, tClass)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    //    public static <T> T xmlParsing(String string, Class<T> tClass, String tag) {
    //        T object = null;
    //        Serializer serializer = new Persister();
    //        try {
    //            object = serializer.read(tClass, string);
    //
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //            Log.e(tag, "Error in parse XML class");
    //        }
    //        return object;
    //    }
    //
    internal class CharSequenceDeserializer : JsonDeserializer<CharSequence> {
        @Throws(JsonParseException::class)
        override fun deserialize(
            element: JsonElement, type: Type,
            context: JsonDeserializationContext
        ): CharSequence {
            return element.asString
        }
    }
}