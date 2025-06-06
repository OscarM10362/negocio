/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.umg.negocio.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
     public static final String QUEUE_TAREA_CREADA = "tarea_creada_queue";
     public static final String QUEUE_TAREA_FINALIZADA = "tarea_finalizada_queue";

    @Bean
    public Queue tareaCreadaQueue() {
        return new Queue(QUEUE_TAREA_CREADA, true); 
    }
    
     @Bean
    public Queue tareaFinalizadaQueue() {
        return new Queue(QUEUE_TAREA_FINALIZADA, true); 
    }
    
}
