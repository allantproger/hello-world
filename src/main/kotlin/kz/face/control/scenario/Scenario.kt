package kz.btsd.wikibot.scenario

import org.telegram.telegrambots.api.objects.Update

abstract class Scenario(val command: String, val globalMenuStartTextLabel: String? = null) {
    abstract suspend fun start(update: Update)
    //abstract suspend fun help(update:Update)
}
