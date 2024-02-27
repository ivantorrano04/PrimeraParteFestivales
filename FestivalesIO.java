import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * La clase contiene métodos estáticos que permiten
 * cargar la agenda de festivales leyendo los datos desde
 * un fichero
 */
public class FestivalesIO {

    public static void cargarFestivales(AgendaFestivales agenda) {
        Scanner sc = null;
        try {
            sc = new Scanner(FestivalesIO.class.getResourceAsStream("/festivales.csv"));
            while (sc.hasNextLine()) {
                String lineaFestival = sc.nextLine();
                Festival festival = parsearLinea(lineaFestival);
                agenda.addFestival(festival);
            }
        } finally {
            if (sc != null) {
                sc.close();
            }
        }
    }

    /**
     * Se parsea la línea extrayendo sus datos y creando y
     * devolviendo un objeto Festival
     *
     * @param lineaFestival los datos de un festival
     * @return el festival creado
     */
    public static Festival parsearLinea(String lineaFestival) {
        // Elimina espacios al principio y al final de la línea
        lineaFestival = lineaFestival.trim();

        // Divide la línea utilizando el carácter ':'
        String[] partes = lineaFestival.split(":");

        // Obtén los datos del festival
        String nombre = obtenerNombre(partes[0]);
        String lugar = partes[1].toUpperCase();
        LocalDate fechaInicio = obtenerFecha(partes[2]);
        int duracion = Integer.parseInt(partes[3].trim());
        Set<Estilo> estilos = obtenerEstilos(Arrays.copyOfRange(partes, 4, partes.length));

        // Crea y devuelve el objeto Festival
        return new Festival(nombre, lugar, fechaInicio, duracion, (HashSet<Estilo>) estilos);
    }

    private static String obtenerNombre(String nombre) {
        // Capitaliza la primera letra de cada palabra
        String[] palabras = nombre.split(" ");
        StringBuilder resultado = new StringBuilder();
        for (String palabra : palabras) {
            resultado.append(palabra.substring(0, 1).toUpperCase())
                    .append(palabra.substring(1).toLowerCase())
                    .append(" ");
        }
        return resultado.toString().trim();
    }

    private static LocalDate obtenerFecha(String fecha) {
        // Convierte la cadena de fecha en un objeto LocalDate
        String[] partesFecha = fecha.split("-");
        int dia = Integer.parseInt(partesFecha[0].trim());
        int mes = Integer.parseInt(partesFecha[1].trim());
        int anio = Integer.parseInt(partesFecha[2].trim());
        return LocalDate.of(anio, mes, dia);
    }

    private static Set<Estilo> obtenerEstilos(String[] estilos) {
        // Convierte las cadenas de estilos en un conjunto de estilos enumerados
        Set<Estilo> conjuntoEstilos = new HashSet<>();
        for (String estilo : estilos) {
            conjuntoEstilos.add(Estilo.valueOf(estilo.trim().toUpperCase()));
        }
        return conjuntoEstilos;
    }
}
