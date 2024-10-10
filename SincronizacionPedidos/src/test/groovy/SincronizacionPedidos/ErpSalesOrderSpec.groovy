package SincronizacionPedidos

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class ErpSalesOrderSpec extends Specification implements DomainUnitTest<ErpSalesOrder> {

     void "test domain constraints"() {
        when:
        ErpSalesOrder domain = new ErpSalesOrder()
        //TODO: Set domain props here

        then:
        domain.validate()
     }
}
