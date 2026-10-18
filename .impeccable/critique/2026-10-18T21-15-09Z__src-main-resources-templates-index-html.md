---
target: tienda pública (index.html)
total_score: 12
max_score: 40
na_heuristics: 
p0_count: 0
p1_count: 3
target_identity: "file:C:\\Users\\Alexander\\Desktop\\tienda.figuras\\tienda.figuras\\src\\main\\resources\\templates\\index.html"
target_fingerprint: "sha256:5ce5862697b73785353d501adb4c9ca8bb43e9fbfac099b0b155cf04a83971db"
target_path: "C:\\Users\\Alexander\\Desktop\\tienda.figuras\\tienda.figuras\\src\\main\\resources\\templates\\index.html"
timestamp: 2026-10-18T21-15-09Z
slug: src-main-resources-templates-index-html
---
Method: dual-agent (A: revisión de diseño · B: detector y navegador)

# Crítica: tienda pública (index.html + plantillas Mustache)

Aviso previo: el navegador servía una copia antigua de estilos.html desde target/ (.btn sin text-white → texto negro sobre #2529DC, 2.4:1). Solución: Build → Rebuild Project y reiniciar. Con text-white ≈ 8.6:1.

## Heurísticas de Nielsen
| # | Heurística | Nota | Problema principal |
|---|---|---|---|
| 1 | Visibilidad del estado | 1 | Login no cambia nada visible; sin sección activa; solo alert() |
| 2 | Lenguaje del usuario | 1 | "plataforma: Anime"; precios sin € y formato variable; "pass:", "yepa" |
| 3 | Control y libertad | 1 | Sin "volver" en detalle; Atrás sale de la SPA |
| 4 | Coherencia | 2 | Acciones como enlaces y botones; "agregar al carrito"/"agregar carrito" |
| 5 | Prevención de errores | 1 | Cantidad acepta 0/negativos; aviso de login solo tras intentar comprar |
| 6 | Reconocer vs recordar | 2 | Menú visible, pero sin filtro de categoría ni sección activa |
| 7 | Eficiencia | 1 | Sin búsqueda, filtros ni orden |
| 8 | Estética y minimalismo | 1 | Sin jerarquía; enlace admin primero |
| 9 | Recuperación de errores | 1 | Formularios públicos sin mensajes de error |
| 10 | Ayuda | 1 | Ninguna orientación |
| Total | | 12/40 | Pobre (antes de la fase de diseño) |

Carga cognitiva: 6/8 fallos (foco único, agrupación en bloques, agrupación, jerarquía, opciones mínimas — menú de 7-8 —, revelación progresiva).

## Especificidad
No parece una tienda de figuras: estilos por defecto del navegador, fondo amarillo plano, etiquetas "titulo:", una figura por fila. Solo el zoom al pasar el ratón tiene intención. Detector CLI: 0 hallazgos. Navegador: body-text-viewport-edge (index.html:27, texto a 0px del borde) y low-contrast en .btn (versión antigua).

## Lo que funciona
1. Organización de estilos: tokens en @theme y componentes en estilos.html, sin style.
2. Formulario de registro bien construido (label for, tipos correctos, "(opcional)").
3. Detalle centrado con título en negrita.

## Problemas prioritarios
- [P1] Catálogo sin jerarquía (columna única, líneas planas, imagen de 140px, unos 3500px de scroll). Arreglo: cuadrícula 2/4 columnas, imagen grande, nombre como título, precio con €. → /impeccable layout, /impeccable typeset
- [P1] Navegación: 7 enlaces iguales, enlaces de cuenta visibles sin sesión, admin primero, sin cabecera ni nombre. Arreglo: cabecera, cuenta agrupada a la derecha, admin en el pie (sin tocar JS). → /impeccable layout
- [P1] Textos de programador ("titulo:", "pass:", "yepa", "adeministracion", "(por hacer...)"). Arreglo: Contraseña, Iniciar sesión, Añadir al carrito, 19,95 €. Los alert() del JS no se tocan. → /impeccable clarify
- [P2] Móvil: falta meta viewport; enlaces del menú de 22px de alto; texto pegado al borde. → /impeccable adapt
- [P2] Accesibilidad: imágenes sin alt, sin label en login y cantidad, sin h1/nav/main. → /impeccable audit

## Personas
- Casey: página diminuta en el móvil, objetivos táctiles pequeños, 3500px de scroll, alerts bloqueantes.
- Jordan: "Acceso a adeministracion" primero; no sabe qué vende la tienda; descubre el login tras intentar comprar.
- Riley: cantidad −5/999 ignorada; confeti aunque falle el registro; Atrás sale de la SPA (heredado del profe).
- Coleccionista: imagen de 140px, precio sin €, "plataforma" sin significado, no puede comparar precios.

## Detalles menores
Título de la pestaña en inglés; el zoom del 120% tapa a las tarjetas vecinas y no tiene motion-reduce; el input de archivo hereda el borde de .input; no hay color de acento.

## Preguntas
1. ¿Portada por categorías en vez de la lista completa?
2. ¿Imagen como vitrina?
3. ¿Añadir al carrito solo en el detalle?
