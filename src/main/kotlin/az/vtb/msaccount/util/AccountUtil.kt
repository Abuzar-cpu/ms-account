package az.vtb.msaccount.util

import java.util.Random


private const val ALLOWED_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
private val random = Random()

fun generateRandomString(length: Int): String {
    val sb = StringBuilder(length)
    for (i in 0 until length) {
        val randomIndex: Int = random.nextInt(ALLOWED_CHARACTERS.length)
        sb.append(ALLOWED_CHARACTERS[randomIndex])
    }
    return sb.toString()
}

fun generateAccountNumber(currency: String): String {
    val randomString1 = generateRandomString(3)
    val randomString2 = generateRandomString(4)
    val randomString3 = generateRandomString(2)

    val remainingLength = 25 - randomString1.length - randomString2.length - currency.length - randomString3.length
    require(remainingLength >= 0) { "Total length exceeds 12 characters" }
    val paddedRandomString3 = generateRandomString(remainingLength)

    return "Abu$randomString1$currency$randomString2$paddedRandomString3"
}
