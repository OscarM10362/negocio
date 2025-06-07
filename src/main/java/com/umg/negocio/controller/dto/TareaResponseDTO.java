/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.umg.negocio.controller.dto;

import java.time.LocalDateTime;


public class TareaResponseDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private UsuarioResponseDTO usuario; // DTO del usuario
    private EstadoTareaResponseDTO estado; // DTO del estado
    private PrioridadTareaResponseDTO prioridad; // DTO de la prioridad
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaVencimiento;
    private boolean completada;
    
     public TareaResponseDTO() {}
     
      public TareaResponseDTO(com.umg.persistencia.entidades.Tarea tarea) {
        this.id = tarea.getId();
        this.titulo = tarea.getTitulo();
        this.descripcion = tarea.getDescripcion();
        // Mapea las entidades relacionadas a sus respectivos DTOs
        this.usuario = new UsuarioResponseDTO(tarea.getUsuario());
        this.estado = new EstadoTareaResponseDTO(tarea.getEstado());
        this.prioridad = new PrioridadTareaResponseDTO(tarea.getPrioridad());
        this.fechaCreacion = tarea.getFechaCreacion();
        this.fechaVencimiento = tarea.getFechaVencimiento();
        
    }

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

    public UsuarioResponseDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioResponseDTO usuario) {
        this.usuario = usuario;
    }

    public EstadoTareaResponseDTO getEstado() {
        return estado;
    }

    public void setEstado(EstadoTareaResponseDTO estado) {
        this.estado = estado;
    }

    public PrioridadTareaResponseDTO getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(PrioridadTareaResponseDTO prioridad) {
        this.prioridad = prioridad;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
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
