package com.example.demo.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.repository.LibroRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Libro;
import com.example.demo.entity.Valoracion;
import com.example.demo.model.LibroDTO;
import com.example.demo.model.ValoracionDTO;
import com.example.demo.repository.ValoracionRepository;
import com.example.demo.service.LibroService;
import com.example.demo.service.ValoracionService;
@Service("valoracionService")
public class ValoracionServiceImpl implements ValoracionService {
	@Autowired
	@Qualifier("valoracionRepository")
	private ValoracionRepository valoracionRepository;
	@Autowired
	@Qualifier("librosService")
	private LibroService libroService;

	@Autowired
	private LibroRepository librosRepository;

	@Override
	public List<ValoracionDTO> ListAllValoracion() {
		return valoracionRepository.findAll().stream()
				.map(c->transform(c)).collect(Collectors.toList());
	}

	@Override
	public Valoracion addValoracion(ValoracionDTO valoracionDTO) {
		valoracionRepository.save(transform(valoracionDTO));
		System.out.println(valoracionDTO);
		System.out.println(valoracionDTO.getIdLibro());
		List<ValoracionDTO> notaList = getListNotaByIdLibro(valoracionDTO.getIdLibro());
		System.out.println(notaList);
		float nMedia=0F;
		for(ValoracionDTO v : notaList) 
			nMedia+=v.getPuntos();
		float media = nMedia/notaList.size();
		LibroDTO libroAct = libroService.findLibro(valoracionDTO.getIdLibro());
		libroAct.setNota(media);
		libroService.updateLibro(libroAct);
		return transform(valoracionDTO);
	}

	@Override
	public int removeValoracion(int id) {
		valoracionRepository.deleteById(id);
		return 0;
	}
//metodo para eliminar valoracion (no se usa)
	@Override
	public Valoracion updateValoracion(ValoracionDTO valoracionDTO) {
		valoracionRepository.save(transform(valoracionDTO));
		List<ValoracionDTO> notaList = getListNotaByIdLibro(valoracionDTO.getIdLibro());
		float nMedia=0F;
		for(ValoracionDTO v : notaList) 
			nMedia+=v.getPuntos();
		float media = nMedia/notaList.size();
		LibroDTO libroAct = libroService.findLibro(valoracionDTO.getIdLibro());
		libroAct.setNota(media);
		libroService.updateLibro(libroAct);
		return transform(valoracionDTO);
		
	}

	@Override
	public Valoracion transform(ValoracionDTO valoracionDTO) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(valoracionDTO, Valoracion.class);
	}

	@Override
	public ValoracionDTO transform(Valoracion valoracion) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(valoracion, ValoracionDTO.class);
	}

	@Override
	public List<Valoracion> findValoracionByIdLibro(int idLibro) {
		return valoracionRepository.findByIdLibro(idLibro);
	}

	@Override
	public ValoracionDTO findValoracion(int id) {
		return transform(valoracionRepository.findById(id));
	}

	@Override
	public List<ValoracionDTO> getListNotaByIdLibro(int idLibro) {
		Libro l = librosRepository.findById(idLibro);
		List<ValoracionDTO> listNota = valoracionRepository.findByIdLibro(l).stream().map(c -> transform(c))
				.collect(Collectors.toList());

		return listNota;
	}
}
