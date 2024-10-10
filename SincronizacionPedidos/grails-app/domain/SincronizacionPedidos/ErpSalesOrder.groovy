package SincronizacionPedidos

import grails.gorm.annotation.Entity

@Entity
class ErpSalesOrder {

    String externalId
    Date createdDate
    Date updatedDate

    static constraints = {
        externalId nullable: false, unique: true
        createdDate nullable: false
        updatedDatenullable: false
    }

}
