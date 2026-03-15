import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Clase que gestiona el inventario de productos y la colección del usuario.
 * Utiliza implementaciones de Map proporcionadas por MapFactory.
 */
public class Inventario {

    // Map: producto -> categoría (inventario completo)
    private Map<String, String> inventario;

    // Map: producto -> cantidad (colección del usuario)
    private Map<String, Integer> coleccionUsuario;

    // Map: categoría -> lista de productos (índice por categoría)
    private Map<String, List<String>> categorias;

    private int tipoMap;

    /**
     * Constructor que inicializa los mapas usando MapFactory.
     * @param tipoMap tipo de implementación de Map (1=HashMap, 2=TreeMap, 3=LinkedHashMap)
     */
    public Inventario(int tipoMap) {
        this.tipoMap = tipoMap;
        this.inventario = MapFactory.getMap(tipoMap);
        this.coleccionUsuario = MapFactory.getMap(tipoMap);
        this.categorias = MapFactory.getMap(tipoMap);
    }

    /**
     * Carga el inventario desde un archivo de texto.
     * Formato esperado: Categoría|Producto
     * @param archivo ruta del archivo a leer
     */
    public void cargarInventario(String archivo) {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(archivo), "UTF-8"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;

                String[] partes = linea.split("\\|");
                if (partes.length == 2) {
                    String categoria = partes[0].trim();
                    String producto = partes[1].trim();

                    inventario.put(producto, categoria);

                    // Agregar al índice de categorías
                    if (!categorias.containsKey(categoria)) {
                        categorias.put(categoria, new ArrayList<>());
                    }
                    categorias.get(categoria).add(producto);
                }
            }
            System.out.println("Inventario cargado exitosamente. Total de productos: " + inventario.size());
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    /**
     * Opción 1: Agregar un producto a la colección del usuario.
     * El usuario ingresa la categoría y selecciona el producto.
     */
    public void agregarProducto(Scanner scanner) {
        System.out.println("\n===== AGREGAR PRODUCTO A LA COLECCIÓN =====");
        System.out.println("Categorías disponibles:");
        int i = 1;
        List<String> listaCategorias = new ArrayList<>(categorias.keySet());
        for (String cat : listaCategorias) {
            System.out.println("  " + i + ". " + cat);
            i++;
        }

        System.out.print("\nIngrese la categoría del producto que desea agregar: ");
        String categoriaIngresada = scanner.nextLine().trim();

        // Verificar si la categoría existe
        if (!categorias.containsKey(categoriaIngresada)) {
            System.out.println("\n*** ERROR: La categoría '" + categoriaIngresada + "' no existe en el inventario. ***");
            System.out.println("Por favor, ingrese una categoría válida de la lista mostrada.");
            return;
        }

        // Mostrar productos de esa categoría
        List<String> productos = categorias.get(categoriaIngresada);
        System.out.println("\nProductos disponibles en '" + categoriaIngresada + "':");
        for (int j = 0; j < productos.size(); j++) {
            System.out.println("  " + (j + 1) + ". " + productos.get(j));
        }

        System.out.print("\nSeleccione el número del producto a agregar: ");
        try {
            int seleccion = Integer.parseInt(scanner.nextLine().trim());
            if (seleccion < 1 || seleccion > productos.size()) {
                System.out.println("*** ERROR: Selección fuera de rango. ***");
                return;
            }

            String productoSeleccionado = productos.get(seleccion - 1);

            // Agregar o incrementar en la colección del usuario
            if (coleccionUsuario.containsKey(productoSeleccionado)) {
                coleccionUsuario.put(productoSeleccionado, coleccionUsuario.get(productoSeleccionado) + 1);
            } else {
                coleccionUsuario.put(productoSeleccionado, 1);
            }

            System.out.println("\n>> Producto '" + productoSeleccionado + "' agregado a su colección.");
            System.out.println("   Cantidad actual: " + coleccionUsuario.get(productoSeleccionado));

        } catch (NumberFormatException e) {
            System.out.println("*** ERROR: Ingrese un número válido. ***");
        }
    }

    /**
     * Opción 2: Mostrar la categoría de un producto dado su nombre.
     */
    public void mostrarCategoria(Scanner scanner) {
        System.out.println("\n===== BUSCAR CATEGORÍA DE UN PRODUCTO =====");
        System.out.print("Ingrese el nombre del producto: ");
        String producto = scanner.nextLine().trim();

        if (inventario.containsKey(producto)) {
            System.out.println("\nProducto: " + producto);
            System.out.println("Categoría: " + inventario.get(producto));
        } else {
            System.out.println("\n*** ERROR: El producto '" + producto + "' no se encontró en el inventario. ***");
        }
    }

