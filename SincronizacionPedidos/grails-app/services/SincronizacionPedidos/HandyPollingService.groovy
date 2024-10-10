package SincronizacionPedidos

import grails.gorm.transactions.Transactional
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException

@Transactional
class HandyPollingService implements Job {

    @Override
    void execute(JobExecutionContext context) throws JobExecutionException {
        // Llamamos al método que se encargará de realizar el polling
        fetchSalesOrders()
    }

    void fetchSalesOrders() {
        // Configuramos la fecha y hora actual
        def now = new Date()
        def lastTime = now - 10.minutes // 10 minutos antes

        // Creamos la URL para la API de Handy
        def url = "https://hub.handy.la/api/v2/salesOrder?start=${lastTime.format('yyyy-MM-dd HH:mm:ss')}&end=${now.format('yyyy-MM-dd HH:mm:ss')}&deleted=false"

        def response = restTemplate.getForObject(url, String.class)

        if (response) {
            def salesOrders = new JsonSlurper().parseText(response).salesOrders

            businessLogic(salesOrders, false)
        }
    }

    void businessLogic(salesOrders, deleted) {
        for (salesOrder in salesOrders) {
            // Guarda el pedido de ventas en un sistema ERP
            def erpSalesOrder = new ErpSalesOrder(
                externalId: salesOrder.id,
            )
            erpSalesOrder.save()
        }
    }

}
