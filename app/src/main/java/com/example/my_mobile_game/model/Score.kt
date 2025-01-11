data class Score (
    val name: String,
    val score: Int,
    val lon: Long,
    val lan: Long,
) {
    class Builder(
        var name: String = "",
        var score: Int = 0,
        var lon: Long = 0L,
        var lan: Long = 0L,
        var scoreDate: Long = 0L
    ) {
        fun name(name: String) = apply { this.name = name }
        fun score(score: Int) = apply { this.score = score }
        fun lon(lon: Long) = apply { this.lon = lon }
        fun lan(lan: Long) = apply { this.lan = lan }
        fun build() = Score(
            name = name,
            score = score,
            lon = lon,
            lan = lan,
        )
    }
}
