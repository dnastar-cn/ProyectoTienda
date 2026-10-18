# Tienda de figuras — proyecto de clase

## Lo más importante: estoy aprendiendo

Este es un proyecto de clase y mi objetivo es **aprender**, no solo que funcione.

- Explícame en **español**, paso a paso, con palabras sencillas.
- Antes de hacer un cambio grande, dime **qué vas a hacer y por qué**.
- Haz los cambios **poco a poco**, para que pueda ver el resultado de cada paso en el navegador.
- Cuando uses algo nuevo (una clase de Tailwind, una propiedad CSS, un patrón), explica brevemente qué hace.
- Si hay varias formas de hacer algo, dime cuál recomiendas y por qué. No me des una lista larga.
- De vez en cuando, propón que yo haga una parte para practicar.

## Qué quiero aprender sobre diseño

Quiero aprender a **organizar bien el diseño de una web**, no solo a que quede bonita:

- **No editar cosa por cosa en cada HTML.** Los colores, las fuentes y los componentes que se repiten (botones, tarjetas de producto, inputs) deben definirse **en un solo sitio**, para que un cambio ahí se aplique a toda la web.
- La organización acordada:
  - **Tailwind en el HTML** solo para lo puntual: márgenes, alineación, tamaños concretos.
  - **Clases propias reutilizables** (`.btn`, `.tarjeta`, `.input`…) definidas con `@apply` en un fragmento compartido.
  - **Variables de tema** (`@theme`) para colores y tipografía.
  - **Nada de `style="..."`** escrito en el HTML.
- El fragmento compartido se crea en `templates/fragments/estilos.html` y se incluye en el `<head>` de cada página con `th:insert`. Las plantillas de Mustache no lo necesitan, porque se insertan dentro de `index.html`.
- Cuando reorganices o rediseñes algo, explícame **la estructura** (dónde va cada cosa y por qué), no solo el resultado.
- Tengo instaladas las skills **impeccable** (diseño general, tipografía, orden) y **emil-design-eng** (animaciones y detalles finos). Orden previsto: `/impeccable init` → `critique` → `extract` → `typeset` → `layout` → `polish`. Después, Emil para las animaciones.

## Reglas del proyecto

- Está basado en el proyecto del profe (`Descargas\tienda.online`, tienda de videojuegos), adaptado a figuras. Lo vamos avanzando en clase semana a semana.
- **No corregir los errores heredados del código del profe** (por ejemplo, el carrito falla con usuarios recién registrados, o el login acepta datos incorrectos), salvo que yo lo pida. Los veremos en clase.
- **Al rediseñar, cambiar solo el aspecto.** No tocar la lógica de Java ni el JavaScript (registro, login, carrito), para no romper lo que explique el profe después.
- Las imágenes de `static/imagenes_productos_iniciales/` (de `1.jpg` a `10.jpg`) son **provisionales**: cuadrados de color con el nombre. Las fotos solo se cargan al crear la base de datos. Para cambiarlas, hay que borrar `data/` y volver a arrancar.
- Antes de un cambio grande, recuérdame hacer **commit** en Git.

## Datos técnicos

- Java 17, Spring Boot, JPA, base de datos H2 (`data/base_de_datos`), Thymeleaf para el admin, jQuery + Mustache para la parte pública (una sola página, `index.html`), Tailwind 4 por CDN.
- Paquete: `com.xander.tienda.figuras`.
- Arranco la app desde **IntelliJ** (`Application.java`) → `http://localhost:8080`.
- Admin: `http://localhost:8080/admin/inicioAdmin` (contraseña `123`). Usuario de prueba: `pepe@gmail.com` / `123`.
- Al abrir la web por primera vez, `ServicioSetUpImplJPA` crea 4 categorías (Pokémon, Dragon Ball, Marvel, One Piece), 10 figuras, el usuario Pepe y su carrito.
- Repositorio: `https://github.com/dnastar-cn/ProyectoTienda` (rama `master`).
