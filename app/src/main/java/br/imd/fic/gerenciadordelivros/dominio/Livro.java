package br.imd.fic.gerenciadordelivros.dominio;

import java.io.Serializable;

public class Livro  implements Serializable {

    private long id;
    private String titulo;
    private String autor;
    private String editora;
    private int emprestado;

    public Livro(long id, String titulo, String autor, String editora, int emprestado) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.emprestado = emprestado;
    }

    public Livro(String titulo, String autor, String editora, int emprestado) {
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.emprestado = emprestado;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public int getEmprestado() {
        return emprestado;
    }

    public void setEmprestado(int emprestado) {
        this.emprestado = emprestado;
    }
}
