package programacionDinamica.problemaMochila;

/**
 * Resolución del problema clásico de la Mochila (0/1 Knapsack Problem)
 * utilizando Programación Dinámica con Memoización (top-down).
 *
 * Problema: 
 *   Dados n objetos, cada uno con un peso wt[i] y un valor val[i], y una mochila
 *   con capacidad máxima W, determinar el valor máximo que se puede obtener
 *   seleccionando un subconjunto de esos objetos, de modo que la suma de los 
 *   pesos no exceda W. No se pueden tomar divisiones de los objetos
 *   (cada objeto se toma completo o no se toma).
 *
 * Enfoque:
 *   - Recurrencia: para cada objeto, se decide tomarlo (si no excede la capacidad)
 *     o no tomarlo, y se elige la alternativa con mayor ganancia total.
 *   - Técnica: Memoización. Almacena los resultados ya calculados para evitar
 *     resolver el mismo subproblema recursivamente más de una vez.
 * 
 * Complejidad: 
 *   - Tiempo O(n*W), donde n es la cantidad de ítems y W la capacidad total.
 *   - Espacio O(n*W) para la tabla de memoización.
 *
 * Variables:
 *   - W: capacidad restante de la mochila.
 *   - val[]: valores de los objetos.
 *   - wt[]: pesos de los objetos.
 *   - n: índice actual de objeto a considerar (elementos 0 ... n-1).
 *   - memo: tabla memoizadora de soluciones parciales, dimensiones (n+1) x (W+1).
 */

class GfG_Memoization {

    /**
     * Función recursiva principal con memoización para el problema de la mochila.
     *
     * @param W    Capacidad restante de la mochila.
     * @param val  Arreglo con valores de los objetos.
     * @param wt   Arreglo con pesos de los objetos.
     * @param n    Cantidad de objetos considerados (restantes).
     * @param memo Tabla de memoización para almacenar soluciones parciales ya calculadas.
     * @return     Valor máximo que se puede obtener con los n primeros objetos y capacidad W.
     */
    static int knapsackRec(int W, int[] val, int[] wt, int n, int[][] memo) {
        // Caso base: sin objetos o mochila sin capacidad, ganancia 0.
        if (n == 0 || W == 0)
            return 0;

        // Si ya hemos calculado este subproblema, lo devolvemos directamente.
        if (memo[n][W] != -1)
            return memo[n][W];

        int pick = 0; // Valor si tomamos el objeto n-ésimo (índices desde 0).
        
        // Intentar incluir el objeto actual solo si cabe en la mochila.
        // Si lo tomamos, sumamos su valor y restamos su peso a la capacidad restante,
        // y seguimos con los n-1 objetos restantes.
        if (wt[n - 1] <= W)
            pick = val[n - 1] + knapsackRec(W - wt[n - 1], val, wt, n - 1, memo);

        // Opción de NO tomar el objeto actual: resolver solo con los n-1 objetos.
        int notPick = knapsackRec(W, val, wt, n - 1, memo);

        // Guardamos la mejor de ambas opciones en memo y la devolvemos.
        return memo[n][W] = Math.max(pick, notPick);
    }

    /**
     * Inicializa la tabla de memoización e invoca la función recursiva.
     *
     * @param W   Capacidad total de la mochila.
     * @param val Valores de los objetos.
     * @param wt  Pesos de los objetos.
     * @return    Valor máximo que se puede obtener.
     */
    static int knapsack(int W, int[] val, int[] wt) {
        int n = val.length;

        // Creamos tabla de memoización de tamaño (n+1) x (W+1)
        int[][] memo = new int[n + 1][W + 1];

        // Inicializamos todas las posiciones con -1 para denotar subproblemas no calculados.
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= W; j++)
                memo[i][j] = -1;
        }

        // Resolver el subproblema completo.
        return knapsackRec(W, val, wt, n, memo);
    }

    /**
     * Ejemplo de uso del algoritmo.
     * Prueba el algoritmo con 3 objetos con valores y pesos dados y una
     * capacidad de mochila de 4, mostrando el resultado óptimo.
     */
    public static void main(String[] args) {
        // Ejemplo: valores de los objetos
        int[] val = { 2, 5, 10, 14, 15 };
        // Pesos de los objetos correspondientes
        int[] wt = { 1, 3, 4, 5, 7 };
        // Capacidad de la mochila
        int W = 8;

        // Ejecuta el algoritmo y muestra el resultado óptimo por consola
        System.out.println(knapsack(W, val, wt));
    }
}