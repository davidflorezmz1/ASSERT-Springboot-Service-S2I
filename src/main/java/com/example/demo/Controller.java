package com.example.demo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.entity.Trabajador;
import com.example.demo.linkconexion.Conexion;

@RestController

public class Controller {
	
	@Autowired
	private Conexion conexion;
	
	@GetMapping("/hola")
	public String getEmployees() {
		return "Bienvenido al Servicio Web de gestion de trabajadores";
	}
	
	@GetMapping("/trabajadores")
	public Iterable<Trabajador> getTrabajadores(){
		return conexion.findAll();
	}
	
	@GetMapping("/trabajador")
	public Optional<Trabajador> getTrabajador(Long id){
		return conexion.findById(id);		
	}
	
	@PostMapping("/crear")
	public String putTrabajador(String nombres, String apellidos, String telefono, String email){		
		Trabajador trabajador = new Trabajador();
		trabajador.setNombre(nombres);
		trabajador.setApellido(apellidos);
		trabajador.setTelefono(telefono);
		trabajador.setEmail(email);
		conexion.save(trabajador);	
		return "Operacion realizada";		
	}
	
	@PutMapping("/editar")
	public String modificarTrabajador(Long id, String nombres, String apellidos, String telefono, String email) {
		
		if(conexion.findById(id).isPresent()) {
			Trabajador existente = conexion.findById(id).get();
			existente.setNombre(nombres);
			existente.setApellido(apellidos);
			existente.setTelefono(telefono);
			existente.setEmail(email);
			conexion.save(existente);
			return "Modificacion realizada";
		}
		else {
			return "No se pudo realizar la modificacion";
		}		
	}
	
	@GetMapping("/eliminar")
	public String deleteTrabajador(Long id){
		conexion.deleteById(id);	
		return "Eliminacion realizada";		
	}
	
	
	@GetMapping("/cantidad")
	public long getCantidadTrabajadores(){
		return conexion.count();
	}
	
}
