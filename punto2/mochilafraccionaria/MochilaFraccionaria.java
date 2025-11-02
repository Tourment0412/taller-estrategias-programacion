package punto2.mochilafraccionaria;

import java.util.ArrayList;
import java.util.List;

class Objeto {
    String nombre;
    int valor;
    int peso;

    public Objeto(String nombre, int valor, int peso) {
        this.nombre = nombre;
        this.valor = valor;
        this.peso = peso;
    }

    public double getValorPorPeso() {
        return (double) valor / peso;
    }
    
}

public class MochilaFraccionaria {

    public double obtenerMaximo(List<Objeto> objetos, int capacidadMochila, ArrayList<Objeto> objetosSeleccionados) {
        // Ordenar los objetos por valor/peso en orden descendente
        objetos.sort((o1, o2) -> Double.compare(o2.getValorPorPeso(), o1.getValorPorPeso()));

        double valorTotal = 0.0;
        int w=capacidadMochila;
        for (Objeto obj : objetos) {
            if (w == 0) {
                break; // La mochila está llena
            }
            if (obj.peso <= w) {
                // Si el objeto cabe completamente, lo añadimos
                w -= obj.peso;
                valorTotal += obj.valor;
                objetosSeleccionados.add(obj);
            } else {
                // Si no cabe completamente, añadimos la fracción que cabe
                double fraccion = (double) w / obj.peso;
                valorTotal += obj.valor * fraccion;
                objetosSeleccionados.add(new Objeto(obj.nombre, (int) (obj.valor * fraccion), (int) (obj.peso * fraccion))); // Añadir el objeto aunque sea fraccionado
                w = 0; // La mochila está llena
            }
        }

        return valorTotal;
    }
    

    public static void main (String [] args){
        List<Objeto>objetos=new ArrayList<>();
        objetos.add(new Objeto("O1",60,10));
        objetos.add(new Objeto("O2",100,20));
        objetos.add(new Objeto("O3",120,30));
        int capacidadMochila=40;
        ArrayList<Objeto> objetosSeleccionados=new ArrayList<>();
        MochilaFraccionaria mf=new MochilaFraccionaria();
        double valorMaximo=mf.obtenerMaximo(objetos,capacidadMochila,   objetosSeleccionados);
        System.out.println("Capacidad de la mochila: "+capacidadMochila);
        System.out.println("Valor maximo que se puede obtener: "+valorMaximo);
        System.out.println("Objetos seleccionados:");
        for(Objeto obj:objetosSeleccionados){
            System.out.println("Nombre: "+obj.nombre+" Valor: "+obj.valor+" Peso: "+obj.peso);
        }


    }
}
