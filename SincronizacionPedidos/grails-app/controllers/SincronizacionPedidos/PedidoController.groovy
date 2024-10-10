package SincronizacionPedidos

import groovy.json.JsonSlurper
import groovyx.net.http.RESTClient
import grails.gorm.transactions.Transactional
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@groovy.util.logging.Log
class PedidoController {

    PedidoService pedidoService

    @Transactional
    def testApi() {
        String apiToken = 'Bearer c5u6t6rmgel8vslbkfbdls7stcs1l0bm'
        String apiUrl = 'https://hub.handy.la/api/v2/salesOrder'

        try {
            // Crear el cliente REST para consumir la API
            def client = new RESTClient(apiUrl)
            def response = client.get(headers: ['Authorization': apiToken])

            log.debug("Respuesta completa de la API: ${response.data}")

            // Verificar si la respuesta es exitosa
            if (response.status == 200) {
                // Mostrar la respuesta completa para depuración
                def responseData = response.data

                if (responseData) {
                    // Analizar la respuesta JSON
                    log.info("Pedidos obtenidos: ${responseData.salesOrders}")render "Pedidos obtenidos: ${responseData.salesOrders}"
                } else {
                    log.info('La respuesta de la API está vacía.')
                }
            } else {
                log.error("Error al consumir la API: ${response.status} - ${response.data?.text ?: 'Respuesta nula'}")
            }
        } catch (Exception e) {
            // Manejo de excepciones
            log.error("Error al consumir la API: ${e.message}", e)
        }
    }

    @Transactional
    def saveOrders() {
        def jsonSlurper = new JsonSlurper()
        def ordersData = jsonSlurper.parse(request.JSON)

        if (!ordersData) {
            log.error('No se recibieron datos de pedidos.')
            render status: 400, text: 'No se recibieron datos de pedidos.'
            return
        }

        try {
            pedidoService.saveOrders(ordersData)
            render status: 200, text: 'Pedidos guardados exitosamente.'
        } catch (Exception e) {
            log.error("Error al guardar los pedidos: ${e.message}", e)
            render status: 500, text: 'Error al guardar los pedidos.'
        }
    }

}
