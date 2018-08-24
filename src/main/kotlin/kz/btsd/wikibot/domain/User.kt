package kz.btsd.wikibot.domain

data class User (val messengerId: Int, var query: String? = null) {
    override fun equals(other: Any?): Boolean = if (other is User) messengerId == other.messengerId else false
}