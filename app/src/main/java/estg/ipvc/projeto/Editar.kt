package estg.ipvc.projeto

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class Editar : AppCompatActivity() {
    private lateinit var text1: EditText
    //  private lateinit var text2: EditText

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar)
        text1=findViewById(R.id.title)
        // text2=findViewById(R.id.notes)

        val button = findViewById<Button>(R.id.button_edit)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(text1.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val title = text1.text.toString()
                // val notes = text2.text.toString()
                text1.setText(title)


                replyIntent.putExtra(EXTRA_REPLY_TITLE, title.toString())
                // replyIntent.putExtra(EXTRA_REPLY_NOTES, notes.toString())



                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }


    companion object {
        const val EXTRA_REPLY_TITLE= "ipvc.estg.projeto.title"
        //const val EXTRA_REPLY_NOTES = "ipvc.estg.projeto.notes"


    }
}