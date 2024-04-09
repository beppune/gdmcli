package it.posteitaliane.gdc.gmdcli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import java.io.FileInputStream
import java.nio.file.Files
import java.util.Properties
import kotlin.io.path.Path

fun CliktCommand.config() : Config = (currentContext.findRoot().command as RootCommand).config

fun main(args:Array<String>) {

    val ins = Thread.currentThread().contextClassLoader.getResourceAsStream("gdmrcdefaults")!!

    var props = Properties().apply {
        load(ins)
        ins.close()
    }

    var config = Config(
        root = props.getProperty("root")!!
    )

    if( Files.exists(Path(".gdmrc")) ) {

        props.load( FileInputStream(".gdmrc") )

        config.root = props.getProperty("root", config.root)
    }

    RootCommand(config)
        .subcommands(OrderCommand())
        .main(args)

}