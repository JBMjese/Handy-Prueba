package SincronizacionPedidos

class UrlMappings {
    static mappings = {        
        
        // Rura para el consumo de la api
        "/test"(controller: "pedido", action: 'testApi', method: "GET")
        "/saveOrders"(controller: "pedido", action: 'saveOrders', method: "POST")


        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')

    }
}
