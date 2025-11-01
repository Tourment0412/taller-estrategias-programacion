package Punto1;

public class MajorityElement {

    //Metodo principal para encontrar el elemento mayoritario
    public static int mayorElemento(int[] lista) {
        if (lista == null || lista.length == 0) return -1;
        int candidato = mayorElementoRango(lista, 0, lista.length - 1);
        int cantidad = contarEnRango(lista, candidato, 0, lista.length - 1);
        return (cantidad > lista.length / 2) ? candidato : -1;
    }

    // Recursiva: devuelve el candidato mayoritario en el rango [inicio..fin]
    private static int mayorElementoRango(int[] lista, int inicio,int fin) {
        if (inicio == fin) return lista[inicio]; 
        
        int mitad = inicio + (fin - inicio) / 2;
        int candidatoIzquierdo = mayorElementoRango(lista, inicio, mitad);
        int candidatoDerecho = mayorElementoRango(lista, mitad + 1, fin);

        if (candidatoIzquierdo == candidatoDerecho) return candidatoIzquierdo;

        int cantidadIzquierda = (candidatoIzquierdo == Integer.MIN_VALUE) ? 0 : contarEnRango(lista, candidatoIzquierdo, inicio, fin);
        int cantidadDerecha = (candidatoDerecho == Integer.MIN_VALUE) ? 0 : contarEnRango(lista, candidatoDerecho, inicio, fin);

        if (cantidadIzquierda > (fin - inicio + 1) / 2) return candidatoIzquierdo;
        if (cantidadDerecha > (fin - inicio + 1) / 2) return candidatoDerecho;
        return Integer.MIN_VALUE; 
    }

    // Cuenta apariciones del valor val en lista[inicio..fin]
    private static int contarEnRango(int[] lista, int val, int inicio, int fin) {
        int cnt = 0;
        for (int i = inicio; i <= fin; i++) {
            if (lista[i] == val) {
                cnt++;
            }
        }
        return cnt;
    }

    public static void main(String[] args) {
        int[] arr = {2, 2, 1, 1, 1, 2, 2};
        System.out.println(mayorElemento(arr)); // imprime 2
        int[] arr2 = {1,2,3,4};
        System.out.println(mayorElemento(arr2)); // imprime -1
    }
}
