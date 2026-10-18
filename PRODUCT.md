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

Tienda online de figuras coleccionables de anime y cómic. El visitante puede ver el catálogo, consultar los detalles de cada figura, registrarse, identificarse y añadir figuras a su carrito. Un administrador gestiona productos y usuarios desde un panel aparte.

Es un proyecto de clase para **aprender**: el éxito es que la web funcione y, sobre todo, que su diseño esté bien organizado (estilos definidos en un solo sitio y reutilizables).

## Operating Context

- Parte pública: una sola página (`templates/index.html`) que carga plantillas Mustache (`static/plantillas_html/`) con jQuery: inicio, listado, detalle, registro e identificación.
- Panel de administración: páginas Thymeleaf en `templates/admin/` (inicio, login, gestionar productos y usuarios, alta y edición de productos).
- Se usa en el navegador de escritorio en clase (`http://localhost:8080`).

## Capabilities and Constraints

- Categorías actuales: Pokémon, Dragon Ball, Marvel, One Piece. Al crear la base de datos se cargan 10 figuras de ejemplo.
- Datos de cada figura: título, precio, categoría, descripción, SKU, imagen de portada y un campo `plataforma`.
- **Pendiente:** el campo `plataforma` viene del proyecto original (videojuegos) y se va a renombrar a otro concepto que aún no está decidido. No darle un significado en el diseño hasta entonces.
- Stack: Java 17, Spring Boot, JPA, H2, Thymeleaf (admin), jQuery + Mustache (parte pública), Tailwind 4 por CDN.
- Los rediseños solo cambian el aspecto: no se toca la lógica de Java ni el JavaScript (registro, login, carrito).
- Los errores heredados del código del profe no se corrigen salvo que se pida.

## Brand Commitments

- **Nombre de la tienda: pendiente.** La pestaña dice "Store Enlinea" de forma provisional; no inventar un nombre.
- Idioma de la web: español.

## Evidence on Hand

- Imágenes provisionales en `static/imagenes_productos_iniciales/` (`1.jpg` a `10.jpg`): cuadrados de color con el nombre, no fotos reales.
- No hay opiniones de clientes, valoraciones, cifras de ventas ni logotipo. No inventarlos.

## Product Principles

1. **Encontrar la figura rápido**: el catálogo y la categoría son lo primero, para el fan y para el coleccionista.
2. **La figura es la protagonista**: imagen, nombre y precio claros; el detalle da la información que el coleccionista busca.
3. **Comprar sin dudas**: registrarse, identificarse y añadir al carrito debe entenderse a la primera.
4. **Diseño organizado y reutilizable**: colores, tipografía y componentes se definen en un solo sitio, porque el proyecto sirve para aprender a hacerlo bien.
