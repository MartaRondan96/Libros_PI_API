package com.example.demo.controller;

import java.util.List;

import com.example.demo.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.model.ComentarioDTO;
import com.example.demo.model.LibroDTO;
import com.example.demo.model.ValoracionDTO;
import com.example.demo.service.ComentarioService;
import com.example.demo.service.LibroService;
import com.example.demo.service.ValoracionService;
import com.example.demo.serviceImpl.UserService;

@RestController
@RequestMapping("/api")
public class RestLibros {
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private LibroService librosService;
	@Autowired
	private LibroRepository librosRepository;
	@Autowired
	private ValoracionService valoracionService;
	@Autowired
	private ComentarioService comentarioService;
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	//Endpoint que recupera todos los libros
	@GetMapping("/libros")
	public ResponseEntity<?> getAllLibros() {
		boolean exist = librosService.ListAllLibros()!=null;
		if(exist) {
			List<LibroDTO> libro=librosService.ListAllLibros();
			return ResponseEntity.ok(libro);
		}
		else
			return ResponseEntity.noContent().build();
	}
	//Endpoint que recupera el libro por Id
	@GetMapping("/libros/{id}")
	public ResponseEntity<?> getLibroById(@PathVariable int id) {
		boolean exist = librosService.findLibro(id)!=null;
		if(exist) {
			LibroDTO libro=librosService.findLibro(id);
			return ResponseEntity.ok(libro);
		}
		else
			return ResponseEntity.noContent().build();
	}

	//Endpoint que recupera el libro por ISBN 
	@GetMapping("/libros/ISBN/{isbn}")
	public ResponseEntity<?> getLibroByISBN(@PathVariable String isbn) {
		boolean exist = librosService.findLibroByISBN(isbn)!=null;
		if(exist) {
			LibroDTO libro=librosService.findLibroByISBN(isbn);
			return ResponseEntity.ok(libro);
		}
		else
			return ResponseEntity.noContent().build();
	}
	
	//Endpoint que recupera el libro por titulo 
	@GetMapping("/libros/titulo/{titulo}")
	public ResponseEntity<?> getLibroByTitulo(@PathVariable String titulo) {
		boolean exist = librosService.findLibroByTitulo(titulo)!=null;
		if(exist) {
			LibroDTO libro=librosService.findLibroByTitulo(titulo);
			return ResponseEntity.ok(libro);
		}
		else
			return ResponseEntity.noContent().build();
	}
	//Recuperar libro por autor
	@GetMapping("/libros/autor/{autor}")
	public ResponseEntity<?> getLibroByAutor(@PathVariable String autor) {
		boolean exist = librosService.findLibroByAutor(autor)!=null;
		if(exist) {
			List<LibroDTO> libro=librosService.findLibroByAutor(autor);
			return ResponseEntity.ok(libro);
		}
		else
			return ResponseEntity.noContent().build();
	}
	
	//Enpoint añadir libro favorito
	@PostMapping("/libros/addFav/{idLibro}")
	private ResponseEntity<?> addFav(@PathVariable int idLibro) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.addFav(idLibro,username);
		return ResponseEntity.ok().build();
	}
	//Endpoint que elimina libro favorito
	@PutMapping("/libros/deleteFav/{idLibro}")
	private ResponseEntity<?> deleteFav(@PathVariable int idLibro) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User user = userService.deleteFav(idLibro,username);
		return ResponseEntity.ok().build();
	}
	//Endpoint Recuperar lista libros favoritos
	@GetMapping("/libros/getFavs")
	private ResponseEntity<?> getFavs() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
        User u = userService.findUser(username);
        List<Integer> listFavs = userService.getFavs(u.getId());
		return ResponseEntity.ok(listFavs);
	}
	
	//Endpoint añadir nota a libro
	@PostMapping("/libros/nota")
	private ResponseEntity<?> addNota(@RequestBody ValoracionDTO v){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User u = userService.findUser(username);
		v.setIdUsuario(u.getId());
		valoracionService.addValoracion(v);
		return ResponseEntity.ok(v);
	}
	
	//Endpoint leer nota de libro x idLibro
	@GetMapping("/libros/nota/{id}")
	private ResponseEntity<?> readNota(@PathVariable int idLibro){
		valoracionService.findValoracionByIdLibro(idLibro);
		return ResponseEntity.ok().build();
	}

	
	//Endpoint muestra todos los comentarios de un libro
	@GetMapping("/libros/comentario/{idLibro}")
	private ResponseEntity<?> readComentariosByIdLibro(@PathVariable int idLibro){
		List<ComentarioDTO> listComentarios = comentarioService.getComentariosLibro(idLibro);
		return ResponseEntity.ok(listComentarios);
	}
	
	//Endpoint añadir comentario a libro 
	@PostMapping("/libros/comentario")
	private ResponseEntity<?> addComentarioByIdLibro(@RequestBody ComentarioDTO comentario){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User u = userService.findUser(username);
		comentario.setIdUsuario(u.getId());
		comentarioService.addComentario(comentario);
		return ResponseEntity.ok().body(comentario);
	}
}
