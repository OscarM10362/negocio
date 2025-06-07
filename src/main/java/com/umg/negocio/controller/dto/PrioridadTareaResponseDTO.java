/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.umg.negocio.controller.dto;

/**
 *
 * @author Oscar
 */
public class PrioridadTareaResponseDTO {
    private Long id;
    private String nombre;
    
    public PrioridadTareaResponseDTO() {}
    
     public PrioridadTareaResponseDTO(com.umg.persistencia.entidades.PrioridadTarea prioridadTarea) {
        this.id = (long)prioridadTarea.getId();
        this.nombre = prioridadTarea.getNombre();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
     
     
}
