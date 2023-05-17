package com.example.demo.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Comentario;
@Repository("comentarioRepository")
public interface ComentarioRepository extends JpaRepository<Comentario, Serializable>{
	public abstract List<Comentario> findByIdLibro(int idLibro);
	public abstract Comentario findById(int id);
}
