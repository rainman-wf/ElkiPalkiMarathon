package bot

import com.pengrad.telegrambot.model.request.InlineKeyboardButton
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup
import com.pengrad.telegrambot.request.SendMessage
import bot.BotService.bot
import users.UserService

val service = UserService

val oneButton: (String, String) -> InlineKeyboardMarkup = { text, data ->
    InlineKeyboardMarkup(InlineKeyboardButton(text).callbackData(data))
}

fun checkPaymentMsg(userID: Long) {
    bot.execute(SendMessage(userID, "Получить доступ").replyMarkup(oneButton("Проверить", "checkPayment")))
}

fun helloMsg(userId: Long) {
    bot.execute(SendMessage(userId, MSG_HELLO).replyMarkup(oneButton("Начать", "begin")))
}

fun welcomeMsg(userID: Long) {
    bot.execute(SendMessage(userID, MSG_WELCOME).replyMarkup(oneButton(BTN_READY, "ready")))
}

fun payMsg(userID: Long) {
    val payment = BotService.client.sendPaymentRequest("id$userID")
    bot.execute(
        SendMessage(userID, MSG_PAY).replyMarkup(
            InlineKeyboardMarkup(
                InlineKeyboardButton(BTN_PAY).url(payment.url)
            )
        )
    )
}

fun firstMsg(userID: Long) {
    val level = service.getUser(userID)?.level ?: return
    bot.execute(SendMessage(userID, messages[level][0]))
}

fun secondMsg(userID: Long) {
    val level = service.getUser(userID)?.level ?: return
    bot.execute(
        SendMessage(userID, messages[level][1]).replyMarkup(
            InlineKeyboardMarkup(
                arrayOf(
                    InlineKeyboardButton("1").callbackData("1"),
                    InlineKeyboardButton("2").callbackData("2"),
                    InlineKeyboardButton("3").callbackData("3"),
                    InlineKeyboardButton("4").callbackData("4"),
                    InlineKeyboardButton("5").callbackData("5"),
                ),
                arrayOf(InlineKeyboardButton("Хочу перейти к тексту!").callbackData("getText"))
            )
        )
    )
}

fun thirdMsg(userID: Long) {
    val level = service.getUser(userID)?.level ?: return
    bot.execute(SendMessage(userID, messages[level][2]))
}

fun fourthMsg(userID: Long) {
    val level = service.getUser(userID)?.level ?: return
    bot.execute(SendMessage(userID, messages[level][3]))
    service.levelUp(userID)
}

fun fifthMsg(userID: Long) {
    val level = service.getUser(userID)?.level ?: return
    bot.execute(SendMessage(userID, messages[level][0]))
}

fun sixthMsg(userID: Long) {
    val level = service.getUser(userID)?.level ?: return
    bot.execute(SendMessage(userID, messages[level][1]))
}

fun seventhMsg(userID: Long) {
    val level = service.getUser(userID)?.level ?: return
    bot.execute(SendMessage(userID, messages[level][2]))
}

fun eighthMsg(userID: Long) {
    val level = service.getUser(userID)?.level ?: return
    bot.execute(SendMessage(userID, messages[level][3]))
    service.removeUser(userID)
}

