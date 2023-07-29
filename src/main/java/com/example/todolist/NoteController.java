package com.example.todolist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/note")
public class NoteController {
    @Autowired
    private NoteService noteService;

    @GetMapping("/add")
    public ModelAndView addPage() {
        return new ModelAndView("add-note");
    }

    @PostMapping("/add")
    public String add(@RequestParam String title,
                      @RequestParam String content) {
        noteService.add(new Note(title, content));
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
    public String edit(@PathVariable Long id,
                       @RequestParam String title,
                       @RequestParam String content) {
        noteService.update(new Note(id, title, content));
        return "redirect:/note/list";
    }
}