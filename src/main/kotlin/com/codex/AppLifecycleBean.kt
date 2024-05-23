package com.codex

import io.quarkus.runtime.ShutdownEvent
import io.quarkus.runtime.StartupEvent
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.event.Observes
import jakarta.inject.Inject
import org.jboss.logging.Logger


@ApplicationScoped
class AppLifecycleBean {

    @Inject
    private lateinit var logger: Logger

    fun onStart(@Observes ev: StartupEvent?) {
        logger.info("The application is starting...")
    }

    fun onStop(@Observes ev: ShutdownEvent?) {
        logger.info("The application is stopping...")
    }
}