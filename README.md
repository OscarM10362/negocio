# 📘 Proyecto de Gestión de Tareas con Spring Boot

Este proyecto consiste en una aplicación de backend para la gestión de tareas, integrando estructuras de datos, servicios RESTful y mensajería con RabbitMQ.

---

## 🛠️ Tecnologías utilizadas

- Java 17+  
- Spring Boot  
- Maven  
- MySQL  
- RabbitMQ  
- Swagger/OpenAPI  
- Estructuras de datos: Lista, Árbol, Pila, Cola

---

## 👨‍💻 Desarrollador

**Nombre:** _[OSCAR MONROY]_  
**Carné:** _[5190-17-10362]_  
**Curso:** _[PROGRAMACION III]_  

---

## 🗓️ Plan de trabajo por semana

### 📌 Semana 1 – Inicio y configuración

- [ ] Crear proyecto Maven con Spring Boot.
- [ ] Agregar dependencias: Web, JPA, MySQL, AMQP.
- [ ] Configurar conexión a MySQL.
- [ ] Instalar RabbitMQ.
- [ ] Modelar entidades:
Usuario
Tarea
Historial
![image](https://github.com/user-attachments/assets/f778c9f0-073f-4c0e-8fcb-30907a8ab1d3)
![image](https://github.com/user-attachments/assets/4fbce067-401a-454c-8920-6b26a8a31c07)
![image](https://github.com/user-attachments/assets/1ef45976-a92d-4d9f-9049-50593e93e815)

### 📌 Semana 2 – Estructuras de datos
- [ ]  Lista: manejo de tareas por usuario.
- [ ]  Árbol: estructura para subtareas.
- [ ]  Pila: acciones recientes para deshacer.
- [ ]  Cola: tareas programadas por fecha.
- [ ]  Servicios para manipular estas estructuras.
  ![image](https://github.com/user-attachments/assets/a79dc7ea-446d-4244-abce-0f4bf8fb0fc7)

### 📌 Semana 3 – APIs y eventos
- [ ]   POST /tarea – Crear tarea
- [ ]   PUT /tarea/{id} – Editar tarea
- [ ]   DELETE /tarea/{id} – Eliminar tarea
- [ ]   GET /tareas – Listar tareas
- [ ]   RabbitMQ:

![image](https://github.com/user-attachments/assets/bf876f66-5ec5-418f-a332-1907abf1ad54)
![image](https://github.com/user-attachments/assets/1b0592e3-a0b6-4a84-80dc-9276824b7f82)

![image](https://github.com/user-attachments/assets/fa2fc442-05ef-442c-9eed-3d23747aadfe)

📦 Estructura del proyecto
![image](https://github.com/user-attachments/assets/0768fc23-1c78-446f-97ee-e27a89e6c96d)
📦 negocio/ (Módulo principal)
├── src/
│ ├── main/
│ │ ├── java/com/umg/negocio/
│ │ │ ├── controller/
│ │ │ ├── controller/dto/
│ │ │ ├── service/
│ │ │ ├── messaging/
│ │ │ └── config/
│ │ └── resources/
│ │ └── application.properties
│ └── test/
├── pom.xml

📦 estructuradatos/ (Dependencia)
├── src/
│ ├── main/
│ │ └── java/com/umg/estructuradatos/
│ └── test/
├── pom.xml

📦 persistencia/ (Dependencia)
├── src/
│ ├── main/
│ │ └── java/com/umg/persistencia/
│ │ ├── entidades/
│ │ └── repository/
│ └── test/
├── pom.xml




