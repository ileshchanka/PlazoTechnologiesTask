package info.igorek.plazotechnologiestask.defaultComponent

data class User(
    val id: Int,
    val fullName: String,
    val email: String,
    val avatarUrl: String,
) {
    companion object {
        val DEFAULT_USER = User(
            id = 1,
            fullName = "Ihar Leshchanka",
            email = "",
            avatarUrl = "",
        )
    }
}
