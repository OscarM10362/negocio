/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.umg.negocio.controller;

import com.umg.negocio.service.TareaService;
import com.umg.negocio.service.HistorialService;
import com.umg.negocio.controller.dto.TareaRequestDTO;   
import com.umg.negocio.controller.dto.TareaResponseDTO;  
import com.umg.persistencia.entidades.Tarea;             
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors; 
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Optional;

@RestController
@RequestMapping("/api/tareas")
@Tag(name = "Tareas", description = "API para la gestión de tareas")
public class TareaController {
    private final TareaService tareaService;
    private final HistorialService historialService; 

    
    @Autowired
    public TareaController(TareaService tareaService, HistorialService historialService) {
        this.tareaService = tareaService;
        this.historialService = historialService;
    }
    
    @Operation(summary = "Crear una nueva tarea para un usuario",
               description = "Permite a un usuario registrar una nueva tarea en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tarea creada exitosamente",
                    content = @Content(schema = @Schema(implementation = TareaResponseDTO.class))), // Cambiado a DTO
            @ApiResponse(responseCode = "400", description = "Solicitud inválida (ej. datos faltantes, ID de usuario/estado/prioridad no encontrado)")
    })
    @PostMapping
    public ResponseEntity<TareaResponseDTO> crearTarea( 
            @RequestBody TareaRequestDTO tareaDto, 
            @Parameter(description = "ID del usuario que crea la tarea") @RequestHeader("X-User-Id") Long userId) {
        try {
           
            Tarea nuevaTarea = tareaService.crearTarea(userId, tareaDto); 
            return new ResponseEntity<>(new TareaResponseDTO(nuevaTarea), HttpStatus.CREATED); 
        } catch (RuntimeException e) {
            System.err.println("Error al crear tarea: " + e.getMessage());
           
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Obtener todas las tareas de un usuario",
               description = "Devuelve una lista de todas las tareas asociadas a un usuario específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tareas obtenida",
                    content = @Content(schema = @Schema(implementation = TareaResponseDTO.class))), 
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping
    public ResponseEntity<List<TareaResponseDTO>> getTareasPorUsuario( 
            @Parameter(description = "ID del usuario para quien se buscan las tareas") @RequestHeader("X-User-Id") Long userId) {
        try {
            List<Tarea> tareas = tareaService.getTareasByUserId(userId); 
            // Mapear cada Tarea entidad a TareaResponseDTO
            List<TareaResponseDTO> tareaDTOs = tareas.stream()
                                                     .map(TareaResponseDTO::new) 
                                                     .collect(Collectors.toList());
            return ResponseEntity.ok(tareaDTOs);
        } catch (RuntimeException e) {
            System.err.println("Error al obtener tareas por usuario: " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
    
   
    
    @Operation(summary = "Editar Tarea",
               description = "Modifica los detalles de una tarea específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarea actualizada exitosamente",
                    content = @Content(schema = @Schema(implementation = Tarea.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado (no eres el propietario)"),
            @ApiResponse(responseCode = "404", description = "Tarea, usuario, estado o prioridad no encontrados")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Tarea> actualizarTarea(
            @Parameter(description = "ID de la tarea a actualizar") @PathVariable Long id,
            @RequestBody Tarea tareaDetails,
            @Parameter(description = "ID del usuario que actualiza la tarea") @RequestHeader("X-User-Id") Long userId) {
        try {
            Tarea tareaActualizada = tareaService.actualizarTarea(id, tareaDetails, userId);
            return ResponseEntity.ok(tareaActualizada);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("No autorizado")) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            return ResponseEntity.badRequest().build();
        }
    }
    
    @Operation(summary = "Eliminar una tarea ",
               description = "Elimina una tarea específica del sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tarea eliminada exitosamente"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado (no eres el propietario)"),
            @ApiResponse(responseCode = "404", description = "Tarea no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTarea(
            @Parameter(description = "ID de la tarea a eliminar") @PathVariable Long id,
            @Parameter(description = "ID del usuario que elimina la tarea") @RequestHeader("X-User-Id") Long userId) {
        try {
            boolean eliminado = tareaService.eliminarTarea(userId, id); 
            if (eliminado) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            System.err.println("Error al eliminar tarea: " + e.getMessage());
            if (e.getMessage().contains("No autorizado")) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            } else if (e.getMessage().contains("no encontrada")) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.badRequest().build();
        }
    }
    
 
}
