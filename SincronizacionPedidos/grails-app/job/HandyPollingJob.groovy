package SincronizacionPedidos

import org.quartz.JobDetail
import org.quartz.Trigger
import org.quartz.impl.StdSchedulerFactory

class HandyPollingJob {
    static triggers = {
        cron name: 'handyPollingTrigger', cronExpression: '*/10 * * * * ?'
    }

    def execute() {
        def handyPollingService = new HandyPollingService()
        handyPollingService.execute(null)
    }
}