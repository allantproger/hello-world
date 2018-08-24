package kz.btsd.wikibot

import kz.btsd.wikibot.domain.User
import kotlin.coroutines.experimental.AbstractCoroutineContextElement
import kotlin.coroutines.experimental.CoroutineContext



class MessUser(val user: User) : AbstractCoroutineContextElement(MessUser) {
    companion object Key : CoroutineContext.Key<MessUser>
}
