package com.upemor.sesioncuatro.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.upemor.sesioncuatro.model.Role;
import com.upemor.sesioncuatro.repository.RoleRepository;

@Controller
@RequestMapping("/roles")
public class RoleController {

	@Autowired
	private RoleRepository roleRepository;
	
	@GetMapping("")
	public String home(Model model) {		
		return "roles/home";
	}
	
	@GetMapping("/list")
	public String roleList(Model model) {
		List<Role> roles = roleRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
		model.addAttribute("roles",roles);
		return "roles/roleList";
	}
	
	@GetMapping("/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
	    Role role= roleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid role Id:" + id));
	    //System.out.println(member.getName());
	    model.addAttribute("role", role);
	    return "roles/roleUpdate";
	}
	
	@PostMapping("/update/{id}")
	public String updateRole(@PathVariable("id") Integer id, @Valid Role role, BindingResult result, Model model) {
	    if (result.hasErrors()) {
	        role.setId(id);
	        return "roles/update";
	    }
	    roleRepository.save(role);
	    model.addAttribute("role", roleRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
	    return "redirect:/roles/list";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteRole(@PathVariable("id") Integer id, Model model) {
	    Role role = roleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid role Id:" + id));
	    roleRepository.delete(role);
	    model.addAttribute("roles", roleRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
	    return "roles/roleList";
	}
	
	@GetMapping("/create")
	public String roleCreateInit(Model model) {
		model.addAttribute("role",new Role());
		return "roles/roleCreate";
	}
	
	@PostMapping("/create")
	public String roleCreate(@ModelAttribute Role role, Model model) {
		roleRepository.save(role);
		model.addAttribute("role",role);
		return "roles/result";
	}
}
