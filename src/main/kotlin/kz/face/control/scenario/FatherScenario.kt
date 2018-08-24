package kz.btsd.wikibot.scenario


//@Component
//class FatherScenario (
//        private val client: Client,
//        private val parser: Parser
//) : Scenario("/start") {
//    init {
//        client.addScenarios(this)
//    }
//
//    var botSet = HashSet<Bot?>()
//
//    override suspend fun start(update: Update) {
//
//        client.sendMessage("Привет! Я бот, с помощью которого ты можешь получить последние новости с Tengrinews по тегам!")
//
//        queryloop@ while(true){
//
//            var query = requestQuery(update.message.chatId)
//
//
//
//
//
//
//
//        }
//
//
////      queryloop@ while (true) {
////
////            var query = requestQuery(update.message.chatId)
////
////            var query_url = requestQueryUrl(query)
////
////            var query_info = requestQueryInfo(query_url, update.message.chatId)
////
////            client.sendMessage(query_info, update.message.chatId.toInt())
////
////            resultsloop@ while (true){
////
////                try{
////
////                    var choice = choiceRequest(update.message.chatId)
////
////                    if (choice == "Да") {
////                        var more_query_info = requestMoreQueryInfo(query_url)
////                        client.sendMessage(more_query_info, update.message.chatId.toInt())
////                        continue@queryloop
////
////                    } else if (choice == "Сделать новый запрос") {
////                        continue@queryloop
////                    }
////
////                }catch (e: Exception ){
////                    client.sendMessage("Нажмите на следующее действие", update.message.chatId.toInt())
////
////                }
////            }
////        }
//    }
//
//
//    private suspend fun requestQuery(chatId: Long): String {
//        val update = client.sendRequest("Введите ваш запрос, пожалуйста.", null, chatId.toInt())
//        return update.message.text
//    }
//
////    private suspend fun request(chatId:Long): String{
////        val update = client.sendRequest("Set a description for your bot", null, chatId.toInt())
////        return update.message.text
////    }
//
////    private  suspend fun requestNameBot(chatId:Long): String?{
////
////        val update = client.sendRequest("Введите имя для нового бота", null, chatId.toInt())
////        if(update.message.text.contains("bot")){
////              return update.message.text
////        }
////        else
////            return null
////
////    }
//
////    private suspend fun requestQueryUrl(query: String): String {
////        val result: String = parser.urlFinder(query)
////        return result
////    }
//
////    private suspend fun requestQueryInfo(query_url: String, chatId: Long): String{
////        val result: String = parser.outputer(query_url)
////        val output: String = "Извините, больше информации по этому запросу нет."
////        if(result!= "No result"){
////            return result
////        }else{
////            return output
////        }
////    }
//
////    private suspend fun otherResults(query_url: String): String{
////        var result: String = parser.moreOutputer(query_url)
////        return result
////    }
////      request more query info
////    private suspend fun requestMoreQueryInfo(query_url: String): String{
////        val result: String
////        val output = "Извините, больше информации по этому запросу нет."
////        result = otherResults(query_url)
////        if(result != "No result"){
////            return result
////        }else{
////            return output
////        }
////    }
//
////    private suspend fun choiceRequest(messengerId: Long): String?{
////        val update = client.sendRequest("Просмотреть больше вариантов?", moreResult(), messengerId.toInt())
////        if(update.callbackQuery.data == null){
////            return null
////        }else{
////            return update.callbackQuery.data
////        }
////    }
//
//
////    fun moreResult() = inlineKeyboard {
////        row {
////            button {
////                text = "Сделать новый запрос"
////                callbackData = text
////            }
////            button {
////                text = "Да"
////                callbackData = text
////            }
////        }
////    }
//
////    fun createBot() = inlineKeyboard {
////        row{
////            button {
////                text = "Новый бот"
////                callbackData = text
////                }
////            }
////        }
//    // name can be null
////     fun newBot(botName: String?, dscrpt: String? = ""): Bot?{
////
////        val uuid: UUID = UUID.randomUUID()
////        val uuidToString: String = uuid.toString()
////        val token = uuidToString
////        if (botName != null) {
////            val newBot = Bot(botName, token)
////            return newBot
////        }
////        else return null
////    }
//
//
//
//
//
//
//
//
//
//
//
//}
//
//
//
//
//
//
