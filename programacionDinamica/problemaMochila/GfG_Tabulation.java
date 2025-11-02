package programacionDinamica.problemaMochila;

/**
 * Resolución del problema de la Mochila 0/1 (Knapsack Problem) usando Programación Dinámica con tabulación (enfoque bottom-up).
 *
 * --- DESCRIPCIÓN DEL PROBLEMA ---
 *
 * Dado un conjunto de n objetos, cada uno con un peso 'wt[i]' y un valor 'val[i]', y una mochila con capacidad máxima 'W',
 * determinar el valor máximo que se puede obtener introduciendo en la mochila un subconjunto de esos objetos,
 * de modo que la suma de los pesos no exceda W. Cada objeto solo puede incluirse una vez (o se toma entero o no se toma).
 *
 * --- IDEAS CLAVE ---
 * - Programación dinámica (DP) tabulada: se construye una tabla que contiene la solución óptima para subproblemas más pequeños,
 *   y se utilizan para resolver subproblemas mayores evitando la recursión directa.
 * - Se mantiene una matriz 'dp', donde dp[i][j] es el valor máximo obtenible usando los primeros i objetos y una mochila de capacidad j.
 *
 * --- RECURRENCIA DP ---
 *   Si no tomamos el i-ésimo objeto:  dp[i][j] = dp[i-1][j]
 *   Si tomamos el i-ésimo objeto (si cabe): dp[i][j] = val[i-1] + dp[i-1][j - wt[i-1]]
 *   En general: dp[i][j] = max(dp[i-1][j], val[i-1]+dp[i-1][j-wt[i-1]])  (si wt[i-1]<=j)
 *
 * --- CASOS BASE ---
 * - Si no hay objetos (i=0) o capacidad es 0 (j=0): dp[i][j]=0
 *
 * --- COMPLEJIDAD ---
 *   - Tiempo: O(n*W)
 *   - Espacio: O(n*W) para la tabla dp
 *
 * --- EXTENSIONES Y OBSERVACIONES ---
 * - Esta misma tabla puede usarse, si se desea, para recuperar qué objetos componen la solución óptima siguiendo los valores registrados.
 * - Si solo se desea el valor y no el subconjunto, se puede reducir el espacio a O(W) usando un array unidimensional.
 *
 * @author GPT + Usuario
 */
class GfG_Tabulation {

    /**
     * Resuelve el problema de la mochila 0/1 usando programación dinámica (tabulación).
     *
     * @param W    Capacidad máxima de la mochila (entero positivo)
     * @param val  Arreglo de valores de los objetos (val[i] es el valor del i-ésimo objeto)
     * @param wt   Arreglo de pesos de los objetos (wt[i] es el peso del i-ésimo objeto)
     * @return     Valor máximo que se puede obtener sin exceder la capacidad
     */
    static int knapsack(int W, int[] val, int[] wt) {
        int n = wt.length; // Cantidad de objetos
        // dp[i][j]: solución óptima usando primeros i objetos y capacidad j
        int[][] dp = new int[n + 1][W + 1];

        // Llenar la tabla dp de manera ascendente (bottom-up)
        for (int i = 0; i <= n; i++) { // Para cada cantidad de objetos
            for (int j = 0; j <= W; j++) { // Para cada posible capacidad

                // CASO BASE: Sin objetos o mochila sin capacidad => Ganancia 0
                if (i == 0 || j == 0)
                    dp[i][j] = 0;
                else {
                    int pick = 0; // Valor si tomamos el objeto actual

                    // COMPROBAR si el objeto actual (i-1) cabe en la mochila
                    if (wt[i - 1] <= j)
                        // Si lo tomamos: sumamos su valor y resolvemos subproblema con el resto
                        pick = val[i - 1] + dp[i - 1][j - wt[i - 1]];

                    // Si no lo tomamos: el mejor valor es el disponible con i-1 objetos y la misma capacidad
                    int notPick = dp[i - 1][j];

                    // Nos quedamos con la alternativa de mayor ganancia
                    dp[i][j] = Math.max(pick, notPick);
                }
            }
        }

        // La celda final contiene la solución al problema: todos los objetos y capacidad completa
        return dp[n][W];
    }

    /**
     * Ejemplo de uso del algoritmo para la mochila.
     * Se prueba con 3 objetos de valores y pesos determinados y capacidad de 4.
     */
    public static void main(String[] args) {
        // Ejemplo: valores de los objetos
        int[] val = { 2, 5, 10, 14, 15 };
        // Pesos de los objetos correspondientes
        int[] wt = { 1, 3, 4, 5, 7 };
        // Capacidad de la mochila
        int W = 8;
        
        // Ejecutar y mostrar el resultado óptimo
        System.out.println(knapsack(W, val, wt));
    }
}
