package dev.jim.recipe.controller;

import dev.jim.recipe.domain.Category;
import dev.jim.recipe.domain.UnitOfMeasure;
import dev.jim.recipe.repository.CategoryRepository;
import dev.jim.recipe.repository.UnitOfMeasureRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {

    @RequestMapping({"/", "/index"})
    public String getIndexPage() {


        return "index";
    }

}
