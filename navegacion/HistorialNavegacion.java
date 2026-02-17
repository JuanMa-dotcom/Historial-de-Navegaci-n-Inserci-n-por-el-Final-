/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.navegacion;

/**
 *
 * @author jv134
 */
class HistorialNavegacion {
    private Nodo cabeza;
    private int tamanio;

    public HistorialNavegacion() {
        this.cabeza = null;
        this.tamanio = 0;
    }

    // Insertar al frente (sitio mas reciente)
    public void agregarDireccion(String url) {
        Nodo nuevo = new Nodo(url);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            nuevo.siguiente = cabeza;
            cabeza.anterior = nuevo;
            cabeza = nuevo;
        }
        tamanio++;
    }

    // Mostrar la direccion actual (cabeza)
    public String mostrarActual() {
        if (cabeza == null) return "No hay paginas en el historial.";
        return cabeza.url;
    }

    // Recorrer todos los nodos de la lista
    public java.util.ArrayList<String> obtenerHistorial() {
        java.util.ArrayList<String> lista = new java.util.ArrayList<>();
        Nodo actual = cabeza;
        int pos = 1;
        while (actual != null) {
            lista.add(pos + ". " + actual.url);
            actual = actual.siguiente;
            pos++;
        }
        return lista;
    }

    // Eliminar cabeza (simula retroceder)
    public void eliminarCabeza() {
        if (cabeza == null) return;
        cabeza = cabeza.siguiente;
        if (cabeza != null) cabeza.anterior = null;
        tamanio--;
    }

    public boolean estaVacio() {
        return cabeza == null;
    }

    public int getTamanio() {
        return tamanio;
    }
}