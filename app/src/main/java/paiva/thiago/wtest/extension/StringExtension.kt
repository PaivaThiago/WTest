package paiva.thiago.wtest.extension

import androidx.core.text.isDigitsOnly
import java.text.Normalizer

private val String.Companion.EMPTY: String
    get() = ""

fun String.withWildCard() = "%$this%"

private fun String.hasLength(length: Int): Boolean = this.length == length

fun String.validateAsNumber(length: Int) = this.isNotEmpty()
        && this.hasLength(length) && this.isDigitsOnly()

fun String.cleanText() = this
    .replace(" ", String.EMPTY)
    .replace(".", String.EMPTY)
    .replace(".", String.EMPTY)
    .replace("/", String.EMPTY)
    .replace("(", String.EMPTY)
    .replace(")", String.EMPTY)
    .replace(" ", String.EMPTY)
    .replace("-", String.EMPTY)
    .replace(",", String.EMPTY)
    .stripAccents()

private fun String.stripAccents(): String = Normalizer
    .normalize(this, Normalizer.Form.NFD)
    .replace("[\\p{InCombiningDiacriticalMarks}]".toRegex(), String.EMPTY)