package SincronizacionPedidos

import grails.gorm.transactions.Transactional

@Transactional
class PedidoService {

    void saveOrders(List<Map> orders) {
        orders.each { orderData ->
            if (!orderData.id || orderData.totalSales == null) {
            logActivity("Datos de pedido incompletos: ${orderData}")
            return // Salir si los datos son incompletos
            }
            // Busca el pedido por ID o crea uno nuevo si no existe
            def order = Pedido.findById(orderData.id) ?: new Pedido(id: orderData.id)

            order.totalSales = orderData.totalSales
            
            // Guarda el pedido en la base de datos
            if (order.save(flush: true)) {
                logActivity("Pedido ${order.id} guardado exitosamente.")
            } else {
                logActivity("Error al guardar el pedido ${order.id}: ${order.errors}")
            }
        }
    }
}