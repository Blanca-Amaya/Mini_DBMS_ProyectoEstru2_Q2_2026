/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mini_dbms_proyectoestru2_q2_2026;

/**
 *
 * @author bamay
 */
public class Header { // es la primera seccion del archivo que contiene info general sobre el archivo mismo.
    private int identificador_unico; // 4 bytes
    private int version_formato; // 4 bytes
    private char[] nombre_archivo; // maximo 50 caracteres = 100 bytes
    private int cantidad_campos; // 4 bytes -> total de campos
    private long offsetDatos; // 8 bytes -> pos donde empieza los registros
    private long offset_availList; // 8 bytes -> pos de donde empieza la lista de espacios libres
    private int cantidad_registros; // 4 bytes -> registros activos + registros eliminados
    private int registros_actives; // 4 bytes -> (no eliminados)
    private int tamanio_metadata; // 4 bytes -> tamanio total de la metadata
    private byte estrategia; // 1 byte -> 0 = best fit, 1 = worst fit

    public Header(int identificador_unico, int version_formato, char[] nombre_archivo, int cantidad_campos, long offsetDatos, long offset_availList, int cantidad_registros, int registros_actives, int tamanio_metadata, byte estrategia) {
        this.identificador_unico = identificador_unico;
        this.version_formato = version_formato;
        this.nombre_archivo = nombre_archivo;
        this.cantidad_campos = cantidad_campos;
        this.offsetDatos = offsetDatos;
        this.offset_availList = offset_availList;
        this.cantidad_registros = cantidad_registros;
        this.registros_actives = registros_actives;
        this.tamanio_metadata = tamanio_metadata;
        this.estrategia = estrategia;
    }

    public Header() { // header con valores vacios
        this.identificador_unico = 0;
        this.version_formato = 1; // version inicial
        this.nombre_archivo = new char[50];
        this.cantidad_campos = 0;
        this.offsetDatos = 0;
        this.offset_availList = 0;
        this.cantidad_registros = 0;
        this.registros_actives = 0;
        this.tamanio_metadata = 0;
        this.estrategia = 0;
    }

    public int getIdentificador_unico() {
        return identificador_unico;
    }

    public int getVersion_formato() {
        return version_formato;
    }

    public char[] getNombre_archivo() {
        return nombre_archivo;
    }

    public int getCantidad_campos() {
        return cantidad_campos;
    }

    public long getOffsetDatos() {
        return offsetDatos;
    }

    public long getOffset_availList() {
        return offset_availList;
    }

    public int getCantidad_registros() {
        return cantidad_registros;
    }

    public int getRegistros_actives() {
        return registros_actives;
    }

    public int getTamanio_metadata() {
        return tamanio_metadata;
    }

    public byte getEstrategia() {
        return estrategia;
    }

    public void setIdentificador_unico(int identificador_unico) {
        this.identificador_unico = identificador_unico;
    }

    public void setVersion_formato(int version_formato) {
        this.version_formato = version_formato;
    }

    public void setNombre_archivo(char[] nombre_archivo) {
        this.nombre_archivo = nombre_archivo;
    }

    public void setCantidad_campos(int cantidad_campos) {
        this.cantidad_campos = cantidad_campos;
    }

    public void setOffsetDatos(long offsetDatos) {
        this.offsetDatos = offsetDatos;
    }

    public void setOffset_availList(long offset_availList) {
        this.offset_availList = offset_availList;
    }

    public void setCantidad_registros(int cantidad_registros) {
        this.cantidad_registros = cantidad_registros;
    }

    public void setRegistros_actives(int registros_actives) {
        this.registros_actives = registros_actives;
    }

    public void setTamanio_metadata(int tamanio_metadata) {
        this.tamanio_metadata = tamanio_metadata;
    }

    public void setEstrategia(byte estrategia) {
        this.estrategia = estrategia;
    }
    
    // Asignar el nombre del archivo
    public void setNombre_archivo(String nombre) {
        for (int i = 0; i < this.nombre_archivo.length; i++) {
            if (i < nombre.length()) {
                this.nombre_archivo[i] = nombre.charAt(i);
            } else {
                this.nombre_archivo[i] = '\0'; // eje = 'e' 'n' 'v' 'i' 'o' '0' '0' '0' ... hasta llegar a 49
            }
        }
    }
    
    // obtener el nombre del archivo -> string
    public String getNombre_archivo_String() {
        String cadena = "";
        for (char c : nombre_archivo) {
            if (c!= '\0') { // sin incluir lo nulo
                cadena += c;
            } else {
                break;
            }
        }
        return cadena;
    }
    
    // generar el id unico
    public void setIdentificador_unico(String nombre) {
        this.identificador_unico = 0;
        for (int i = 0; i < nombre.length() && i < 4; i++) {
            this.identificador_unico = (this.identificador_unico << 8) | nombre.charAt(i);
        }
    }

    @Override
    public String toString() {
        return "ID: " + identificador_unico + " | Version: " + version_formato + " | Nombre: " + getNombre_archivo_String() + " | Campos: " + cantidad_campos + " | Offset Datos: " + offsetDatos + " | Offset AvailList: " + offset_availList + " | Total Registros: " + cantidad_registros + 
                " | Registros Activos: " + registros_actives + " | Tamanio Metadata: " + tamanio_metadata + " | Estrategia: " + estrategia;
    }
}
