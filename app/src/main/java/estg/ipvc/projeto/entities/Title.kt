package estg.ipvc.projeto.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "title_table")

class Title(

    @PrimaryKey(autoGenerate = true) val id:Int? = null,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name="notes") val notes:String,
    @ColumnInfo(name ="date") val date: String

)