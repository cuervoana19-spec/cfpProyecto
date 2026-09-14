import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        System.out.println("Iniciando procesamiento de notas...");

        try {

            // Guarda el nombre de cada estudiante
            Map<String, String> estudiantes = new HashMap<>();

            // Guarda la suma de nota por creditos de cada estudiante
            Map<String, Double> sumaPonderada = new HashMap<>();

            // Guarda la cantidad total de creditos de cada estudiante
            Map<String, Integer> totalCreditos = new HashMap<>();

            // Leer archivo alumnos.csv
            BufferedReader lectorAlumnos =
                    new BufferedReader(new FileReader("alumnos.csv"));

            String linea;

            while ((linea = lectorAlumnos.readLine()) != null) {

                String[] datos = linea.split(";");

                if (datos.length >= 2) {

                    String id = datos[0];
                    String nombre = datos[1];

                    estudiantes.put(id, nombre);
                }
            }

            lectorAlumnos.close();

            // Leer archivo notas.txt
            BufferedReader lectorNotas =
                    new BufferedReader(new FileReader("notas.txt"));

            while ((linea = lectorNotas.readLine()) != null) {

                String[] datos = linea.split(";");

                if (datos.length >= 4) {

                    String id = datos[0];

                    // Permite leer notas con coma o con punto decimal
                    double nota =
                            Double.parseDouble(datos[2].replace(",", "."));

                    String textoCreditos =
                            datos[3].replace("_creditos", "");

                    int creditos =
                            Integer.parseInt(textoCreditos);

                    double valorPonderado =
                            nota * creditos;

                    sumaPonderada.put(
                            id,
                            sumaPonderada.getOrDefault(id, 0.0)
                            + valorPonderado
                    );

                    totalCreditos.put(
                            id,
                            totalCreditos.getOrDefault(id, 0)
                            + creditos
                    );
                }
            }

            lectorNotas.close();

            // Lista para guardar los resultados
            List<String[]> resultados = new ArrayList<>();

            for (String id : estudiantes.keySet()) {

                double promedio = 0;

                if (totalCreditos.containsKey(id)
                        && totalCreditos.get(id) > 0) {

                    promedio =
                            sumaPonderada.get(id)
                            / totalCreditos.get(id);
                }

                resultados.add(
                        new String[]{
                            id,
                            estudiantes.get(id),
                            String.valueOf(promedio)
                        }
                );
            }

            // Ordenar de mayor a menor promedio
            resultados.sort(
                    Comparator.comparingDouble(
                            (String[] estudiante)
                            -> Double.parseDouble(estudiante[2])
                    ).reversed()
            );

            // Crear archivo promedios.csv
            PrintWriter escritor =
                    new PrintWriter("promedios.csv");

            for (String[] estudiante : resultados) {

                double promedio =
                        Double.parseDouble(estudiante[2]);

                escritor.printf(
                        Locale.US,
                        "%s;%s;%.2f_Prom%n",
                        estudiante[0],
                        estudiante[1],
                        promedio
                );
            }

            escritor.close();

            System.out.println(
                    "Archivo promedios.csv generado correctamente."
            );

            System.out.println(
                    "Procesamiento finalizado."
            );

        } catch (Exception e) {

            System.out.println(
                    "ERROR al procesar los archivos: "
                    + e.getMessage()
            );
        }
    }
}
