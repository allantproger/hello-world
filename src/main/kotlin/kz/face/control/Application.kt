package kz.btsd.wikibot


import kz.btsd.wikibot.config.HttpClientAcceptSelfSignedCertificate
import kz.btsd.wikibot.dispatcher.Dispatcher
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.TelegramBotsApi
import org.telegram.telegrambots.starter.EnableTelegramBots
import java.util.*

@SpringBootApplication
@EnableTelegramBots
class Application

fun main(args: Array<String>) {
   // HttpClientAcceptSelfSignedCertificate.free()
    setupJvmProperties()

    ApiContextInitializer.init()
   val context = runApplication<Application>(*args)
//    val RSSParser = context.getBean(RSSparser::class.java)
//    val feedString = RSSParser.getStringRSS()

    val dispatchers: Collection<Dispatcher> = context.getBeansOfType(Dispatcher::class.java).values

    try {
        dispatchers.forEach { TelegramBotsApi().registerBot(it) }
    } catch (e: Exception) {
    }
}


private fun setupJvmProperties() {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
    System.setProperty("jsse.enableSNIExtension", "false")
}

