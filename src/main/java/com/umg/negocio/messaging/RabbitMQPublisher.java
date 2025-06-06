/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.umg.negocio.messaging;

import com.umg.negocio.config.RabbitMQConfig;
import com.umg.persistencia.entidades.Tarea;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class RabbitMQPublisher {
     private final RabbitTemplate rabbitTemplate;
    
@Autowired
public RabbitMQPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

 public void enviarTareaCreada(Tarea tarea) {
        System.out.println("Enviando mensaje de tarea creada: " + tarea.getId());
        
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_TAREA_CREADA, "Tarea creada con ID: " + tarea.getId() + " - " + tarea.getTitulo());
    }
 
public void enviarTareaFinalizada(Tarea tarea) {
        System.out.println("Enviando mensaje de tarea finalizada: " + tarea.getId());
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_TAREA_FINALIZADA, "Tarea finalizada con ID: " + tarea.getId() + " - " + tarea.getTitulo());
    }
}
