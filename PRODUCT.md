# Product

<!-- impeccable:product-schema 1 -->

## Platform

web

## Users

Dos tipos de visitante, los dos importantes:

- **Fans en general**: público joven o casual que busca una figura de su personaje favorito o un regalo. Quieren encontrarla rápido y que comprar sea fácil.
- **Coleccionistas**: gente que conoce bien el anime y los cómics, compara precios y detalles, y compra figuras para exponer. Valoran la información clara de cada figura.

La tienda es imaginaria: es un proyecto de clase.

## Product Purpose

Tienda online de figuras coleccionables de anime y cómic. El visitante puede ver el catálogo (con buscador y paginación), consultar los detalles de cada figura, registrarse (con avatar), identificarse, añadir figuras a su carrito y hacer un pedido en 4 pasos (envío, pago, método de envío y resumen). Un administrador gestiona figuras, categorías, usuarios y pedidos (incluido su estado) desde un panel aparte.

Es un proyecto de clase para **aprender**: el éxito es que la web funcione y, sobre todo, que su diseño esté bien organizado (estilos definidos en un solo sitio y reutilizables).

## Operating Context

- Parte pública: una sola página por idioma (`templates/index_es.html`, `index_en.html`, `index_ja.html`) que carga plantillas Mustache (`static/plantillas_html_es|en|ja/`) con jQuery desde `static/javascript/aplicacion.js`: listado, detalle, registro, identificación, carrito y los 4 pasos del pedido.
- Panel de administración: páginas Thymeleaf en `templates/admin/` (inicio, login, figuras, categorías, usuarios y pedidos), con la cabecera común en `templates/fragments/general.html`.
- Estilos en un solo sitio: `templates/fragments/estilos.html` (tokens `@theme` y componentes `@layer components`).
- Se usa en el navegador de escritorio en clase (`http://localhost:8080`), y debe verse bien también en el móvil.

## Capabilities and Constraints

- Categorías actuales: Pokémon, Dragon Ball, Marvel, One Piece. Al crear la base de datos se cargan 10 figuras de ejemplo.
- Datos de cada figura: título, precio, categoría, descripción, SKU, imagen de portada, **fabricante** (por ejemplo Bandai S.H.Figuarts, First 4 Figures, Pokémon Center), **altura** (cm) y **material**. Son datos que valora el coleccionista.
- Datos del pedido: nombre, dirección, provincia, localidad, código postal y teléfono; tipo, número, titular y caducidad de la tarjeta; y método de envío (estándar, urgente o recogida en tienda).
- Pendiente (necesita Java o JavaScript, se verá en clase): "Mis pedidos", "Mis datos", borrar o cambiar cantidades en el carrito, total del carrito y del pedido, enmascarar el número de tarjeta y sustituir los `alert()` por avisos en la página.
- Stack: Java 17, Spring Boot, JPA, H2, Thymeleaf (admin), jQuery + Mustache (parte pública), Tailwind 4 por CDN.
- Los rediseños solo cambian el aspecto: no se toca la lógica de Java ni el JavaScript (registro, login, carrito).
- Los errores heredados del código del profe no se corrigen salvo que se pida.

## Brand Commitments

- **Nombre de la tienda: "Tienda de Figuras"** (decidido por el usuario; no se traduce).
- Idiomas de la web: español (principal), inglés y japonés.
- Dirección visual elegida: "vitrina de coleccionista" — fondo neutro cálido, paneles blancos, la figura como protagonista; azul para las acciones y amarillo como acento.

## Evidence on Hand

- Fotos oficiales de producto en `static/imagenes_productos_iniciales/` (`1.jpg` a `10.jpg`, 800×800): Tamashii Nations / Bandai S.H.Figuarts, Pokémon Center, First 4 Figures y Jazwares. Unas tienen fondo blanco y otras el fondo con escenario de la foto oficial.
- No hay opiniones de clientes, valoraciones, cifras de ventas ni logotipo. No inventarlos.

## Product Principles

1. **Encontrar la figura rápido**: el catálogo y la categoría son lo primero, para el fan y para el coleccionista.
2. **La figura es la protagonista**: imagen, nombre y precio claros; el detalle da la información que el coleccionista busca.
3. **Comprar sin dudas**: registrarse, identificarse y añadir al carrito debe entenderse a la primera.
4. **Diseño organizado y reutilizable**: colores, tipografía y componentes se definen en un solo sitio, porque el proyecto sirve para aprender a hacerlo bien.
