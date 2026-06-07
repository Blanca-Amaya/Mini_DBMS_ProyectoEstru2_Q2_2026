/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mini_dbms_proyectoestru2_q2_2026;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author bamay
 */

/**
 * Metadata = Header + Tabla de Campos (DescriptorCampos[]) + AvailList
 *
 * Estructura física del archivo en disco:
 *
 * - HEADER  (141 bytes fijos)
 * 
 * - TABLA DE CAMPOS  (N × 38 bytes)
 * 
 * - REGISTROS DE DATOS
 * 
 * La AvailList se serializa al FINAL del archivo cuando se persiste
 *
 * Header = 141 bytes
 *   int  identificador_unico  =  4 bytes
 *   int  version_formato      =  4 bytes
 *   char[50] nombre_archivo   = 100 bytes (writeChar, 2 bytes/char)
 *   int  cantidad_campos      =  4 bytes
 *   long offsetDatos          =  8 bytes
 *   long offset_availList     =  8 bytes
 *   int  cantidad_registros   =  4 bytes
 *   int  registros_actives    =  4 bytes
 *   int  tamanio_metadata     =  4 bytes
 *   byte estrategia           =  1 byte
 *
 * DescriptorCampos = 38 bytes
 *   byte[25] nombre_campo     = 25 bytes (1 byte/char)
 *   byte  tipo                =  1 byte
 *   int   tamanio_max         =  4 bytes
 *   byte  longitud            =  1 byte
 *   byte  nullable            =  1 byte
 *   byte  llave_primaria      =  1 byte
 *   byte  llave_secundaria    =  1 byte
 *   int   posicion_logica     =  4 bytes
 */
public class Metadata { // header + tablas + indices + availlist
    
    public static final int TAMANIO_HEADER       = 141;
    public static final int TAMANIO_NOMBRE_CAMPO = 25;
    public static final int TAMANIO_DESCRIPTOR   = 38;
    public static final int TAMANIO_NODO_AVAIL   = 12;
    
    private Header header; 
    private DescriptorCampos[] tabla_campos; 
    private AvailList availList; 
    // private indice
    
    public Metadata() {
        this.header      = new Header();
        this.tabla_campos = new DescriptorCampos[0];
        this.availList   = new AvailList();
    }
 
    public Metadata(Header header, DescriptorCampos[] tabla_campos, AvailList availList) {
        this.header       = header;
        this.tabla_campos = tabla_campos;
        this.availList    = availList;
    }
    
    /**
     * Metadata para un archivo recién creado
     * para nombre     nombre del archivo (sin extensión)
     * para estrategia 0 = best fit, 1 = worst fit
     */
    public void inicializar(String nombre, byte estrategia) {
        header.setIdentificador_unico(nombre);
        header.setNombre_archivo(nombre);
        header.setVersion_formato(1);
        header.setEstrategia(estrategia);
        header.setCantidad_campos(0);
        header.setCantidad_registros(0);
        header.setRegistros_actives(0);
        header.setOffset_availList(0);
        tabla_campos = new DescriptorCampos[0];
        availList    = new AvailList();
    }
    
    /**
     * Se escribe toda la metadata al inicio del archivo (posición 0)
     * Recalcula offsetDatos y tamanio_metadata antes de persistir
     */
    public void escribirMetadata(RandomAccessFile raf) throws IOException {
        int cantCampos      = (tabla_campos != null) ? tabla_campos.length : 0;
        int tamanioMetadata = TAMANIO_HEADER + (cantCampos * TAMANIO_DESCRIPTOR);
 
        header.setCantidad_campos(cantCampos);
        header.setTamanio_metadata(tamanioMetadata);
        header.setOffsetDatos(tamanioMetadata); // datos empiezan justo después de la metadata
 
        raf.seek(0);
        escribirHeader(raf);
        for (int i = 0; i < cantCampos; i++) {
            escribirDescriptorCampo(raf, tabla_campos[i]);
        }
    }
    
