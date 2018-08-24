package kz.btsd.wikibot.scenario

import kz.btsd.wikibot.dispatcher.Client
import kz.btsd.wikibot.domain.Parser
import kz.btsd.wikibot.infra.builders.button
import kz.btsd.wikibot.infra.builders.inlineKeyboard
import kz.btsd.wikibot.infra.builders.row
import org.telegram.telegrambots.api.objects.Update

class FatherScenario (
        private val client: Client,
        private val parser: Parser
) : Scenario("/start") {
    init {
        client.addScenarios(this)
    }

    override suspend fun start(update: Update) {

        client.sendMessage("Ёу, пацаны. Я ваш дохуя виртуальный помошник. Буду отслеживать пробились QR коды или нет." +
                "Для этого просто напишите мне номер посетителя. И всё нахуй ежжи. Алиба только не пускайте, а то он пидар. Даже Рауль" +
                "хоть и гей, но он не пидар ,как Алиб пидар. Пидрила. \nФу")
        val set = arrayListOf<Int>()
        loop@while(true) {
            if(update.message.text == "/show"){
                client.sendMessage(set.toString())
            }
            try {
                val value = update.message.text.toInt()
                if (!(value in set)) {
                    set.add(value)
                    client.sendMessage("Салам новому гостю")
                }else{
                    client.sendMessage("Этот QRcode был уже пробит. Тут что-то не то, разберитесь")
                }
            }catch (e: Exception){
                client.sendMessage(" Вводи цифры ебанат, я тупой слова не понимаю")
            }
            continue@loop
        }
    }


}






