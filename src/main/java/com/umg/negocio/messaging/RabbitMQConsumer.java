/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.umg.negocio.messaging;

import com.umg.negocio.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumer {
    
    @RabbitListener(queues = RabbitMQConfig.QUEUE_TAREA_CREADA)
    public void recibirTareaCreada(String message) {
        System.out.println("Mensaje recibido en Tarea Creada: " + message);
        //  guardar esto en un log de MongoDB o procesarlo
        
    }
    
    @RabbitListener(queues = RabbitMQConfig.QUEUE_TAREA_FINALIZADA)
    public void recibirTareaFinalizada(String message) {
        System.out.println("Mensaje recibido en Tarea Finalizada: " + message);
       
    }
}
