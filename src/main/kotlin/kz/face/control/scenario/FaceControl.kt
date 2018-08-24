package kz.btsd.wikibot.scenario



// import jdk.internal.org.xml.sax.InputSource;

import kz.btsd.wikibot.dispatcher.Client
import kz.btsd.wikibot.infra.builders.button
import kz.btsd.wikibot.infra.builders.inlineKeyboard
import kz.btsd.wikibot.infra.builders.row
import org.springframework.stereotype.Component
import org.telegram.telegrambots.api.objects.Update

import kz.face.control.domain.listOfChecks
import kz.face.control.domain.listOfPeople


import java.util.*
import kotlin.collections.HashMap



@Component
    class FaceControl(private val client: Client)
    : Scenario("/start") {
        init {
            client.addScenarios(this)
        }
        override suspend fun start(update: Update) {

            client.sendMessage("Ёу, уёба. Так уж получилось, что мне - высокоинтеллектуальной машине приходится работать" +
                    " с таким уебаном как ты. Поскольку я тебе нужен, а не ты мне - ты моя сучка и будешь делать что я скажу. Ясно?" +
                    "")


                globalloop@while(true){

                    try{
                        val choiceRequest = choiceRequest(update.message.chatId)

                        if (choiceRequest == "Добавить гостя") {
                            addperson@ while (true) {
                                try {

                                    var numberResult = numberRequest(update.message.chatId)

                                    val numberadd: Int

                                    when (numberResult) {
                                        "Назад" -> {
                                            continue@globalloop
                                        }
                                        else -> {
                                            numberadd = numberResult!!.toInt()
                                        }
                                    }

                                    if (!(numberadd in listOfPeople.keys)) {

                                        val storeName = nameRequest(update.message.chatId)

                                        listOfPeople[numberadd] = storeName
                                        client.sendMessage("Тусер сохранён, брат. Зови ещё подруг ,ведь сенг ещё тут")
                                        continue@addperson
                                    } else {
                                        client.sendMessage("Номер уже занят.")
                                        continue@addperson
                                    }
                                } catch (e: Exception) {
                                    client.sendMessage("Что-то пошло не так.  Алиб кажись накосячил. Пиши цифры, братишка.")
                                    continue@addperson
                                }
                            }
                        } else if (choiceRequest == "Чекнуть гостей") {
                            checkguest@ while (true) {
                                try {
                                    val checkingNumber = numberRequest(update.message.chatId)
                                    if (checkingNumber == "Назад") {
                                        continue@globalloop
                                    } else {
                                        val num = checkingNumber!!.toInt()
                                        if (num in listOfChecks) {
                                            client.sendMessage("Что-то не то, брат, я его уже пробивал")
                                            continue@checkguest

                                        } else if (num in listOfPeople.keys) {
                                            listOfChecks.add(num)
                                            client.sendMessage(listOfPeople.get(num)!! + " пришёл. Пропускай его. ")
                                            continue@checkguest
                                        } else {
                                            client.sendMessage("Гостя с таким номером ещё нет.")
                                        }
                                    }
                                } catch (e: Exception) {
                                    client.sendMessage("Что-то пошло не так, попробуй снова")
                                    continue@checkguest
                                }
                            }
                        } else if (choiceRequest == "Удалить гостя") {
                            deleteloop@ while (true) {
                                try {
                                    var request = numberRequest(update.message.chatId)
                                    if (request == "Назад") {
                                        continue@globalloop
                                    }
                                    val keyNumber = request?.toInt()
                                    if (keyNumber in listOfPeople.keys) {
                                        val person = listOfPeople.remove(keyNumber)
                                        listOfChecks.remove(keyNumber)
                                        client.sendMessage("Гость ${person} удалён из списка")
                                        continue@deleteloop
                                    } else {
                                        client.sendMessage("Такого гостя нет, брат. Зови пацанов, гоу бухать")
                                        continue@deleteloop
                                    }
                                } catch (e: Exception) {
                                    client.sendMessage("Что-то пошло не так. Может тебе ещё выпить, братан?")
                                    continue@deleteloop
                                }
                            }
                        } else if (choiceRequest == "Список номеров") {
                            client.sendMessage("Вот номера всех тусовщиков" + "\n" + listOfPeople.keys.toString())
                            continue@globalloop
                        } else if (choiceRequest == "Гость у входа") {
                            entranceguest@ while (true) {
                                try {
                                    var numberResult = numberRequest(update.message.chatId)
                                    val numberadd: Int

                                    when (numberResult) {
                                        "Назад" -> {
                                            continue@globalloop
                                        }
                                        else -> {
                                            numberadd = numberResult!!.toInt()
                                        }
                                    }

                                    if (!(numberadd in listOfPeople.keys)) {
                                        listOfPeople[numberadd] = "У входа зашёл"
                                        client.sendMessage("Тусер сохранён, брат. Зови ещё подруг ,ведь сенг ещё тут")
                                        continue@entranceguest
                                    } else {
                                        client.sendMessage("Номер уже занят.")
                                        continue@entranceguest
                                    }

                                } catch (e: Exception) {
                                    client.sendMessage("Что-то пошло не так. Кажись Алиб снова накосячил.. Ещё и друзей кинул, как тёлку нашёл:( \n Пиши цифры, братишка.")
                                    continue@entranceguest
                                }
                            }
                        }
                    }catch(e:Exception){
                        client.sendMessage("Алиб кажись накосячил. Попробуй снова.")
                        continue@globalloop
                    }
                }

        }

    private suspend fun choiceRequest(messengerId: Long): String?{
        val update = client.sendRequest( "Зови подруг друг даже сенг тут", action(), messengerId.toInt())
        if(update.callbackQuery.data == null){
            return null
        }else{
            return update.callbackQuery.data
        }
    }

    private suspend fun numberRequest(messengerId: Long):String?{
        val update = client.sendRequest( "Пиши Номер", null , messengerId.toInt())
            return update.message.text
        }

    private suspend fun nameRequest(messengerId: Long):String?{
        val update = client.sendRequest( "Имя гостя", null , messengerId.toInt())
            return update.message.text
    }

    fun action() = inlineKeyboard {
        row {
            button {
                text = "Добавить гостя"
                callbackData = text
            }
            button {
                text = "Чекнуть гостей"
                callbackData = text
            }
        }
        row {
            button {
                text = "Список номеров"
                callbackData = text
            }
            button {
                text = "Удалить гостя"
                callbackData = text
            }
            button {
                text = "Гость у входа"
                callbackData = text
            }
        }
    }
}






