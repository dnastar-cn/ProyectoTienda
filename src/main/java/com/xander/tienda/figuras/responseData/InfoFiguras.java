package com.xander.tienda.figuras.responseData;

import java.util.List;
import java.util.Map;

//tipo de dato para devolver los videojuegos y su total para el js mostrar_videojuegos
public class InfoFiguras {
    private List<Map<String,Object>> figuras;
    private int totalFiguras;

    public List<Map<String, Object>> getFiguras() {
        return figuras;
    }

    public void setFiguras(List<Map<String, Object>> figuras) {
        this.figuras = figuras;
    }

    public int getTotalFiguras() {
        return totalFiguras;
    }

    public void setTotalFiguras(int totalFiguras) {
        this.totalFiguras = totalFiguras;
    }
}
