/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.umg.negocio.controller.dto;

/**
 *
 * @author Oscar
 */
public class EstadoTareaResponseDTO {
    private long id;
    private String nombre;
    
    public EstadoTareaResponseDTO() {}
    

  public EstadoTareaResponseDTO(com.umg.persistencia.entidades.EstadoTarea estadoTarea) {
        this.id = estadoTarea.getId();
        this.nombre = estadoTarea.getNombre();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
  
  
}
