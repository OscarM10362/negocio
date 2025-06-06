/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.umg.negocio.service;

import com.umg.persistencia.entidades.HistorialAccion;
import com.umg.persistencia.entidades.Usuario;
import com.umg.persistencia.repository.HistorialAccionRepository;
import com.umg.estructuradatos.Pila; //  pila personalizada
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

@Service
public class HistorialService {
    private final HistorialAccionRepository historialAccionRepository;
    
    @Autowired
    public HistorialService(HistorialAccionRepository historialAccionRepository) {
        this.historialAccionRepository = historialAccionRepository;
    }
    
    public HistorialAccion registrarAccion(Usuario usuario, Long tareaId, String tipoAccion, String detalles, boolean undoable) {
        HistorialAccion accion = new HistorialAccion();
        accion.setUsuario(usuario);
        accion.setTipoAccion(tipoAccion);
        accion.setDetalles(detalles);
        accion.setUndoable(undoable);
        return historialAccionRepository.save(accion);
}
    public Optional<HistorialAccion> getUltimaAccionDeshacible(Usuario usuario) {
        return historialAccionRepository.findFirstByUsuarioAndUndoableOrderByFechaAccionDesc(usuario, true);
    }
    
     public boolean deshacerUltimaAccion(Usuario usuario) {
        Optional<HistorialAccion> ultimaAccionOpt = getUltimaAccionDeshacible(usuario);
        if (ultimaAccionOpt.isPresent()) {
            HistorialAccion accionADeshacer = ultimaAccionOpt.get();
            
            accionADeshacer.setUndoable(false);
            historialAccionRepository.save(accionADeshacer);
            return true;
        }
        return false;
    }
}
