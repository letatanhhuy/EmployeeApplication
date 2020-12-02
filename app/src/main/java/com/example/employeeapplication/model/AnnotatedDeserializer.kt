package com.example.employeeapplication.model

import com.google.gson.*
import java.lang.reflect.Field
import java.lang.reflect.Type

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class JsonRequired

class AnnotatedDeserializer<T> : JsonDeserializer<T> {
    @Throws(JsonParseException::class)
    override fun deserialize(je: JsonElement?, type: Type?, jdc: JsonDeserializationContext?): T {
        val pojo: T = Gson().fromJson(je, type)
        val fields: Array<Field> = pojo!!::class.java.declaredFields
        for (f in fields) {
            if (f.getAnnotation(JsonRequired::class.java) != null) {
                f.isAccessible = true
                if (f.get(pojo) == null) {
                    throw JsonParseException("Missing field in JSON: " + f.name)
                }
            }
        }
        return pojo
    }

    companion object {
        private const val TAG = "EA:MO:AND"
    }
}
//ref:
//https://stackoverflow.com/questions/21626690/gson-optional-and-required-fields