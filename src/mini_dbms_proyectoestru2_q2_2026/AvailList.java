/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mini_dbms_proyectoestru2_q2_2026;

/**
 *
 * @author bamay
 */
public class AvailList { // AvailList simple solo agrega los bloques sin un orden específico (normalmente al inicio o al final).
    private NodoAvail cabeza; // Primer bloque libre (menor posicion)
    private NodoAvail cola; // Ultimo bloque libre (mayor posicion)
    
    // para ver si hay espacio disponible
    public boolean estaVacio() {
        return cabeza == null;
    }
    
    // contar cuantos bloques hay libre
    public int getTamanio() {
        int bloquesLibres = 0;
        NodoAvail actual = cabeza; 
        while (actual != null) {
            bloquesLibres++;
            actual = actual.siguiente; // referenca al nodo posterior 
        }
        return bloquesLibres;
    }
    
    
}
