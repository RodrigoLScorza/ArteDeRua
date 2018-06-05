package br.com.rodrigoscorza.artederua.util

import android.content.Context
import android.graphics.Typeface.createFromAsset
import android.widget.EditText
import android.widget.TextView
import java.util.regex.Pattern

fun EditText.value(): String {
    return this.text.toString()
}


fun EditText.validEmail(): Boolean {
        return Pattern.compile(
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(this.value()).matches()

}


fun EditText.TypeFace(context: Context) {
    this.typeface = createFromAsset(context.assets, "fonts/crione.ttf")
}

fun TextView.TypeFace(context: Context){
    this.typeface = createFromAsset(context.assets, "fonts/crione.ttf")
}