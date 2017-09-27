package com.example.rako.bankingapp;

/**
 * Created by rako on 27/09/2017.
 */

public class Recipe {
    private String tituloReceita;
    private int totalIngredientes;
    private int totalPassos;

    public Recipe(String tituloReceita) {
        this.tituloReceita = tituloReceita;
    }

    public String getTituloReceita() {
        return tituloReceita;
    }

    public void setTituloReceita(String tituloReceita) {
        this.tituloReceita = tituloReceita;
    }

    public int getTotalIngredientes() {
        return totalIngredientes;
    }

    public void setTotalIngredientes(int totalIngredientes) {
        this.totalIngredientes = totalIngredientes;
    }

    public int getTotalPassos() {
        return totalPassos;
    }

    public void setTotalPassos(int totalPassos) {
        this.totalPassos = totalPassos;
    }
}
