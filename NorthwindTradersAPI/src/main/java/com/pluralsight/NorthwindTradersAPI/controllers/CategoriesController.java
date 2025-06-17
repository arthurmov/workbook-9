package com.pluralsight.NorthwindTradersAPI.controllers;

import com.pluralsight.NorthwindTradersAPI.dao.CategoryDAO;
import com.pluralsight.NorthwindTradersAPI.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoriesController {

    private CategoryDAO categoryDAO;

    @Autowired
    public CategoriesController(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @RequestMapping(path="/categories", method = RequestMethod.GET)
    public List<Category> getCategories(){
        return categoryDAO.getAllCategories();
    }

    @RequestMapping(path="/categories/{categoryId}", method = RequestMethod.GET)
    public Category getCategories(@PathVariable int categoryId){
        return categoryDAO.getCategoryById(categoryId);
    }
}
