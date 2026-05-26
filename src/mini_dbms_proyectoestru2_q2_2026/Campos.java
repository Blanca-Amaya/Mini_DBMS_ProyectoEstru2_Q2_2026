/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mini_dbms_proyectoestru2_q2_2026;

/**
 *
 * @author bamay
 */
public class Campos { // para
    private String nombre;
    private String tipoDato;
    private String tamanio;
    private boolean llavePrimaria;
    private boolean llaveSecundaria;

    public Campos(String nombre, String tipoDato, String tamanio, boolean llavePrimaria, boolean llaveSecundaria) {
        this.nombre = nombre;
        this.tipoDato = tipoDato;
        this.tamanio = tamanio;
        this.llavePrimaria = llavePrimaria;
        this.llaveSecundaria = llaveSecundaria;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipoDato() {
        return tipoDato;
    }

    public String getTamanio() {
        return tamanio;
    }

    public boolean isLlavePrimaria() {
        return llavePrimaria;
    }

    public boolean isLlaveSecundaria() {
        return llaveSecundaria;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
    }

    public void setTamanio(String tamanio) {
        this.tamanio = tamanio;
    }

    public void setLlavePrimaria(boolean llavePrimaria) {
        this.llavePrimaria = llavePrimaria;
    }

    public void setLlaveSecundaria(boolean llaveSecundaria) {
        this.llaveSecundaria = llaveSecundaria;
    }

    @Override
    public String toString() {
        return nombre + "|" + tipoDato + "|" + tamanio + "|" + ((llavePrimaria) ? "1" : "0") + "|" + ((llaveSecundaria) ? "1" : "0");
    }
}
