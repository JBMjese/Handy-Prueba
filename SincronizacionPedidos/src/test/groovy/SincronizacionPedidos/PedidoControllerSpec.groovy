package SincronizacionPedidos

import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class PedidoControllerSpec extends Specification implements ControllerUnitTest<PedidoController> {

     void "test index action"() {
        when:
        controller.index()

        then:
        status == 200

     }
}
