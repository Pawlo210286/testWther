// Инкапсулирует работу с ContextCompat
@file:SuppressWarnings("ForbiddenImport")

package com.testwther.ui.common

import android.content.Context
import androidx.core.content.ContextCompat
import com.testwther.domain.resources.ResourceReader
import java.io.InputStream

class AndroidResourceReader(
    private val context: Context
) : ResourceReader {

    private val resources = context.resources

    override fun getString(resId: Int): String {
        return resources.getString(resId)
    }

    override fun getString(resId: Int, vararg args: Any): String {
        @Suppress("SpreadOperator") // Не влияет на производительность, нужен для передачи параметров
        return resources.getString(resId, *args)
    }

    override fun getQuantityString(
        pluralResId: Int,
        quantity: Int,
        vararg formatArgs: Any
    ): String {
        @Suppress("SpreadOperator") // Не влияет на производительность, нужен для передачи параметров
        return resources.getQuantityString(pluralResId, quantity, *formatArgs)
    }

    override fun getStringArray(resId: Int): Array<String> {
        return resources.getStringArray(resId)
    }

    override fun getColor(resId: Int): Int {
        return ContextCompat.getColor(context, resId)
    }

    override fun getAsset(path: String): InputStream {
        return context.assets.open(path)
    }
}
