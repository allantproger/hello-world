package kz.btsd.wikibot.infra.builders

import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow
import java.util.*

fun replyKeyboard(configure: ReplyKeyboardMarkup.() -> Unit): ReplyKeyboardMarkup {
    val keyboardMarkup = ReplyKeyboardMarkup()
    keyboardMarkup.configure()
    return keyboardMarkup
}

fun ReplyKeyboardMarkup.keyboard(configure: ArrayList<KeyboardRow>.() -> Unit) {
    val array = ArrayList<KeyboardRow>()
    array.configure()
    keyboard = array
}

fun MutableList<KeyboardRow>.row(configure: KeyboardRow.() -> Unit) {
    val row = KeyboardRow()
    row.configure()
    add(row)
}

val MutableList<KeyboardRow>.row get() = KeyboardRow().also { add(it) }

fun KeyboardRow.button(configure: KeyboardButton.() -> Unit) {
    val button = KeyboardButton()
    button.configure()
    add(button)
}
