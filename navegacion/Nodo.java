/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.navegacion;

/**
 *
 * @author jv134
 */
class Nodo {
    String url;
    Nodo siguiente;
    Nodo anterior;

    public Nodo(String url) {
        this.url = url;
        this.siguiente = null;
        this.anterior = null;
    }
}