    public void escribirSoloHeader(RandomAccessFile raf) throws IOException {
        raf.seek(0);
        escribirHeader(raf);
    }
        
    public void escribirAvailList(RandomAccessFile raf) throws IOException { // Serializa la AvailList al final del archivo y actualiza offset en header
        long posInicio = raf.length();
        raf.seek(posInicio);
        int cant = availList.getTamanio();
        raf.writeInt(cant);
        NodoAvail actual = availList.getCabeza();
        while (actual != null) {
            raf.writeLong(actual.posicion);
            raf.writeInt(actual.tamanio);
            actual = actual.siguiente;
        }
        header.setOffset_availList(posInicio);
        escribirSoloHeader(raf);
    }
    
    private void escribirHeader(RandomAccessFile raf) throws IOException {
        raf.writeInt(header.getIdentificador_unico());
        raf.writeInt(header.getVersion_formato());
        char[] nombre = header.getNombre_archivo();
        for (int i = 0; i < 50; i++) {
            raf.writeChar(i < nombre.length ? nombre[i] : '\0');
        }
        raf.writeInt(header.getCantidad_campos());
        raf.writeLong(header.getOffsetDatos());
        raf.writeLong(header.getOffset_availList());
        raf.writeInt(header.getCantidad_registros());
        raf.writeInt(header.getRegistros_actives());
        raf.writeInt(header.getTamanio_metadata());
        raf.writeByte(header.getEstrategia());
    }
    
    private void escribirDescriptorCampo(RandomAccessFile raf, DescriptorCampos dc) throws IOException {
        String nombre = dc.getNombre_campo();
        byte[] buf = new byte[TAMANIO_NOMBRE_CAMPO];
        for (int i = 0; i < TAMANIO_NOMBRE_CAMPO; i++) {
            buf[i] = (i < nombre.length()) ? (byte) nombre.charAt(i) : 0;
        }
        raf.write(buf);
        raf.writeByte(dc.getTipo());
        raf.writeInt(dc.getTamanio_max());
        raf.writeByte(dc.getLongitud());
        raf.writeByte(dc.getNullable());
        raf.writeByte(dc.getLlave_primaria());
        raf.writeByte(dc.getLlave_secundaria());
        raf.writeInt(dc.getPosicion_logica());
    }
        
    public static Metadata leerMetadata(RandomAccessFile raf) throws IOException { // Lee toda la metadata desde el inicio del archivo
        raf.seek(0);
        Header header = leerHeader(raf);
 
        int cantCampos = header.getCantidad_campos();
        DescriptorCampos[] tabla = new DescriptorCampos[cantCampos];
        for (int i = 0; i < cantCampos; i++) {
            tabla[i] = leerDescriptorCampo(raf);
        }
 
        AvailList avail = new AvailList();
        long offsetAvail = header.getOffset_availList();
        if (offsetAvail > 0 && offsetAvail < raf.length()) {
            avail = leerAvailList(raf, offsetAvail);
        }
 
        return new Metadata(header, tabla, avail);
    }
    
    private static Header leerHeader(RandomAccessFile raf) throws IOException {
        Header h = new Header();
        h.setIdentificador_unico(raf.readInt());
        h.setVersion_formato(raf.readInt());
        char[] nombre = new char[50];
        for (int i = 0; i < 50; i++) nombre[i] = raf.readChar();
        h.setNombre_archivo(nombre);
        h.setCantidad_campos(raf.readInt());
        h.setOffsetDatos(raf.readLong());
        h.setOffset_availList(raf.readLong());
        h.setCantidad_registros(raf.readInt());
        h.setRegistros_actives(raf.readInt());
        h.setTamanio_metadata(raf.readInt());
        h.setEstrategia(raf.readByte());
        return h;
    }
    
