package estg.ipvc.projeto

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddCity : AppCompatActivity() {
    private lateinit var cidadeText: EditText
    private lateinit var notesText: EditText
    private lateinit var dateText: EditText

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_city)
        cidadeText=findViewById(R.id.title)
        notesText=findViewById(R.id.notes)
        dateText=findViewById(R.id.date)
        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(notesText.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                replyIntent.putExtra(EXTRA_REPLY_TITLE, cidadeText.toString())
                replyIntent.putExtra(EXTRA_REPLY_NOTES, notesText.toString())
                replyIntent.putExtra(EXTRA_REPLY_DATE, dateText.toString())


                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }
        companion object {
            const val EXTRA_REPLY_TITLE= "ipvc.estg.projeto.title"
            const val EXTRA_REPLY_NOTES = "ipvc.estg.projeto.notes"
            const val EXTRA_REPLY_DATE = "ipvc.estg.projeto.date"

        }
}