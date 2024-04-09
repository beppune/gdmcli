package it.posteitaliane.gdc.gmdcli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands


class ListOrders : CliktCommand(
    name = "list",
    help = "list registered orders"
) {
    override fun run() {
        khttp.get("${config().root}/orders")
            .text.also(::println)
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