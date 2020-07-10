package com.alexG.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alexG.model.CategoryModel;
import com.alexG.model.TechnologyModel;
import com.alexG.service.Service;

@RestController
@RequestMapping("/api")
public class ForumController {

    @Autowired
    private Service service;

    @GetMapping("/categories")
    public List<CategoryModel> getAllCategories() {
        return service.findAllCategories();
    }

    @GetMapping("/technologies")
    public List<TechnologyModel> getTechnologies(@RequestParam(name = "categId", required = true) Long categId) {
        return service.findTechnologies(categId);
    }

}
