import java.util.Scanner;
import java.io.PrintStream;

/**
 * Clase principal del sistema de inventario de supermercado.
 * Utiliza el patron de diseno Factory para seleccionar la implementacion de
 * MAP.
 * 
 * Operaciones disponibles:
 * 1. Agregar producto a la coleccion del usuario
 * 2. Mostrar categoria de un producto
 * 3. Mostrar coleccion del usuario (producto, categoria, cantidad)
 * 4. Mostrar coleccion del usuario ordenada por categoria
 * 5. Mostrar inventario completo
 * 6. Mostrar inventario completo ordenado por categoria
 */
public class Main {

    public static void main(String[] args) {
        // Configurar consola para UTF-8 en Windows
        try {
            ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "chcp", "65001");
            pb.inheritIO();
            Process p = pb.start();
            p.waitFor();
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (Exception e) {
            // Si falla, continuar con la codificacion por defecto
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("+==================================================+");
        System.out.println("|     SISTEMA DE INVENTARIO - TIENDA ONLINE        |");
        System.out.println("+==================================================+");
        System.out.println();

        // Seleccion de implementacion de MAP usando Factory Pattern
        int tipoMap = 0;
        while (tipoMap < 1 || tipoMap > 3) {
            System.out.println("Seleccione la implementacion de MAP a utilizar:");
            System.out.println("  1. HashMap");
            System.out.println("  2. TreeMap");
            System.out.println("  3. LinkedHashMap");
            System.out.print("Opcion: ");
            try {
                tipoMap = Integer.parseInt(scanner.nextLine().trim());
                if (tipoMap < 1 || tipoMap > 3) {
                    System.out.println("*** Opcion invalida. Intente de nuevo. ***\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("*** Ingrese un numero valido. ***\n");
            }
        }

        // Crear inventario con la implementacion seleccionada
        Inventario inventario = new Inventario(tipoMap);
        inventario.cargarInventario("inventario.txt");

        // Menu principal
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n+==================================================+");
            System.out.println("|                  MENU PRINCIPAL                  |");
            System.out.println("+==================================================+");
            System.out.println("|  1. Agregar producto a su coleccion              |");
            System.out.println("|  2. Mostrar categoria de un producto             |");
            System.out.println("|  3. Mostrar su coleccion                         |");
            System.out.println("|  4. Mostrar su coleccion ordenada por categoria  |");
            System.out.println("|  5. Mostrar inventario completo                  |");
            System.out.println("|  6. Mostrar inventario ordenado por categoria    |");
            System.out.println("|  7. Salir                                        |");
            System.out.println("+==================================================+");
            System.out.print("Seleccione una opcion: ");

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
                        System.out.println("\nGracias por usar el sistema! Hasta luego.");
                        break;
                    default:
                        System.out.println("*** Opcion invalida. Intente de nuevo. ***");
                }

            } catch (NumberFormatException e) {
                System.out.println("*** Ingrese un numero valido. ***");
            }
        }

        scanner.close();
    }
}
