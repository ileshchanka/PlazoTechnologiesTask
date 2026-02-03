package info.igorek.plazotechnologiestask

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform