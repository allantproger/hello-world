package kz.btsd.wikibot.infra.builders

import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton

fun inlineKeyboard(configure: MutableList<MutableList<InlineKeyboardButton>>.() -> Unit): InlineKeyboardMarkup {
    val list = mutableListOf<MutableList<InlineKeyboardButton>>()
    list.configure()
    val keyboardMarkup = InlineKeyboardMarkup()
    keyboardMarkup.keyboard = list
    return keyboardMarkup
}

fun MutableList<MutableList<InlineKeyboardButton>>.row(configure: MutableList<InlineKeyboardButton>.() -> Unit) {
    val list = mutableListOf<InlineKeyboardButton>()
    list.configure()
    add(list)
}

val MutableList<MutableList<InlineKeyboardButton>>.row
    get() = mutableListOf<InlineKeyboardButton>().also { add(it) }

fun MutableList<InlineKeyboardButton>.button(configure: InlineKeyboardButton.() -> Unit) {
    val button = InlineKeyboardButton()
    button.configure()
    this.add(button)
}
