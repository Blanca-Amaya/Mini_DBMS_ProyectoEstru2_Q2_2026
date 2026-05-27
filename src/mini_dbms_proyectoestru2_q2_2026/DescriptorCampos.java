/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mini_dbms_proyectoestru2_q2_2026;

/**
 *
 * @author bamay
 */
public class DescriptorCampos { // estructura fija que describirá cada campo.
    private char[] nombre_campo;
    private byte tipo; // 0 = int, 1 = float, 2 = bool, 3 = char, 4 = string
    private int tamanio_max; // tamanio maximo de bytes que ocupa
    private byte longitud; // 0 = variable, 1 = fije
    private byte nullable; // indica si un campo puede estar vacio o si es obligatorio -> 0 = no puede estar vacio, 1 = puede estar vacio
    private byte llave_primaria;
    private byte llave_secundaria; // indice
    private int posicion_logica; 

    public DescriptorCampos() {
        this.nombre_campo = new char [25];
        this.tipo = 0;
        this.tamanio_max = 0;
        this.longitud = 0;
        this.nullable = 0;
        this.llave_primaria = 0;
        this.llave_secundaria = 0;
        this.posicion_logica = 0;
    }
    
    public DescriptorCampos(String nombre, byte tipo, int tamanio_max, byte longitud, byte nullable, byte llave_primaria, byte llave_secundaria, int posicion_logica) {
        this.nombre_campo = new char[25];
        setNombre_campo(nombre);
        this.tipo = tipo;
        this.tamanio_max = tamanio_max;
        this.longitud = longitud;
        this.nullable = nullable;
        this.llave_primaria = llave_primaria;
        this.llave_secundaria = llave_secundaria;
        this.posicion_logica = posicion_logica;
    }
    
    // set para nombre (char -> string)
    public void setNombre_campo(String nombre) {
        for (int i = 0; i < this.nombre_campo.length; i++) {
            if (i< nombre.length()) {
                this.nombre_campo[i] = nombre.charAt(i);
            } else {
                this.nombre_campo[i] = '\0';
            }
        }
    }
    
    // getter para nombre (char -> string)
    public String getNombre_campo() {
        String resultado = "";
        for (char c : nombre_campo) {
            if (c != '\0') {
                resultado += c;
            } else {
                break;
            }
        }
        return resultado;
    }

    public byte getLlave_primaria() {
        return llave_primaria;
    }

    public byte getLlave_secundaria() {
        return llave_secundaria;
    }

    public byte getLongitud() {
        return longitud;
    }

    public byte getTipo() {
        return tipo;
    }

    public int getTamanio_max() {
        return tamanio_max;
    }

    public byte getNullable() {
        return nullable;
    }

    public int getPosicion_logica() {
        return posicion_logica;
    }

    public void setTipo(byte tipo) {
        this.tipo = tipo;
    }

    public void setTamanio_max(int tamanio_max) {
        this.tamanio_max = tamanio_max;
    }

    public void setLongitud(byte longitud) {
        this.longitud = longitud;
    }

    public void setNullable(byte nullable) {
        this.nullable = nullable;
    }

    public void setLlave_primaria(byte llave_primaria) {
        this.llave_primaria = llave_primaria;
    }

    public void setLlave_secundaria(byte llave_secundaria) {
        this.llave_secundaria = llave_secundaria;
    }

    public void setPosicion_logica(int posicion_logica) {
        this.posicion_logica = posicion_logica;
    }
}
