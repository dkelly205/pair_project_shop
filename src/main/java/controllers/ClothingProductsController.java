package controllers;

import db.DBHelper;
import models.Clothing;
import models.Food;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

public class ClothingProductsController {

    public ClothingProductsController() {
        this.setupEndPoints();
    }

    private void setupEndPoints() {
        get("/clothing-products", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Clothing> clothingProducts = DBHelper.getAll(Clothing.class);
            model.put("clothingProducts", clothingProducts);
            model.put("template", "templates/clothingProducts/index.vtl");
            String loggedInUser = LoginController.getLoggedInUsername(req, res);
            model.put("user", loggedInUser);

            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

        get("/clothing-products/:id", (req, res) -> {
            String strId = req.params(":id");
            Integer intId = Integer.parseInt(strId);
            Clothing clothingProduct = DBHelper.find(Clothing.class, intId);

            Map<String, Object> model = new HashMap<>();

            model.put("clothingProduct", clothingProduct);
            model.put("template", "templates/clothingProducts/show.vtl");
            String loggedInUser = LoginController.getLoggedInUsername(req, res);
            model.put("user", loggedInUser);

            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

        post ("/clothing-products/:id/delete", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            Clothing productToDelete = DBHelper.find(Clothing.class, id);
            DBHelper.delete(productToDelete);
            res.redirect("/clothing-products");
            return null;
        }, new VelocityTemplateEngine());
    }
}
