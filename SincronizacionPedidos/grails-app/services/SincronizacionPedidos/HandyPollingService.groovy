package SincronizacionPedidos

import grails.gorm.transactions.Transactional
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException
import groovy.json.JsonSlurper


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

    void businessLogic(salesOrders, logInfo = true) {
        log.info('Inicio del método businessLogic')

        try {
            // Guarda los pedidos de ventas  en sistema ERP
            for (salesOrder in salesOrders) {
                def erpSalesOrder = new ErpSalesOrder(
                externalId: salesOrder.id,
            )
                erpSalesOrder.save()
            }

            // Detecta pedidos eliminados en Handy y elimina los de la base de datos local
            def handySalesOrdersIds = salesOrders*.id
            def localSalesOrders = ErpSalesOrder.findAllByExternalIdNotInList(handySalesOrdersIds)
            localSalesOrders*.delete()

            if (logInfo) {
                log.info("Pedidos de ventas guardados en sistema ERP: ${salesOrders.size()}")
                log.info("Pedidos de ventas eliminados de sistema ERP: ${localSalesOrders.size()}")
            }
    } catch (Exception e) {
            log.error("Error en el método businessLogic: ${e.message}")
        }

        log.info('Fin del método businessLogic')
    }

}