    /**
     * Opción 3: Mostrar la colección del usuario con categoría y cantidad.
     */
    public void mostrarColeccion() {
        System.out.println("\n===== COLECCIÓN DEL USUARIO =====");
        if (coleccionUsuario.isEmpty()) {
            System.out.println("Su colección está vacía. Agregue productos con la opción 1.");
            return;
        }

        System.out.printf("%-35s %-25s %-10s%n", "Producto", "Categoría", "Cantidad");
        System.out.println("-".repeat(70));

        for (Map.Entry<String, Integer> entry : coleccionUsuario.entrySet()) {
            String producto = entry.getKey();
            int cantidad = entry.getValue();
            String categoria = inventario.get(producto);
            System.out.printf("%-35s %-25s %-10d%n", producto, categoria, cantidad);
        }
    }

    /**
     * Opción 4: Mostrar la colección del usuario ordenada por categoría.
     */
    public void mostrarColeccionOrdenada() {
        System.out.println("\n===== COLECCIÓN DEL USUARIO (ORDENADA POR CATEGORÍA) =====");
        if (coleccionUsuario.isEmpty()) {
            System.out.println("Su colección está vacía. Agregue productos con la opción 1.");
            return;
        }

        // Crear un TreeMap temporal para ordenar por categoría
        TreeMap<String, List<String[]>> porCategoria = new TreeMap<>();

        for (Map.Entry<String, Integer> entry : coleccionUsuario.entrySet()) {
            String producto = entry.getKey();
            int cantidad = entry.getValue();
            String categoria = inventario.get(producto);

            if (!porCategoria.containsKey(categoria)) {
                porCategoria.put(categoria, new ArrayList<>());
            }
            porCategoria.get(categoria).add(new String[]{producto, String.valueOf(cantidad)});
        }

        System.out.printf("%-25s %-35s %-10s%n", "Categoría", "Producto", "Cantidad");
        System.out.println("-".repeat(70));

        for (Map.Entry<String, List<String[]>> entry : porCategoria.entrySet()) {
            String categoria = entry.getKey();
            for (String[] datos : entry.getValue()) {
                System.out.printf("%-25s %-35s %-10s%n", categoria, datos[0], datos[1]);
            }
        }
    }

    /**
     * Opción 5: Mostrar todos los productos del inventario con su categoría.
     * Incluye medición de tiempo para profiling.
     */
    public void mostrarInventario() {
        System.out.println("\n===== INVENTARIO COMPLETO =====");

        long inicio = System.nanoTime();

        System.out.printf("%-35s %-25s%n", "Producto", "Categoría");
        System.out.println("-".repeat(60));

        for (Map.Entry<String, String> entry : inventario.entrySet()) {
            System.out.printf("%-35s %-25s%n", entry.getKey(), entry.getValue());
        }

        long fin = System.nanoTime();
        double tiempoMs = (fin - inicio) / 1_000_000.0;

        System.out.println("-".repeat(60));
        System.out.println("Total de productos: " + inventario.size());
        System.out.printf("Tiempo de ejecución: %.4f ms%n", tiempoMs);
    }

    /**
     * Opción 6: Mostrar todos los productos del inventario ordenados por categoría.
     */
    public void mostrarInventarioOrdenado() {
        System.out.println("\n===== INVENTARIO COMPLETO (ORDENADO POR CATEGORÍA) =====");

        long inicio = System.nanoTime();

        // Crear un TreeMap temporal para ordenar por categoría
        TreeMap<String, List<String>> porCategoria = new TreeMap<>();

        for (Map.Entry<String, String> entry : inventario.entrySet()) {
            String producto = entry.getKey();
            String categoria = entry.getValue();

            if (!porCategoria.containsKey(categoria)) {
                porCategoria.put(categoria, new ArrayList<>());
            }
            porCategoria.get(categoria).add(producto);
        }

        System.out.printf("%-25s %-35s%n", "Categoría", "Producto");
        System.out.println("-".repeat(60));

        for (Map.Entry<String, List<String>> entry : porCategoria.entrySet()) {
            String categoria = entry.getKey();
            for (String producto : entry.getValue()) {
                System.out.printf("%-25s %-35s%n", categoria, producto);
            }
        }

        long fin = System.nanoTime();
        double tiempoMs = (fin - inicio) / 1_000_000.0;

        System.out.println("-".repeat(60));
        System.out.printf("Tiempo de ejecución: %.4f ms%n", tiempoMs);
    }
}
