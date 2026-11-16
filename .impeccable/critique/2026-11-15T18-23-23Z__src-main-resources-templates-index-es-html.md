---
target: tienda y admin (index_es.html)
total_score: 22
max_score: 40
na_heuristics: 
p0_count: 0
p1_count: 2
target_identity: "file:C:\\Users\\Alexander\\Desktop\\tienda.figuras\\tienda.figuras\\src\\main\\resources\\templates\\index_es.html"
target_fingerprint: "sha256:28cac4bc228cf4867d41342058d16db7199e881910a65aff2b222f6da9f20bf3"
target_path: "C:\\Users\\Alexander\\Desktop\\tienda.figuras\\tienda.figuras\\src\\main\\resources\\templates\\index_es.html"
timestamp: 2026-11-15T18-23-23Z
slug: src-main-resources-templates-index-es-html
---
Method: dual-agent (A: revisión de diseño · B: detector y navegador)

# Crítica: tienda + admin tras el rediseño completo

## Heurísticas de Nielsen
| # | Heurística | Nota | Problema |
|---|---|---|---|
| 1 | Visibilidad del estado | 2 | Carrito sin contador; feedback por alert |
| 2 | Lenguaje del usuario | 2 | "34.5 €", "EnProceso", alerts con texto de programador |
| 3 | Control y libertad | 2 | Sin "Atrás" en el pedido; "Borrar producto" no funciona (heredado) |
| 4 | Coherencia | 3 | Sistema compartido; falta estado activo en los menús |
| 5 | Prevención de errores | 2 | Sin required; altura 0 por defecto en nueva figura |
| 6 | Reconocer vs recordar | 2 | Sin total en el carrito ni en el resumen |
| 7 | Eficiencia | 2 | Sin filtro por franquicia (110 figuras) |
| 8 | Estética y minimalismo | 3 | Ordenado; 4 controles por tarjeta; cabecera grande en móvil |
| 9 | Recuperación de errores | 1 | Todo con alert |
| 10 | Ayuda | 1 | Sin info de envío; login sin enlace a registro |
| Total | | 22/40 | Aceptable |

## Especificidad
Parcial: vitrina, fotos oficiales y ficha (fabricante/altura/material) son propias; el fondo crema, la tipografía y el botón azul son genéricos. Los fondos mezclados de las fotos rompen la vitrina.
Detector: CLI con 5 avisos en el admin, todos falsos positivos (th:src, clases de Tailwind). Navegador: el placeholder de los buscadores tiene contraste 3,3:1 (real). Resto de contrastes OK; sin desbordamiento.

## Lo que funciona
1. Sistema de diseño centralizado y compartido.
2. Detalle y admin bien compuestos.
3. Accesibilidad básica presente.

## Problemas prioritarios
- [P1] Sin totales en la compra; tarjeta sin enmascarar → clarify, layout
- [P1] Feedback por alert() con texto interno → harden
- [P2] Catálogo sin filtro por franquicia, 4 controles por tarjeta, paginación solo arriba → layout
- [P2] Vitrina irregular por los fondos de las fotos → polish/bolder
- [P2] Cabecera en móvil (40 % de pantalla, carrito se pierde) → adapt

## Personas
Casey: cabecera grande, sin acceso rápido al carrito. Jordan: aviso al añadir sin sesión, login sin registro, final en alert. Sam: avisos no ligados a campos, sin aria-current, foco no se mueve. Coleccionista: datos solo en el detalle; "34.5 €".

## Menores
Resumen en minúsculas y sin tildes; botones "Aceptar" genéricos; etiquetas de estado del mismo color; descripción de una línea; .imagen-producto sin uso.

## Preguntas
1. ¿Una estantería donde mirar va antes que comprar? 2. ¿Organizar por franquicia? 3. ¿Qué momento recuerda el coleccionista tras confirmar?
