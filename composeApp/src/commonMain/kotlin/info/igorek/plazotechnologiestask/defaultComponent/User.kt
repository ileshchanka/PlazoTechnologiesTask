package info.igorek.plazotechnologiestask.defaultComponent

data class User(
    val id: Int,
    val fullName: String,
    val email: String,
    val avatarUrl: String,
) {
    companion object {
        val EMPTY = User(
            id = 0,
            fullName = "",
            email = "",
            avatarUrl = "",
        )
        val DEFAULT = User(
            id = 1,
            fullName = "Ihar Leshchanka",
            email = "i@i.com",
            avatarUrl = "",
        )
    }
}
