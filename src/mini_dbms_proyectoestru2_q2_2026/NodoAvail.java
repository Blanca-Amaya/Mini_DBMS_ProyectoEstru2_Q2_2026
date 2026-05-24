/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mini_dbms_proyectoestru2_q2_2026;

/**
 *
 * @author bamay
 */
public class NodoAvail {
    public long posicion; // (offset) long porque asi puede representar posiciones en archivos grandes, long (64 bits) - hasta 9.22 exabytes
    public int tamanio; // cantidad de espacio libre en bytes
    // Para que sea doblemente enlanzada = debe ir en ambas direccion (anterior y siguiente)
    public NodoAvail anterior; // referencia para regresar al nodo previo
    public NodoAvail siguiente; // referencia para avanzar al nodo posterior

    public NodoAvail(long posicion, int tamanio, NodoAvail anterior, NodoAvail siguiente) {
        this.posicion = posicion;
        this.tamanio = tamanio;
        this.anterior = anterior;
        this.siguiente = siguiente;
    }

    @Override
    public String toString() {
        return "Posicion: " + posicion + ", Tamanio: " + tamanio;
    }   
}
