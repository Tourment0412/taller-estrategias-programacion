package punto3.caminoMinimo;
// Programación Dinámica en matriz (solo arriba e izquierda)
// En cada celda (i, j), el costo mínimo depende de min(dp[i-1][j], dp[i][j-1]) + cost[i][j]

/**
 * Problema: Dada una matriz de costos no negativos cost[n][m], hallar el costo
 * mínimo para ir desde la celda inicial (0, 0) hasta la celda final (n-1, m-1)
 * moviéndose solo hacia la derecha o hacia abajo.
 *
 * Idea de DP (programación dinámica): El costo mínimo para llegar a (i, j)
 * depende del mejor costo para llegar a sus predecesores inmediatos
 * alcanzables con las restricciones de movimiento: (i-1, j) (desde arriba) y
 * (i, j-1) (desde la izquierda).
 *
 * Recurrencia:
 *   dp[i][j] = cost[i][j] + min(dp[i-1][j], dp[i][j-1])
 *
 * Casos base:
 *   dp[0][0] = cost[0][0]
 *   primera fila (i = 0): dp[0][j] = dp[0][j-1] + cost[0][j] (solo se puede venir de la izquierda)
 *   primera columna (j = 0): dp[i][0] = dp[i-1][0] + cost[i][0] (solo se puede venir de arriba)
 *
 * Restricciones y supuestos:
 * - Se asume que cost tiene dimensiones válidas (n>0 y m>0) y usualmente costos
 *   no negativos. Si hay costos negativos, la recurrencia sigue siendo válida,
 *   pero la interpretación del problema puede requerir cuidado adicional.
 * - Movimientos permitidos: derecha y abajo. No se permiten diagonales ni retrocesos.
 *
 * Complejidad:
 *   Tiempo O(n*m), Espacio O(n*m). Puede optimizarse a O(m) con la versión 1D.
 *
 * Extensiones comunes:
 * - Obstáculos: si una celda es inaccesible, puede marcarse con un valor
 *   especial y saltarse en la transición.
 * - Reconstrucción de camino: mantener un arreglo de predecesores para imprimir
 *   la ruta óptima, no solo el costo.
 */
public class MinPathSumGrid {

    /**
     * Calcula el costo mínimo acumulado para llegar a (n-1, m-1) usando DP 2D.
     * Construye la tabla dp de tamaño n x m donde dp[i][j] guarda el costo mínimo
     * para alcanzar la celda (i, j) desde (0, 0) con movimientos derecha/abajo.
     *
     * Validaciones simples:
     * - Si la matriz es vacía (n=0 o m=0), retorna 0 por conveniencia.
     *
     * @param cost matriz de costos (n x m)
     * @return costo mínimo para llegar a (n-1, m-1)
     */
    public static int minPathSum(int[][] cost) {
        int n = cost.length;
        if (n == 0) return 0;
        int m = cost[0].length;
        if (m == 0) return 0;

        int[][] dp = new int[n][m];
        // Punto de partida
        dp[0][0] = cost[0][0];

        // Completar primera fila: solo se puede venir desde la izquierda
        for (int j = 1; j < m; ++j) {
            dp[0][j] = dp[0][j - 1] + cost[0][j];
        }

        // Completar primera columna: solo se puede venir desde arriba
        for (int i = 1; i < n; ++i) {
            dp[i][0] = dp[i - 1][0] + cost[i][0];
        }

        // Rellenar el resto de celdas aplicando la recurrencia
        for (int i = 1; i < n; ++i) {
            for (int j = 1; j < m; ++j) {
                dp[i][j] = cost[i][j] + Math.min(dp[i - 1][j], dp[i][j - 1]);
            }
        }

        return dp[n - 1][m - 1];
    }

    /**
     * Variante optimizada de suma mínima de caminos usando solo una fila acumulada (espacio O(m)).
     *
     * En esta versión, en lugar de usar una matriz completa dp[n][m], solo utilizamos un arreglo
     * unidimensional 'dp' de tamaño m, donde m es el número de columnas.
     *
     * El arreglo dp simula una "fila móvil" que contiene, para cada columna j, el costo mínimo
     * para llegar a la celda actual en la fila i y columna j.
     *
     * ¿Cómo se interpreta dp durante el algoritmo?
     * - dp[j] antes de actualizar corresponde al costo mínimo para llegar a la celda (i-1, j),
     *   es decir, la celda de arriba.
     * - dp[j-1], ya actualizado en la misma iteración, representa el costo mínimo para llegar
     *   a la celda de la izquierda (i, j-1).
     *
     * De esta forma, cuando actualizamos dp[j], sumamos el costo actual y tomamos el mínimo entre
     * el camino desde arriba (dp[j]) y el camino desde la izquierda (dp[j-1]), que son las únicas
     * formas de llegar a la celda actual permitidas en el problema.
     *
     * La inicialización también se hace cuidadosamente:
     * - Primero construimos dp para la primera fila, ya que solo puede alcanzarse desde la izquierda.
     * - Luego, por cada fila siguiente, actualizamos dp[0] (primera columna) sumando solo el valor
     *   desde arriba, y luego procesamos el resto de columnas considerando ambos caminos posibles.
     *
     * Esta optimización ahorra mucha memoria (especialmente para matrices grandes), manteniendo el
     * mismo valor de DP en O(n*m), pero usando solo O(m) espacio extra.
     *
     * @param cost matriz de costos (n x m)
     * @return costo mínimo para llegar a (n-1, m-1)
     */
    public static int minPathSum1D(int[][] cost) {
        int n = cost.length;
        if (n == 0) return 0;
        int m = cost[0].length;
        if (m == 0) return 0;

        int[] dp = new int[m];
        // Inicialización de la primera celda y primera fila
        dp[0] = cost[0][0];
        System.out.println("dp[0] = " + dp[0]);
        for (int j = 1; j < m; ++j) {
            dp[j] = dp[j - 1] + cost[0][j];
        }
        
        for (int i = 1; i < n; ++i) {
            // Primera columna: solo se viene desde arriba
            dp[0] = dp[0] + cost[i][0];
            System.out.println("dp[0] = " + dp[0]);
            for (int j = 1; j < m; ++j) {
                dp[j] = cost[i][j] + Math.min(dp[j], dp[j - 1]);

            }
        }
        return dp[m - 1];
    }

    /**
     * Ejemplo de uso con una matriz de costos pequeña.
     * Salida esperada:
     *   Costo mínimo (2D): 7
     *   Costo mínimo (1D): 7
     */
    public static void main(String[] args) {
        int[][] cost = {
            {1, 3, 1},
            {1, 5, 1},
            {4, 2, 1}
        };

        int ans2D = minPathSum(cost);
        int ans1D = minPathSum1D(cost);
        //System.out.println("Costo mínimo (2D): " + ans2D); // esperado: 7
        System.out.println("Costo mínimo (1D): " + ans1D); // esperado: 7
    }
}


