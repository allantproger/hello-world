package kz.btsd.wikibot.dispatcher

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class Client(
        @Value("651222299:AAEquwVatYVaRhbewomp0kKMYOQqhL3mSps") val token: String,
        @Value("awecawecwabot") val username: String
) : Dispatcher() {

    override fun getBotToken() = token
    override fun getBotUsername() = username
}

