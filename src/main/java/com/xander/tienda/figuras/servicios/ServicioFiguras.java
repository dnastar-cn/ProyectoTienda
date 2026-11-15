package com.xander.tienda.figuras.servicios;

import com.xander.tienda.figuras.model.Figura;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Map;

public interface ServicioFiguras {

     void registrarFigura(Figura v);

     List<Figura> obtenerFiguras();

     List<Figura> obtenerFigurasPorTitulo(String titulo);

     List<Figura> obtenerFigurasPorTituloYcomienzoYfin(String titulo,int comienzo, int resultadoPorPagina);

     Figura obtenerFiguraPorId(long id);

     void borrarFigura(long id) throws Exception;

     void guardarCambiosFigura(@Valid Figura figuraEditar) throws Exception;

     List<Map<String, Object>> obtenerJSONfiguras(String titulo, int comienzo);

     Map<String,Object> obtenerJSONFiguraPorId(Long id);

     int obtenerTotalFiguras(String titulo);
}
