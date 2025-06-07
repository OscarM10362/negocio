### 🧠 **Descripción General**

Tu reto será desarrollar un **sistema de gestión de tareas** (como Trello, Todoist o Google Tasks) desde cero, enfocado exclusivamente en la lógica del backend. Cada usuario podrá crear tareas, organizarlas por prioridad, estado o categoría, marcar tareas como completadas, y consultar un historial de actividades.

Este proyecto te permitirá aplicar **estructuras de datos avanzadas**, como **pilas para deshacer acciones**, **colas para tareas programadas**, **árboles para jerarquizar tareas** y **listas para gestión general**. Además, integrarás una base de datos relacional (**MySQL**), mensajería asíncrona con **RabbitMQ** y APIs REST documentadas con **Swagger**.

---

### 🛠️ **Tecnologías obligatorias**

- Java + Maven
- Spring Boot  | Quarkus
- MySQL, MongoDB
- RabbitMQ (Docker)
- Swagger para documentación

> Opcional: MongoDB para guardar logs o registros históricos.
> 

---

### 🧩 **Retos técnicos y estructuras de datos**

- 📋 **Lista**: para gestionar todas las tareas de un usuario.
- 📥 **Pila**: para registrar acciones y permitir *deshacer* (por ejemplo, eliminar tarea).
- 🔁 **Cola**: para tareas programadas que deben ejecutarse más adelante.
- 🌳 **Árbol**: para tareas jerárquicas o tareas padres con subtareas.

---

### 🎯 **Objetivo del sistema**

- Crear, editar, eliminar y marcar tareas como completadas.
- Clasificar tareas por estado, prioridad o tipo.
- Jerarquizar tareas en subniveles (usando árboles).
- Registrar historial de acciones con posibilidad de deshacer.
- Exponer todas las funciones como APIs REST.
- Integrar eventos con RabbitMQ (ej. cuando se crea o finaliza una tarea).
- (Opcional) Guardar los eventos o logs de atención en MongoDB.

---

### 📦 **Entregables**

- Repositorio en GitHub con código limpio y documentado.
- APIs funcionales y probadas con Postman o Swagger.
- Documento README explicando las estructuras y funcionamiento.
- (Opcional) Video corto explicando el flujo del sistema.
