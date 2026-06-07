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
    
    // retorna la cabeza (para iterar o serializar)
    public NodoAvail getCabeza() {
        return cabeza;
    }
 
    // Retorna la cola
    public NodoAvail getCola() {
        return cola;
    }
    
    /**
     * agrega un bloque libre al FINAL de la lista
     * y se usa cuando se elimina un registro (append al avail list)
     *
     * para posicion offset del bloque en el archivo
     * para tamanio  cantidad de bytes que ocupa el bloque
     */
    public void agregarAlFinal(long posicion, int tamanio) {
        NodoAvail nuevo = new NodoAvail(posicion, tamanio, cola, null);
        if (cola != null) {
            cola.siguiente = nuevo;
        }
        cola = nuevo;
        if (cabeza == null) {
            cabeza = nuevo;
        }
    }
 
    // agrega un bloque libre al INICIO de la lista
    public void agregarAlInicio(long posicion, int tamanio) {
        NodoAvail nuevo = new NodoAvail(posicion, tamanio, null, cabeza);
        if (cabeza != null) {
            cabeza.anterior = nuevo;
        }
        cabeza = nuevo;
        if (cola == null) {
            cola = nuevo;
        }
    }
    
    public NodoAvail buscarBestFit(int tamanioNecesario) { // Best Fit: busca el bloque libre más pequeño que sea >= tamanioNecesario
        NodoAvail mejor  = null;
        NodoAvail actual = cabeza;
        while (actual != null) {
            if (actual.tamanio >= tamanioNecesario) {
                if (mejor == null || actual.tamanio < mejor.tamanio) {
                    mejor = actual;
                }
            }
            actual = actual.siguiente;
        }
        return mejor;
    }
    
    public NodoAvail buscarWorstFit(int tamanioNecesario) { // Worst Fit: busca el bloque libre más grande disponible
        NodoAvail peor   = null;
        NodoAvail actual = cabeza;
        while (actual != null) {
            if (actual.tamanio >= tamanioNecesario) {
                if (peor == null || actual.tamanio > peor.tamanio) {
                    peor = actual;
                }
            }
            actual = actual.siguiente;
        }
        return peor;
    }
    
    public NodoAvail buscarEspacio(int tamanioNecesario, byte estrategia) {
        if (estrategia == 1) {
            return buscarWorstFit(tamanioNecesario);
        }
        return buscarBestFit(tamanioNecesario);
    }
    
    public void eliminarNodo(NodoAvail nodo) {
        if (nodo == null) return;
 
        // reconectar anterior con siguiente
        if (nodo.anterior != null) {
            nodo.anterior.siguiente = nodo.siguiente;
        } else {
            // era la cabeza
            cabeza = nodo.siguiente;
        }
 
        if (nodo.siguiente != null) {
            nodo.siguiente.anterior = nodo.anterior;
        } else {
            // era la cola
            cola = nodo.anterior;
        }
 
        // aislar el nodo eliminado
        nodo.anterior = null;
        nodo.siguiente = null;
    }
    
    public void liberarEspacio(long posicionEnArchivo, int tamanioRegistro) {
        agregarAlFinal(posicionEnArchivo, tamanioRegistro);
    }
    
    public NodoAvail reutilizarEspacio(int tamanioNecesario, byte estrategia) {
        NodoAvail encontrado = buscarEspacio(tamanioNecesario, estrategia);
        if (encontrado != null) {
            eliminarNodo(encontrado);
        }
        return encontrado;
    }
        
}
