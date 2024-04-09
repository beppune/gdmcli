package it.posteitaliane.gdc.gmdcli

import com.github.ajalt.clikt.core.CliktCommand

class RootCommand(val config:Config) : CliktCommand(name = "gdmcli") {

    override fun run() = Unit


}