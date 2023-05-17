package com.example.demo.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Libro;

@Repository("librosRepository")
public interface LibroRepository extends JpaRepository<Libro, Serializable>{
	public abstract Libro findById(int id);
	public abstract Libro findByISBN(String isbn);
	public abstract Libro findByTitulo(String titulo);
	public abstract List<Libro> findByAutor(String autor);
}
