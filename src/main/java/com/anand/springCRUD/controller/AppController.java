package com.anand.springCRUD.controller;

import java.util.List;

import com.anand.springCRUD.model.Role;
import com.anand.springCRUD.model.User;
import com.anand.springCRUD.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.anand.springCRUD.model.Student;
import com.anand.springCRUD.services.StudentServices;

@Controller
public class AppController {

    @Autowired
    StudentServices service;

    @Autowired
    UserServices userServices;

    @RequestMapping("/")
    public String viewHomePage(Model model) {
        List<Student> listStudent = service.listAll();
        model.addAttribute("listStudent", listStudent);
        return "indexStudent";
    }

    @RequestMapping("/new")
    public String newStudentPage(Model model) {
        Student student = new Student();
        model.addAttribute(student);
        return "new_student";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveStudent(@ModelAttribute("student") Student student) {
        service.save(student);
        return "redirect:/";
    }

    @RequestMapping("/edit/{sid}")
    public ModelAndView showEditStudentPage(@PathVariable(name = "sid") Long sid) {
        ModelAndView mav = new ModelAndView("edit_student");
        Student student = service.get(sid);
        mav.addObject("student", student);
        return mav;
    }

    @RequestMapping("/delete/{sid}")
    public String deleteStudentPage(@PathVariable(name = "sid") Long sid) {
        service.delete(sid);
        return "redirect:/";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "signup_form";
    }

    @PostMapping("/process_register")
    public String processRegister(User user) {
        userServices.registerDefaultUser(user);
        return "register_success";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> listUsers = userServices.listAll();
        model.addAttribute("listUsers", listUsers);
        return "users";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        User user = userServices.get(id);
        List<Role> listRoles = userServices.listRoles();
        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        return "user_form";
    }

    @PostMapping("/users/save")
    public String saveUser(User user) {
        userServices.save(user);
        return "redirect:/users";
    }

    @GetMapping("")
    public String viewHomePage() {
        return "index";
    }

}
