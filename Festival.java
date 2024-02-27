import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.time.temporal.ChronoUnit;

/**
 * Un objeto de esta clase almacena los datos de un
 * festival.
 * Todo festival tiene un nombre, se celebra en un lugar
 * en una determinada fecha, dura una serie de días y
 * se engloba en un conjunto determinado de estilos
 *
 */
public class Festival {
    private final String nombre;
    private final String lugar;
    private final LocalDate fechaInicio;
    private final int duracion;
    private final HashSet<Estilo> estilos;

    public Festival(String nombre, String lugar, LocalDate fechaInicio, int duracion, HashSet<Estilo> estilos) {
        this.nombre = nombre;
        this.lugar = lugar;
        this.fechaInicio = fechaInicio;
        this.duracion = duracion;
        this.estilos = estilos;
    }

    public String getNombre() {
        return nombre;
    }

    public String getLugar() {
        return lugar;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public int getDuracion() {
        return duracion;
    }

    public Set<Estilo> getEstilos() {
        return estilos;
    }

    public void addEstilo(Estilo estilo) {
        this.estilos.add(estilo);
    }

    /**
     * Devuelve el mes de celebración del festival, como
     * valor enumerado
     */
    public Month getMes() {
        return fechaInicio.getMonth();
    }

    /**
     * Determina si el festival actual empieza en una fecha anterior
     * a otro festival.
     *
     * @param otro Festival a comparar
     * @return true si el festival actual empieza antes que otro
     */
    public boolean empiezaAntesQue(Festival otro) {
        return fechaInicio.isBefore(otro.getFechaInicio());
    }

    /**
     * Determina si el festival actual empieza en una fecha
     * posterior a otro festival.
     *
     * @param otro Festival a comparar
     * @return true si el festival actual empieza después que otro
     */
    public boolean empiezaDespuesQue(Festival otro) {
        return fechaInicio.isAfter(otro.getFechaInicio());
    }

    /**
     * Devuelve true si el festival ya ha concluido, false en otro caso
     *
     * @return true si el festival ha concluido, false en otro caso
     */
    public boolean haConcluido() {
        LocalDate hoy = LocalDate.now();
        return fechaInicio.plusDays(duracion).isBefore(hoy);
    }

    /**
     * Representación textual del festival, exactamente
     * como se indica en el enunciado
     *
     * @return representación textual del festival
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH);

        StringBuilder sb = new StringBuilder();
        sb.append(nombre).append(" ").append(estilos).append("\n")
                .append(lugar.toUpperCase()).append("\n")
                .append(fechaInicio.format(formatter)).append(" - ")
                .append(fechaInicio.plusDays(duracion - 1).format(formatter))
                .append(" ").append(fechaInicio.getYear());

        if (haConcluido()) {
            sb.append(" (concluido)\n");
        } else {
            long diasRestantes = ChronoUnit.DAYS.between(LocalDate.now(), fechaInicio.plusDays(duracion));
            sb.append(" (quedan ").append(diasRestantes).append(" días)\n");
        }

        sb.append("------------------------------------------------------------");

        return sb.toString();
    }

    /**
     * Código para probar la clase Festival
     *
     * @param args argumentos de línea de comandos (no se utilizan)
     */
    public static void main(String[] args) {
        System.out.println("Probando clase Festival");
        String datosFestival = "Gazpatxo Rock : " +
                "valencia: 28-02-2022  :1  :rock" +
                ":punk " +
                ": hiphop ";
        Festival f1 = FestivalesIO.parsearLinea(datosFestival);
        System.out.println(f1);

        datosFestival = "black sound fest:badajoz:05-02-2022:  21" +
                ":rock" + ":  blues";
        Festival f2 = FestivalesIO.parsearLinea(datosFestival);
        System.out.println(f2);

        datosFestival = "guitar bcn:barcelona: 28-01-2022 :  170" +
                ":indie" + ":pop:fusion";
        Festival f3 = FestivalesIO.parsearLinea(datosFestival);
        System.out.println(f3);

        datosFestival = "  benidorm fest:benidorm:26-01-2022:3" +
                ":indie" + ": pop  :rock";
        Festival f4 = FestivalesIO.parsearLinea(datosFestival);
        System.out.println(f4);

        System.out.println("\nProbando empiezaAntesQue() empiezaDespuesQue()" +
                "\n");
        if (f1.empiezaAntesQue(f2)) {
            System.out.println(f1.getNombre() + " empieza antes que " + f2.getNombre());
        } else if (f1.empiezaDespuesQue(f2)) {
            System.out.println(f1.getNombre() + " empieza después que " + f2.getNombre());
        } else {
            System.out.println(f1.getNombre() + " empieza el mismo día que " + f2.getNombre());
        }

        System.out.println("\nProbando haConcluido()\n");
        System.out.println(f4);
        System.out.println(f4.getNombre() + " ha concluido? " + f4.haConcluido());
        System.out.println(f1);
        System.out.println(f1.getNombre() + " ha concluido? " + f1.haConcluido());
    }
}
