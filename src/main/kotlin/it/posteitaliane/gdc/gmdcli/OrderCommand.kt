package it.posteitaliane.gdc.gmdcli

import cc.ekblad.toml.decode
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.arguments.argument
import org.json.JSONObject
import java.io.FileInputStream
import java.net.ConnectException
import java.nio.file.Files
import java.nio.file.Path


class ListOrders : CliktCommand(
    name = "list",
    help = "list registered orders"
) {
    override fun run() {

        try {

            khttp.get("${config().root}/orders")
                .jsonArray.forEach { it ->
                    val o = it as JSONObject

                    mapper().readValue(o.toString(), Order::class.java)
                        .also {
                            println(it)
                            it.lines.forEach(::println)
                        }
                }
        }catch (ex:ConnectException) {
            println(ex.message)
        }
    }

}

class Sumbit : CliktCommand() {

    val file:String by argument(help = "Textfile for submition in toml format")

    override fun run() {

        val content = Files.readAllBytes( Path.of(file) ).toString()

        reader()
    }

}

class OrderCommand : CliktCommand(
    name = "orders",
    help = "Operations on orders. Default prints orders list",
    invokeWithoutSubcommand = true
) {

    private val list = ListOrders()

    init {
        subcommands(list)
    }

    override fun run() {
        if( currentContext.invokedSubcommand == null ) {
            list.run()
        }
    }
}