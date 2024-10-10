package SincronizacionPedidos

import grails.gorm.transactions.Transactional
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory


@Transactional
class PedidoService {
    private static final Log log = LogFactory.getLog(PedidoService)

    void saveOrders(List<Map> orders) {
        orders.each { orderData ->
            if (!orderData.id || orderData.totalSales == null) {
            log.warn("Datos de pedido incompletos: ${orderData}")
            return // Salir si los datos son incompletos
            }
            // Busca el pedido por ID o crea uno nuevo si no existe
            def order = Pedido.findById(orderData.id) ?: new Pedido(id: orderData.id)

            order.totalSales = orderData.totalSales
            
            // Guarda el pedido en la base de datos
            if (order.save(flush: true)) {
                log.info("Pedido ${order.id} guardado exitosamente.")
            } else {
                log.error("Error al guardar el pedido ${order.id}: ${order.errors}")
            }
        }
    }
}