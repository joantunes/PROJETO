package estg.ipvc.projeto

data class user(
    val id: Int,
    val userName: String,
    val password: String
)
data class problems(
    val id: Int,
    val descr: String,
    val lat: String,
    val lng: String,
    val userID: Int
)

