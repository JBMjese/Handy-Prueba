package SincronizacionPedidos

import groovy.json.JsonSlurper
import groovyx.net.http.RESTClient
import grails.gorm.transactions.Transactional

class PedidoController {

    @Transactional
    def testApi() {
        String apiToken = 'Bearer c5u6t6rmgel8vslbkfbdls7stcs1l0bm'
        String apiUrl = 'https://hub.handy.la/api/v2/salesOrder'

        try {
            // Crear el cliente REST para consumir la API
            def client = new RESTClient(apiUrl)
            def response = client.get(headers: ['Authorization': apiToken])


            // para depuración
            println "Respuesta completa de la API: ${response.data}"

            // Verificar si la respuesta es exitosa
            if (response.status == 200) {
                // Mostrar la respuesta completa para depuración
                def responseData = response.data

                if (responseData) {
                    // Analizar la respuesta JSON
                    //def orders = new JsonSlurper().parseText(responseText)
                    render "Pedidos obtenidos: ${responseData.salesOrders}"
                } else {
                    render 'La respuesta de la API está vacía.'
                }
            } else {
                render "Error al consumir la API: ${response.status} - ${response.data?.text ?: 'Respuesta nula'}"
            }
        } catch (Exception e) {
            // Manejo de excepciones
            render "Error al consumir la API: ${e.message}"
            e.printStackTrace() // Para ver más detalles en la consola
        }
    }
}