/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.umg.negocio.service;

import com.umg.estructuradatos.Cola; 
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@Service
public class TareaSchedulerService {
     private Cola<Long> tareasProgramadas = new Cola<>(); // Para IDs de tarea

    public void programarTareaParaProcesar(Long tareaId) {
        tareasProgramadas.enqueue(tareaId);
        System.out.println("Tarea " + tareaId + " programada para procesamiento futuro.");
    }
     @Scheduled(fixedRate = 30000) // Se ejecuta cada 30 segundos
    public void procesarTareasProgramadas() {
        if (!tareasProgramadas.isEmpty()) {
            Long tareaId = tareasProgramadas.dequeue();
            System.out.println("Procesando tarea programada: " + tareaId);
            
        }
    }
}
