package com.example.batch.batch

import org.slf4j.LoggerFactory
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.listener.JobExecutionListenerSupport
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
class JobExecutionNotificationListener(
    private val jdbcTemplate: JdbcTemplate
) : JobExecutionListenerSupport() {

    private val logger = LoggerFactory.getLogger(JobExecutionNotificationListener::class.java)

    override fun afterJob(jobExecution: JobExecution) {
        if (jobExecution.status == BatchStatus.COMPLETED) {
            logger.info("JOB FINISHED!")

            jdbcTemplate.query("SELECT first_name, last_name FROM people") { rs, _ ->
                Person(rs.getString(1), rs.getString(2))
            }.forEach {
                logger.info("Found $it")
            }
        }
    }
}