package org.example.controllers;


import jakarta.validation.Valid;
import org.example.dao.PersonDao;
import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Controller
@RequestMapping("/people")
public class PeopleController {
   @Autowired
    private PersonDao personDao;

    public PeopleController(PersonDao personDao) {
        this.personDao = personDao;
    }

    @GetMapping()
     public  String index(Model model){
         // Получим всех людей из Dao класса и передадим на отображение в представление
         model.addAttribute("people",personDao.index());
          return "people/index";
     }


  @GetMapping("/{id}")
     public String show(@PathVariable("id")int id , Model model) throws SQLException {

          // Получим одного человека по его id из Дао и передадим на отображение в представленеи
        model.addAttribute("person",personDao. show(id));
         return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(Model model){
        model.addAttribute("person",new Person());
        return "people/new";
    }
@PostMapping()
public String create (@ModelAttribute("person")  @Valid Person person, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "people/new";
       PersonDao.save(person);
      return "redirect:/people";
}

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDao.delete(id);
        return "redirect:/people";
    }
}
