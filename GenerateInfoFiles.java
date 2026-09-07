import java.io.PrintWriter;
import java.util.Random;

public class GenerateInfoFiles {

    // Datos que se utilizaran para generar estudiantes aleatoriamente
    private static final String[] NOMBRES = {
        "Carlos", "Ana", "Luis", "Maria", "Juan",
        "Laura", "Andres", "Sofia", "Daniel", "Valentina"
    };

    private static final String[] APELLIDOS = {
        "Perez", "Gomez", "Rodriguez", "Martinez",
        "Lopez", "Garcia", "Ramirez", "Torres"
    };

    private static final String[] MATERIAS = {
        "Calculo",
        "Programacion",
        "Fisica",
        "Algebra",
        "Ingles"
    };

    private static final Random RANDOM = new Random();

    public static void main(String[] args) {

        try {
            System.out.println("Iniciando generacion de archivos...");

            createStudentsFile(10);
            createGradesFile(10);

            System.out.println("Archivos generados correctamente.");

        } catch (Exception e) {
            System.out.println("ERROR al generar los archivos: "
                    + e.getMessage());
        }
    }

    // Genera el archivo con la informacion de los estudiantes
    public static void createStudentsFile(int studentsCount)
            throws Exception {

        PrintWriter writer = new PrintWriter("alumnos.csv");

        for (int i = 1; i <= studentsCount; i++) {

            String id = "A" + i;

            String nombre =
                    NOMBRES[RANDOM.nextInt(NOMBRES.length)];

            String apellido =
                    APELLIDOS[RANDOM.nextInt(APELLIDOS.length)];

            writer.println(id + ";" + nombre + " " + apellido);
        }

        writer.close();
    }

    // Genera las notas de cada estudiante
    public static void createGradesFile(int studentsCount)
            throws Exception {

        PrintWriter writer = new PrintWriter("notas.txt");

        for (int i = 1; i <= studentsCount; i++) {

            String id = "A" + i;

            // Cada estudiante tendra una nota por cada materia
            for (String materia : MATERIAS) {

                double nota =
                        1.0 + (4.0 * RANDOM.nextDouble());

                int creditos =
                        RANDOM.nextInt(4) + 1;

                writer.printf(
                        "%s;%s;%.1f;%d_creditos%n",
                        id,
                        materia,
                        nota,
                        creditos
                );
            }
        }

        writer.close();
    }
}