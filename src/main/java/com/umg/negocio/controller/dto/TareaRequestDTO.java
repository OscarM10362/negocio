/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.umg.negocio.controller.dto;

import java.time.LocalDateTime;

public class TareaRequestDTO {
    private Long id; 
    private String titulo;
    private String descripcion;
    private Long estadoId;     // Solo el ID de la entidad relacionada
    private Long prioridadId;  // Solo el ID de la entidad relacionada
    private LocalDateTime fechaVencimiento;
    private boolean completada;  
    
     public TareaRequestDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(Long estadoId) {
        this.estadoId = estadoId;
    }

    public Long getPrioridadId() {
        return prioridadId;
    }

    public void setPrioridadId(Long prioridadId) {
        this.prioridadId = prioridadId;
    }

    public LocalDateTime getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDateTime fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public boolean isCompletada() {
        return completada;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }
     
}
