/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.umg.negocio.controller;

import com.umg.negocio.service.TareaService;
import com.umg.negocio.service.HistorialService;
import com.umg.persistencia.entidades.Tarea;
import com.umg.persistencia.entidades.HistorialAccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/tareas")
@Tag(name = "Tareas", description = "API para la gestión de tareas")
public class TareaController {
    private final TareaService tareaService;
    private final HistorialService historialService; // Para deshacer acciones

    
    @Autowired
    public TareaController(TareaService tareaService, HistorialService historialService) {
        this.tareaService = tareaService;
        this.historialService = historialService;
    }
    
    @Operation(summary = "Crear una nueva tarea para un usuario",
               description = "Permite a un usuario registrar una nueva tarea en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tarea creada exitosamente",
                    content = @Content(schema = @Schema(implementation = Tarea.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "404", description = "Usuario, estado o prioridad no encontrados")
    })
    
    @PostMapping
    public ResponseEntity<Tarea> crearTarea(
            @RequestBody Tarea tarea,
            @Parameter(description = "ID del usuario que crea la tarea") @RequestHeader("X-User-Id") Long userId) {
        try {
            Tarea nuevaTarea = tareaService.crearTarea(tarea, userId);
            return new ResponseEntity<>(nuevaTarea, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build(); // Manejo de errores básico
        }
    }
    
    @Operation(summary = "Obtener todas las tareas de un usuario",
               description = "Devuelve una lista de todas las tareas asociadas a un usuario específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tareas obtenida",
                    content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping
    public ResponseEntity<List<Tarea>> getTareasPorUsuario(
            @Parameter(description = "ID del usuario para quien se buscan las tareas") @RequestHeader("X-User-Id") Long userId) {
        try {
            List<Tarea> tareas = tareaService.getTareasPorUsuario(userId);
            return ResponseEntity.ok(tareas);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
     @Operation(summary = "Obtener una tarea por su ID",
               description = "Devuelve los detalles de una tarea específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarea encontrada",
                    content = @Content(schema = @Schema(implementation = Tarea.class))),
            @ApiResponse(responseCode = "404", description = "Tarea no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Tarea> getTareaById(
            @Parameter(description = "ID de la tarea a buscar") @PathVariable Long id) {
        Optional<Tarea> tarea = tareaService.getTareaById(id);
        return tarea.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "Actualizar una tarea existente",
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
    
    @Operation(summary = "Eliminar una tarea por su ID",
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
            tareaService.eliminarTarea(id, userId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            if (e.getMessage().contains("No autorizado")) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            return ResponseEntity.notFound().build();
        }
    }
    
    @Operation(summary = "Deshacer la última acción deshacible de un usuario",
               description = "Revierte la última acción que ha sido marcada como deshacible.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Acción deshecha exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado o no hay acciones deshacibles")
    })
    @PostMapping("/undo")
    public ResponseEntity<String> deshacerUltimaAccion(
            @Parameter(description = "ID del usuario cuya acción se va a deshacer") @RequestHeader("X-User-Id") Long userId) {
        try {
           
            boolean deshecha = historialService.deshacerUltimaAccion(
                
                null 
            );
            if (deshecha) {
                return ResponseEntity.ok("Última acción deshecha exitosamente.");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error al deshacer: " + e.getMessage());
        }
    }
    
     
    @Operation(summary = "Obtener tareas principales (sin subtareas) de un usuario",
               description = "Devuelve solo las tareas que no son subtareas de otra.")
    @GetMapping("/principales")
    public ResponseEntity<List<Tarea>> getTareasPrincipales(
            @Parameter(description = "ID del usuario") @RequestHeader("X-User-Id") Long userId) {
        try {
            List<Tarea> tareas = tareaService.getTareasPrincipalesPorUsuario(userId);
            return ResponseEntity.ok(tareas);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
