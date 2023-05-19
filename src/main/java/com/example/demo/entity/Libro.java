package com.example.demo.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Libro {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private int id;
	private String titulo;
	private String autor;
	private String resumen;
	private String ISBN;
	private int pag;
	private float nota;
	private String imagen;
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idLibro")
    private List<Comentario> comentariosList;
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idLibro")
    private List<Valoracion> listValoraciones;
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idLibro")
    private List<PagActual> listPagina;

	public Libro() {
	}

	public Libro(int id, String titulo, String autor, String resumen, String ISBN, int pag, float nota, String imagen, List<Comentario> comentariosList, List<Valoracion> listValoraciones, List<PagActual> listPagina) {
		this.id = id;
		this.titulo = titulo;
		this.autor = autor;
		this.resumen = resumen;
		this.ISBN = ISBN;
		this.pag = pag;
		this.nota = nota;
		this.imagen = imagen;
		this.comentariosList = comentariosList;
		this.listValoraciones = listValoraciones;
		this.listPagina = listPagina;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public String getResumen() {
		return resumen;
	}

	public void setResumen(String resumen) {
		this.resumen = resumen;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String ISBN) {
		this.ISBN = ISBN;
	}

	public int getPag() {
		return pag;
	}

	public void setPag(int pag) {
		this.pag = pag;
	}

	public float getNota() {
		return nota;
	}

	public void setNota(float nota) {
		this.nota = nota;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public List<Comentario> getComentariosList() {
		return comentariosList;
	}

	public void setComentariosList(List<Comentario> comentariosList) {
		this.comentariosList = comentariosList;
	}

	public List<Valoracion> getListValoraciones() {
		return listValoraciones;
	}

	public void setListValoraciones(List<Valoracion> listValoraciones) {
		this.listValoraciones = listValoraciones;
	}

	public List<PagActual> getListPagina() {
		return listPagina;
	}

	public void setListPagina(List<PagActual> listPagina) {
		this.listPagina = listPagina;
	}
}
