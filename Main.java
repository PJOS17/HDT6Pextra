import java.util.Scanner;

/**
 * Clase principal del sistema de inventario de supermercado.
 * Utiliza el patrón de diseño Factory para seleccionar la implementación de MAP.
 * 
 * Operaciones disponibles:
 * 1. Agregar producto a la colección del usuario
 * 2. Mostrar categoría de un producto
 * 3. Mostrar colección del usuario (producto, categoría, cantidad)
 * 4. Mostrar colección del usuario ordenada por categoría
 * 5. Mostrar inventario completo
 * 6. Mostrar inventario completo ordenado por categoría
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║     SISTEMA DE INVENTARIO - TIENDA ONLINE       ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.println();

        // Selección de implementación de MAP usando Factory Pattern
        int tipoMap = 0;
        while (tipoMap < 1 || tipoMap > 3) {
            System.out.println("Seleccione la implementación de MAP a utilizar:");
            System.out.println("  1. HashMap");
            System.out.println("  2. TreeMap");
            System.out.println("  3. LinkedHashMap");
            System.out.print("Opción: ");
            try {
                tipoMap = Integer.parseInt(scanner.nextLine().trim());
                if (tipoMap < 1 || tipoMap > 3) {
                    System.out.println("*** Opción inválida. Intente de nuevo. ***\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("*** Ingrese un número válido. ***\n");
            }
        }

        // Crear inventario con la implementación seleccionada
        Inventario inventario = new Inventario(tipoMap);
        inventario.cargarInventario("inventario.txt");

        // Menú principal
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n╔══════════════════════════════════════════════════╗");
            System.out.println("║                  MENÚ PRINCIPAL                  ║");
            System.out.println("╠══════════════════════════════════════════════════╣");
            System.out.println("║  1. Agregar producto a su colección              ║");
            System.out.println("║  2. Mostrar categoría de un producto             ║");
            System.out.println("║  3. Mostrar su colección                         ║");
            System.out.println("║  4. Mostrar su colección ordenada por categoría  ║");
            System.out.println("║  5. Mostrar inventario completo                  ║");
            System.out.println("║  6. Mostrar inventario ordenado por categoría    ║");
            System.out.println("║  7. Salir                                        ║");
            System.out.println("╚══════════════════════════════════════════════════╝");
            System.out.print("Seleccione una opción: ");

            try {
                int opcion = Integer.parseInt(scanner.nextLine().trim());

                switch (opcion) {
                    case 1:
                        inventario.agregarProducto(scanner);
                        break;
                    case 2:
                        inventario.mostrarCategoria(scanner);
                        break;
                    case 3:
                        inventario.mostrarColeccion();
                        break;
                    case 4:
                        inventario.mostrarColeccionOrdenada();
                        break;
                    case 5:
                        inventario.mostrarInventario();
                        break;
                    case 6:
                        inventario.mostrarInventarioOrdenado();
                        break;
                    case 7:
                        continuar = false;
                        System.out.println("\n¡Gracias por usar el sistema! Hasta luego.");
                        break;
                    default:
                        System.out.println("*** Opción inválida. Intente de nuevo. ***");
                }

            } catch (NumberFormatException e) {
                System.out.println("*** Ingrese un número válido. ***");
            }
        }

        scanner.close();
    }
}
