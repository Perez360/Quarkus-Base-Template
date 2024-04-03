package com.codex.base.utils

import com.codex.base.enums.LogLevel
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.jboss.logging.Logger
import java.io.BufferedWriter
import java.io.FileWriter
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.stream.Collectors
import java.util.stream.Stream
import kotlin.io.path.absolutePathString
import kotlin.system.exitProcess


@ApplicationScoped
class AppLogger {

    @ConfigProperty(name = "system.log.file.path", defaultValue = "data/log")
    private lateinit var logPath: String

    @Inject
    private lateinit var logger: Logger

    fun log(logLevel: LogLevel, sessionId: String, message: String, ex: Exception? = null) {
        // Logging to terminal
        logger.log(Logger.Level.valueOf(logLevel.name), message)

        // Writing logs to file
        val filename = String.format("%s%s", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), ".log")
        val logTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:SS a"))

        val formattedMessage = Stream.builder<String>()
            .add(logTime)
            .add(LogLevel.INFO.name)
            .add(message)
            .add(ex?.stackTraceToString())
            .build()
            .filter(Objects::nonNull)
            .collect(Collectors.joining(" | "))

        val filePath: Path = Paths.get(logPath, filename)
        try {
            // Create the directory if it doesn't exist
            Files.createDirectories(filePath.parent);

            // Create the file
            if (!filePath.toFile().exists()) Files.createFile(filePath);

            BufferedWriter(FileWriter(filePath.absolutePathString(), true)).use { writer ->
                writer.write(formattedMessage + System.lineSeparator())
            }
        } catch (e: IOException) {
            e.printStackTrace()
            exitProcess(1)
        }
    }
}