package bot

import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.UpdatesListener
import com.pengrad.telegrambot.model.Update
import com.pengrad.telegrambot.request.SendMessage
import payment.Client
import payment.MyAuthenticator
import users.UserService
import java.io.File
import java.net.Authenticator

object BotService : BotImpl {

    private val service = UserService
    var dayLevel = 0

    val file = File("users.txt")

    val client = Client

    val bot: TelegramBot = TelegramBot(BOT_API_TOKEN)

    override fun listen() {

        file.createNewFile()

        Authenticator.setDefault(MyAuthenticator)

        //  val long = Date().time + 120_000

//        timer.schedule(First, Date(long), period)
//        timer.schedule(Second, Date(long + 2 * ms), period)
//        timer.schedule(Fourth, Date(long + 5 * 60 * ms), period)
//        timer.schedule(Fifth, Date(long + 8 * 60 * ms), period)
//        timer.schedule(Sixth, Date(long + 8 * 60 * ms + 30 * ms), period)
//        timer.schedule(Seventh, Date(long + 9 * 60 * ms), period)
//        timer.schedule(Eight, Date(long + 9 * 60 * ms + 3 * ms), period)

        bot.setUpdatesListener {
            it.forEach(this::process)
            UpdatesListener.CONFIRMED_UPDATES_ALL
        }
    }

    override fun process(update: Update) {

        val callBack = update.callbackQuery()
        val message = update.message()

        val senderId = message?.from()?.id() ?: callBack.from().id()

        if (message?.text() == CMD_START) helloMsg(senderId)
        if (message?.text() == "/rrr") service.addUser(senderId)
        if (message?.text() == "/aaa") service.getAll().forEach { file.appendText("${it.key} : ${it.value}\n") }
        if (message?.text() == "/bbb") {
            val stringBuffer = StringBuffer()
            file.readLines().forEach{stringBuffer.append(it).append("\n")}
            bot.execute(SendMessage(senderId, stringBuffer.toString()))
        }

        when (callBack?.data()) {
            "begin" -> if (isRegistered(senderId)) welcomeMsg(senderId) else payMsg(senderId)
            "1", "2", "3", "4", "5" -> thirdMsg(senderId)
//                    val query = AnswerCallbackQuery(callBack.id())
//                    query.text(NOTIFICATION_WAIT + formatDiffTime(86000000 - diffTime))
//                    query.showAlert(true)
//                    bot.execute(query)

        }
    }

    private fun isRegistered(userID: Long) = service.getUser(userID) != null
}

