package it.posteitaliane.gdc.gmdcli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import org.json.JSONObject
import java.net.ConnectException


class ListOrders : CliktCommand(
    name = "list",
    help = "list registered orders"
) {
    override fun run() {

        try {

            khttp.get("${config().root}/orders")
                .jsonArray.forEach { it ->
                    val o = it as JSONObject

                    println("""${o["number"]}:${o.getJSONObject("op")["username"]}:${o["type"]}:${o["subject"]}""")
                }
        }catch (ex:ConnectException) {
            println(ex.message)
        }
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