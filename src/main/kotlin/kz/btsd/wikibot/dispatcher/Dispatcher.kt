package kz.btsd.wikibot.dispatcher

import kz.btsd.wikibot.scenario.Scenario
import kz.btsd.wikibot.MessUser
import kz.btsd.wikibot.domain.User
import kz.btsd.wikibot.infra.extractCommand
import kotlinx.coroutines.experimental.DefaultDispatcher
import kotlinx.coroutines.experimental.launch
import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.objects.Update
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import kotlin.coroutines.experimental.coroutineContext
import kotlin.coroutines.experimental.suspendCoroutine
abstract class Dispatcher : TelegramLongPollingBot(){

    private val users = HashSet<User>()
    private val scenarios = ArrayList<Scenario>()
    private val handlers = HashMap<Int, Handler>()

    fun addScenarios(vararg scenarios: Scenario) {
        this.scenarios.addAll(scenarios)
    }

    override fun onUpdateReceived(update: Update) {
        launch (DefaultDispatcher + initSession(update)) {
            try {
                val messageText = update.message.text
                val command = update.extractCommand()
                val messengerId = coroutineContext[MessUser]!!.user.messengerId

                println(messageText)

                val scenario = scenarios.find {
                    it.command == command || (messageText != null && it.globalMenuStartTextLabel == messageText)
                }

                if (scenario != null) {
                    scenario.start(update)
                } else {
                    val handler = handlers[messengerId] ?: throw IllegalStateException("Handler not found for messengerId=$messengerId and message=$messageText")
                    if (handler.requireSession) initSession(update)
                    handler.handle(update)
                }

            } catch (e: Exception) {
                val messengerId = coroutineContext[MessUser]!!.user.messengerId
                sendMessage("error", messengerId)
                throw e
            }
        }
    }

    private fun initSession(update: Update): MessUser {
        val messengerId = update.userMessengerId
        var user = users.find { messengerId == it.messengerId }
        if (user == null) {
            user = User(messengerId)
            users.add(user)
        }
        return MessUser(user)
    }

    suspend fun sendRequest(text: String,
                            replyMarkup: ReplyKeyboard? = null,
                            messengerId: Int? = null,
                            requireSession: Boolean = true): Update {
        val mesId = messengerId ?: coroutineContext[MessUser]!!.user.messengerId
        return suspendCoroutine { continuation ->
            handlers[mesId] = Handler(requireSession) { continuation.resume(it) }
            val message = SendMessage(mesId.toLong(), text)
            message.replyMarkup = replyMarkup
            execute(message)
        }
    }

    suspend fun sendMessage(text: String, messengerId: Int? = null) {
        val mesId = messengerId ?: coroutineContext[MessUser]!!.user.messengerId
        execute(SendMessage(mesId.toLong(), text))
    }

    suspend fun sendMessage(text: String, keyboard: ReplyKeyboard? = null, enableMarkdown: Boolean = false, messengerId: Int? = null) {
        val mesId = messengerId ?: coroutineContext[MessUser]!!.user.messengerId
        val message = SendMessage(mesId.toLong(), text)
        message.replyMarkup = keyboard
        if (enableMarkdown) {
            message.enableMarkdown(true)
        }
        execute(message)
    }

    private val Update.userMessengerId
        get() = (message?.from?.id ?: callbackQuery?.from?.id) ?:
        throw IllegalStateException("Can't extract user messengerId from an Update")
}

    typealias HandlerFunction = suspend (update: Update) -> Unit

    class Handler(val requireSession: Boolean = true, val handle: HandlerFunction)