    private static DescriptorCampos leerDescriptorCampo(RandomAccessFile raf) throws IOException {
        DescriptorCampos dc = new DescriptorCampos();
        byte[] buf = new byte[TAMANIO_NOMBRE_CAMPO];
        raf.readFully(buf);
        StringBuilder sb = new StringBuilder();
        for (byte b : buf) {
            if (b == 0) break;
            sb.append((char) b);
        }
        dc.setNombre_campo(sb.toString());
        dc.setTipo(raf.readByte());
        dc.setTamanio_max(raf.readInt());
        dc.setLongitud(raf.readByte());
        dc.setNullable(raf.readByte());
        dc.setLlave_primaria(raf.readByte());
        dc.setLlave_secundaria(raf.readByte());
        dc.setPosicion_logica(raf.readInt());
        return dc;
    }
 
    private static AvailList leerAvailList(RandomAccessFile raf, long offset) throws IOException {
        raf.seek(offset);
        AvailList lista = new AvailList();
        int cant = raf.readInt();
        for (int i = 0; i < cant; i++) {
            long posicion = raf.readLong();
            int  tamanio  = raf.readInt();
            lista.agregarAlFinal(posicion, tamanio);            
        }
        return lista;
    }
        
    public void agregarCampo(DescriptorCampos dc) { // agrega un DescriptorCampos al arreglo y actualiza el header
        int n = (tabla_campos != null) ? tabla_campos.length : 0;
        DescriptorCampos[] nueva = new DescriptorCampos[n + 1];
        if (n > 0) System.arraycopy(tabla_campos, 0, nueva, 0, n);
        dc.setPosicion_logica(n);
        nueva[n] = dc;
        tabla_campos = nueva;
        header.setCantidad_campos(tabla_campos.length);
    }
     
    public void actualizarCampo(RandomAccessFile raf, int indiceCampo) throws IOException { // reescribe un solo DescriptorCampos en su posición física
        if (indiceCampo < 0 || indiceCampo >= tabla_campos.length) return;
        raf.seek(TAMANIO_HEADER + (long) indiceCampo * TAMANIO_DESCRIPTOR);
        escribirDescriptorCampo(raf, tabla_campos[indiceCampo]);
    }
     
    public void eliminarCampo(RandomAccessFile raf, int indiceCampo) throws IOException { // se elimina un campo, para recalcular posiciones logicas y reescribe la metadata
        if (indiceCampo < 0 || indiceCampo >= tabla_campos.length) return;
        int n = tabla_campos.length;
        DescriptorCampos[] nueva = new DescriptorCampos[n - 1];
        int dest = 0;
        for (int i = 0; i < n; i++) {
            if (i == indiceCampo) continue;
            nueva[dest] = tabla_campos[i];
            nueva[dest].setPosicion_logica(dest);
            dest++;
        }
        tabla_campos = nueva;
        header.setCantidad_campos(tabla_campos.length);
        escribirMetadata(raf);
    }
    
    public void registroInsertado(RandomAccessFile raf) throws IOException {
        header.setCantidad_registros(header.getCantidad_registros() + 1);
        header.setRegistros_actives(header.getRegistros_actives() + 1);
        escribirSoloHeader(raf);
    }
 
    public void registroEliminado(RandomAccessFile raf) throws IOException {
        int activos = header.getRegistros_actives();
        if (activos > 0) header.setRegistros_actives(activos - 1);
        escribirSoloHeader(raf);
    }
    
    public Header getHeader(){ 
        return header; 
    }
    
    public void   setHeader(Header h){ 
        this.header = h; 
    }
    
    public DescriptorCampos[] getTablaCampos(){ 
        return tabla_campos; 
    }
    
    public void   setTablaCampos(DescriptorCampos[] t){ 
        this.tabla_campos = t; 
    }
    
    public AvailList getAvailList(){ 
        return availList; 
    }
    
    public void   setAvailList(AvailList a){ 
        this.availList = a; 
    }
 
}
