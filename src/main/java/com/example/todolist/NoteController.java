package com.example.todolist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@CrossOrigin
@RequestMapping(value = "/note")
public class NoteController {
    @Autowired
    private NoteService noteService;

    @GetMapping("/add")
    public ModelAndView addPage() {
        ModelAndView result = new ModelAndView("add-note");
        result.addObject("note", new Note());
        return result;
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String add(@ModelAttribute Note note) {
        noteService.add(note);
        return "redirect:/note/list";
    }

    @GetMapping("/list")
    public ModelAndView list() {
        ModelAndView result = new ModelAndView("all-notes");
        result.addObject("notes", noteService.listAll());
        return result;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        noteService.deleteById(id);
        return "redirect:/note/list";
    }

    @GetMapping("/edit")
    public ModelAndView editPage(@RequestParam Long id) {
        ModelAndView result = new ModelAndView("edit-note");
        result.addObject("note", noteService.getById(id));
        return result;
    }

    @PostMapping("/edit/{id}")
    public String edit(@ModelAttribute Note note) {
        noteService.update(note);
        return "redirect:/note/list";
    }
}