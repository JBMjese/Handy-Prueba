package SincronizacionPedidos

class Pedido {

    Long id
    BigDecimal totalSales

    static constraints = {
        id nullable: false, unique: true
        totalSales nullable: false
    }
}