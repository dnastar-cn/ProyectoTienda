package com.xander.tienda.figuras.servicios;

import com.xander.tienda.figuras.model.Figura;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

public interface ServicioFiguras {

     void registrarFigura(Figura v);

     List<Figura> obtenerFiguras();

     Figura obtenerFiguraPorId(long id);

     void borrarFigura(long id) throws Exception;

     void guardarCambiosFigura(@Valid Figura figuraEditar) throws Exception;

     List<Map<String, Object>> obtenerJSONfiguras();

     Map<String,Object> obtenerJSONFiguraPorId(Long id);
}
