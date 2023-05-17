package com.example.demo.model;

import com.example.demo.entity.Libro;
import com.example.demo.entity.User;

public class ValoracionDTO {
	
	private int id;
    private int idUsuario;
    private int idLibro;
	private float puntos;
	
	public ValoracionDTO() {
		super();
	}

	public ValoracionDTO(int id, int idUsuario, int idLibro, float puntos) {
		this.id = id;
		this.idUsuario = idUsuario;
		this.idLibro = idLibro;
		this.puntos = puntos;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getIdLibro() {
		return idLibro;
	}

	public void setIdLibro(int idLibro) {
		this.idLibro = idLibro;
	}

	public float getPuntos() {
		return puntos;
	}

	public void setPuntos(float puntos) {
		this.puntos = puntos;
	}

	@Override
	public String toString() {
		return "ValoracionDTO{" +
				"id=" + id +
				", idUsuario=" + idUsuario +
				", idLibro=" + idLibro +
				", puntos=" + puntos +
				'}';
	}
}
