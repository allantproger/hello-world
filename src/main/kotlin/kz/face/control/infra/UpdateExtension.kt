package kz.btsd.wikibot.infra

import org.telegram.telegrambots.api.objects.Update

/**
 * Extracts part of [Update.message.text] before first whitespace
 */
fun Update.extractCommand(): String? {
    return this.message?.text?.split("\\s".toRegex(), 2)?.get(0)
}

fun Update.requireCallbackData(): String =
        this.callbackQuery.data ?: throw IllegalArgumentException("Property Update.callbackQuery.data cannot be null")
