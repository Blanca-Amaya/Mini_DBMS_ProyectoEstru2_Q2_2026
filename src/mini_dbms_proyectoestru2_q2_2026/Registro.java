/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mini_dbms_proyectoestru2_q2_2026;

import java.util.ArrayList;

/**
 *
 * @author bamay
 */
// un registro del archivo (1 fila)
public class Registro { 
    private ArrayList<Object> valores; // valores de los registros
    private ArrayList<DescriptorCampos> estructuraCampos; // Referencia a la estructura de campos
    private long posicionEnArchivo; // la direccion del archivo en el disco

    public Registro() {
        this.valores = new ArrayList<>();
        this.estructuraCampos = new ArrayList<>();
        this.posicionEnArchivo = -1;
    }
    
    public Registro(ArrayList<DescriptorCampos> estructuraCampos) {
        this.valores = new ArrayList<>();
        this.estructuraCampos = estructuraCampos;
        this.posicionEnArchivo = -1;
        for (int i = 0; i < estructuraCampos.size(); i++) {
            valores.add("");
        }
    }

    public ArrayList<Object> getValores() {
        return valores;
    }

    public long getPosicionEnArchivo() {
        return posicionEnArchivo;
    }

    public void setValores(ArrayList<Object> valores) {
        this.valores = valores;
    }

    public void setPosicionEnArchivo(long posicionEnArchivo) {
        this.posicionEnArchivo = posicionEnArchivo;
    }
    
    // agrega el valor que corresponde a un campo
    public void agregarValor(Object valor) {
        valores.add(valor);
    }
    
    // cantidad de valores
    public int tamanio_valores() {
        return valores.size();
    }
    
    // valor por indices
    public Object getValor(int indice) {
        if (indice >= 0 && indice < valores.size()) {
            return valores.get(indice);
        }
        return null;
    }
    
    // valor-->String
    public String ValorString(int indice) {
        Object v = getValor(indice);
        if (v != null) {
            return v.toString();
        } else {
            return "";
        }
    }
    
    // pasar a string y asi guardarlo en el archivo
    public String guardarString_File() {
        String cadena = "";
        for (int i = 0; i < valores.size(); i++) {
            cadena += ValorString(i);
            if (i < valores.size() - 1) {
                cadena += "|";
            }
        }
        return cadena;
    }
    
    // cambiar algun valor usando el indice que la que se encuentra
    public void setValor(int indice, Object valor) {
        if (indice >= 0 && indice < valores.size()) {
            valores.set(indice, valor);
        }
    }
    
    // estru de campos
    public ArrayList<DescriptorCampos> getEstructuraCampos() {
        return estructuraCampos;
    }
    
    // cambios estru de campos
    public void setEstructuraCampos(ArrayList<DescriptorCampos> estructuraCampos) {
        this.estructuraCampos = estructuraCampos;
    }
    
    public void limpiar() {
        valores.clear();
        posicionEnArchivo = -1;
    }

    @Override
    public String toString() {
        String formato = "";
        for (int i = 0; i < valores.size(); i++) {
            formato += ValorString(i);
            if (i < valores.size() - 1) {
                formato += "|";
            }
        }
        return formato;
    }
}
