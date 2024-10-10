# SincronizacionPedidos 🚀

## Descripción

Este proyecto es una aplicación web creada con **Grails** y **Groovy** que se conecta a la API de Handy para sincronizar los pedidos cada 10 minutos. Los pedidos se almacenan en una base de datos local, y aquellos que hayan sido eliminados en la API se eliminan también en la base de datos local. 📦

## Características clave ✨

- Realiza **polling** a la API de Handy cada 10 minutos para obtener la lista de pedidos. ⏲️
- **Guarda** los pedidos en una base de datos local. 💾
- **Elimina** los pedidos que ya no están disponibles en la API. ❌
- Registra los eventos en logs. 📜

## Requisitos ⚙️

- **JDK** 11 o superior. ☕ ([Documentación de JDK](https://docs.oracle.com/en/java/javase/11/))
- **Grails** 5.1.0 o superior. 🌐 ([Documentación de Grails](https://docs.grails.org/5.1.0/))
- Base de datos **H2** (local) configurada por defecto. 🗄️
- Conexión a la API de Handy con un **token de autenticación**. 🔑

## Instalación 🛠️

### 1. Clonar el repositorio

```bash
git clone https://github.com/tu-usuario/SincronizacionPedidosAPI.git
cd SincronizacionPedidosAPI
