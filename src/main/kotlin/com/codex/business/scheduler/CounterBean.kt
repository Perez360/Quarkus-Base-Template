package com.codex.business.scheduler

import jakarta.enterprise.context.ApplicationScoped


@ApplicationScoped
class CounterBean {
//    private val counter = AtomicInteger()
//    private val logger: Logger = LoggerFactory.getLogger(this::class.java)
//
//    @Inject
//    private lateinit var scheduler: Scheduler
//
//
//    @Scheduled(every = "10s")
//    fun increment() {
//        counter.incrementAndGet()
//        logger.info("Counter value is: ${counter.get()}")
//    }
//
//    @Scheduled(cron = "0 15 10 * * ?")
//    fun cronJob(execution: ScheduledExecution) {
//        counter.incrementAndGet()
//        logger.info(execution.scheduledFireTime.toString())
//    }
//
//    fun trigger() {
//
//        scheduler.newJob("myJob")
//            .setCron("0/5 * * * * ?")
//            .setTask { logger.info("hiiiii") }
//            .schedule()
//    }

//    @Scheduled(cron = "{cron.expr}")
//    fun cronJobWithExpressionInConfig() {
//        counter.incrementAndGet()
//        logger.info("Cron expression configured in application.properties")
//    }
}