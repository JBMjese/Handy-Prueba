package SincronizacionPedidos

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class HandyPollingServiceSpec extends Specification implements ServiceUnitTest<HandyPollingService> {

     void "test something"() {
        expect:
        service.doSomething()
     }
}
