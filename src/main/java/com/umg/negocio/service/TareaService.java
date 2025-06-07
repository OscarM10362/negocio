/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.umg.negocio.service;

import com.umg.persistencia.entidades.Tarea;
import com.umg.persistencia.entidades.Usuario;
import com.umg.persistencia.entidades.EstadoTarea;
import com.umg.persistencia.entidades.PrioridadTarea;
import com.umg.persistencia.repository.TareaRepository;
import com.umg.persistencia.repository.UsuarioRepository;
import com.umg.persistencia.repository.EstadoTareaRepository;
import com.umg.persistencia.repository.PrioridadTareaRepository;
import com.umg.negocio.messaging.RabbitMQPublisher; // Importa el publicador
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Para manejar transacciones
import com.umg.negocio.controller.dto.TareaRequestDTO;
import java.util.List;
import java.util.Optional;

@Service
public class TareaService {
    private final TareaRepository tareaRepository;
    private final UsuarioRepository usuarioRepository; // Para buscar usuarios por ID
    private final EstadoTareaRepository estadoTareaRepository; // Para buscar estados por ID
    private final PrioridadTareaRepository prioridadTareaRepository; // Para buscar prioridades por ID
    private final RabbitMQPublisher rabbitMQPublisher; // Inyecta el publicador
    private final HistorialService historialService; // Para registrar acciones
 
 
    @Autowired
    public TareaService(TareaRepository tareaRepository, UsuarioRepository usuarioRepository,
                        EstadoTareaRepository estadoTareaRepository, PrioridadTareaRepository prioridadTareaRepository,
                        RabbitMQPublisher rabbitMQPublisher, HistorialService historialService) {
        this.tareaRepository = tareaRepository;
        this.usuarioRepository = usuarioRepository;
        this.estadoTareaRepository = estadoTareaRepository;
        this.prioridadTareaRepository = prioridadTareaRepository;
        this.rabbitMQPublisher = rabbitMQPublisher;
        this.historialService = historialService;
    }
    
     @Transactional
    public Tarea crearTarea(Tarea tarea, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        tarea.setUsuario(usuario);

        
        if (tarea.getEstado() != null && tarea.getEstado().getId() != null) {
            EstadoTarea estado = estadoTareaRepository.findById(tarea.getEstado().getId())
                    .orElseThrow(() -> new RuntimeException("Estado de tarea no encontrado"));
            tarea.setEstado(estado);
        } else {
             
            tarea.setEstado(estadoTareaRepository.findByNombre("Pendiente")
                    .orElseThrow(() -> new RuntimeException("Estado 'Pendiente' no configurado")));
        }
        if (tarea.getPrioridad() != null && tarea.getPrioridad().getId() != null) {
            PrioridadTarea prioridad = prioridadTareaRepository.findById(tarea.getPrioridad().getId())
                    .orElseThrow(() -> new RuntimeException("Prioridad de tarea no encontrada"));
            tarea.setPrioridad(prioridad);
        } else {
            
            tarea.setPrioridad(prioridadTareaRepository.findByNombre("Media")
                    .orElseThrow(() -> new RuntimeException("Prioridad 'Media' no configurada")));
        }

        
        if (tarea.getParentTask() != null && tarea.getParentTask().getId() != null) {
            Tarea parent = tareaRepository.findById(tarea.getParentTask().getId())
                    .orElseThrow(() -> new RuntimeException("Tarea padre no encontrada"));
            tarea.setParentTask(parent);
        }

        Tarea nuevaTarea = tareaRepository.save(tarea);
        historialService.registrarAccion(usuario, nuevaTarea.getId(), "CREAR_TAREA", "Tarea '" + nuevaTarea.getTitulo() + "' creada.", true);
        rabbitMQPublisher.enviarTareaCreada(nuevaTarea);
        return nuevaTarea;
    }
    
    public List<Tarea> getTareasPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return tareaRepository.findByUsuario(usuario);
    }

    public Optional<Tarea> getTareaById(Long id) {
        return tareaRepository.findById(id);
    }

    // UPDATE
    @Transactional
    public Tarea actualizarTarea(Long id, Tarea tareaDetails, Long usuarioId) {
        Tarea tareaExistente = tareaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con ID: " + id));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

       
        if (!tareaExistente.getUsuario().getId().equals(usuarioId)) {
            throw new RuntimeException("No autorizado para actualizar esta tarea.");
        }

        tareaExistente.setTitulo(tareaDetails.getTitulo());
        tareaExistente.setDescripcion(tareaDetails.getDescripcion());
        tareaExistente.setFechaVencimiento(tareaDetails.getFechaVencimiento());

        if (tareaDetails.getCompletada() != null && !tareaExistente.getCompletada().equals(tareaDetails.getCompletada())) {
            tareaExistente.setCompletada(tareaDetails.getCompletada());
            if (tareaExistente.getCompletada()) {
                rabbitMQPublisher.enviarTareaFinalizada(tareaExistente);
                historialService.registrarAccion(usuario, tareaExistente.getId(), "COMPLETAR_TAREA", "Tarea '" + tareaExistente.getTitulo() + "' marcada como completada.", true);
            }
        }

        if (tareaDetails.getEstado() != null && tareaDetails.getEstado().getId() != null) {
            EstadoTarea estado = estadoTareaRepository.findById(tareaDetails.getEstado().getId())
                    .orElseThrow(() -> new RuntimeException("Estado de tarea no encontrado"));
            tareaExistente.setEstado(estado);
        }
        if (tareaDetails.getPrioridad() != null && tareaDetails.getPrioridad().getId() != null) {
            PrioridadTarea prioridad = prioridadTareaRepository.findById(tareaDetails.getPrioridad().getId())
                    .orElseThrow(() -> new RuntimeException("Prioridad de tarea no encontrada"));
            tareaExistente.setPrioridad(prioridad);
        }

        
        if (tareaDetails.getParentTask() != null) {
            if (tareaDetails.getParentTask().getId() != null) {
                Tarea newParent = tareaRepository.findById(tareaDetails.getParentTask().getId())
                        .orElseThrow(() -> new RuntimeException("Nueva tarea padre no encontrada"));
                tareaExistente.setParentTask(newParent);
            } else { 
                tareaExistente.setParentTask(null);
            }
        }

        Tarea tareaActualizada = tareaRepository.save(tareaExistente);
        historialService.registrarAccion(usuario, tareaActualizada.getId(), "EDITAR_TAREA", "Tarea '" + tareaActualizada.getTitulo() + "' editada.", true);
        return tareaActualizada;
    }
    
     @Transactional
    public boolean eliminarTarea(Long id, Long usuarioId) {
        Tarea tareaExistente = tareaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con ID: " + id));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!tareaExistente.getUsuario().getId().equals(usuarioId)) {
            throw new RuntimeException("No autorizado para eliminar esta tarea.");
        }

        
        historialService.registrarAccion(usuario, id, "ELIMINAR_TAREA", "Tarea '" + tareaExistente.getTitulo() + "' eliminada.", true);
        tareaRepository.delete(tareaExistente);
        return false;
    }

    public List<Tarea> getTareasPrincipalesPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return tareaRepository.findByParentTaskIsNullAndUsuario(usuario);
    }

    public Tarea crearTarea(Long userId, TareaRequestDTO tareaDto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<Tarea> getTareasByUserId(Long userId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
