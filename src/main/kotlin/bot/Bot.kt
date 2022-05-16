package bot

import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.UpdatesListener
import com.pengrad.telegrambot.model.Update
import com.pengrad.telegrambot.request.SendMessage

object Bot: BotImpl {

    private val bot: TelegramBot = TelegramBot(BOT_API_TOKEN)

    override fun listen() {
        bot.setUpdatesListener {
            it.forEach(this::process)
            UpdatesListener.CONFIRMED_UPDATES_ALL
        }
    }

    override fun process(update: Update) {

        val message = update.message()

        val messageId = message.messageId()
        val senderId = message.from().id()

        bot.execute(SendMessage(senderId, message.text()))
    }

}