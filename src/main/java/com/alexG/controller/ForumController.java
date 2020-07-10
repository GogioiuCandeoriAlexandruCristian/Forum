package com.alexG.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alexG.model.CategoryModel;
import com.alexG.service.Service;

@RestController
@RequestMapping("/api")
public class ForumController {

    @Autowired
    private Service service;

    @GetMapping("/notes")
    public List<CategoryModel> getAllNotes() {
        return service.findAllCategories();
    }

}
