import java.util.HashMap;
import java.util.TreeMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Clase Factory que implementa el patrón de diseño Factory
 * para seleccionar la implementación de MAP en tiempo de ejecución.
 */
public class MapFactory {

    /**
     * Crea y retorna una instancia de Map según la opción seleccionada
     * anteriormente.
     * 
     * @param opcion 1 = HashMap, 2 = TreeMap, 3 = LinkedHashMap
     * @return una nueva instancia de Map
     */
    public static <K, V> Map<K, V> getMap(int opcion) {
        switch (opcion) {
            case 1:
                System.out.println(">> Usando HashMap");
                return new HashMap<>();
            case 2:
                System.out.println(">> Usando TreeMap");
                return new TreeMap<>();
            case 3:
                System.out.println(">> Usando LinkedHashMap");
                return new LinkedHashMap<>();
            default:
                throw new IllegalArgumentException(
                        "Opción inválida. Seleccione 1 (HashMap), 2 (TreeMap) o 3 (LinkedHashMap).");
        }
    }
}
