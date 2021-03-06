package controllers;

import db.DBHelper;
import models.Customer;
import models.PreviousPurchase;
import models.Product;
import models.Shop;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

public class PreviousPurchasesController {

    public PreviousPurchasesController() {
        this.setupEndpoints();
    }

    private void setupEndpoints() {

        get("/previous-purchases/:id/edit", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            PreviousPurchase previousPurchase = DBHelper.find(PreviousPurchase.class, id);
            List<Shop> shops = DBHelper.getAll(Shop.class);
            List<Customer> customers = DBHelper.getAll(Customer.class);
            Map<String, Object> model = new HashMap<>();
            String loggedInUser = LoginController.getLoggedInUsername(req, res);
            model.put("user", loggedInUser);
            model.put("shops", shops);
            model.put("customers", customers);
            model.put("template", "templates/previousPurchases/edit.vtl");
            model.put("previousPurchase", previousPurchase);
            if(loggedInUser.equals("admin")){
                return new ModelAndView(model, "templates/adminLayout.vtl");
            }
            else{
                return new ModelAndView(model, "templates/layout.vtl");
            }
        }, new VelocityTemplateEngine());


        get("/previous-purchases/:id/contents", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            PreviousPurchase purchase = DBHelper.find(PreviousPurchase.class, id);
            List<Product> contents = DBHelper.getAllContentsForPurchase(purchase);
            Map<String, Object> model = new HashMap<>();
            String loggedInUser = LoginController.getLoggedInUsername(req, res);
            double delivery = purchase.getTotal() - purchase.totalContentsOnly(contents);
            model.put("user", loggedInUser);
            model.put("delivery", delivery);
            model.put("contents", contents);
            model.put("purchase", purchase);
            if (loggedInUser.equals("admin")) {
                model.put("template", "templates/previousPurchases/contents.vtl");
            }
            else {
                model.put("template", "templates/accountpage/customer_previousOrders_contents.vtl");
            }
            if(loggedInUser.equals("admin")){
                return new ModelAndView(model, "templates/adminLayout.vtl");
            }
            else{
                return new ModelAndView(model, "templates/layout.vtl");
            }
            }, new VelocityTemplateEngine());


            get("/previous-purchases", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<PreviousPurchase> previousPurchases = DBHelper.getAll(PreviousPurchase.class);
            String loggedInUser = LoginController.getLoggedInUsername(req, res);
            model.put("user", loggedInUser);
            model.put("template", "templates/previousPurchases/index.vtl");
            model.put("previousPurchases", previousPurchases);
                if(loggedInUser.equals("admin")){
                    return new ModelAndView(model, "templates/adminLayout.vtl");
                }
                else{
                    return new ModelAndView(model, "templates/layout.vtl");
                }
        }, new VelocityTemplateEngine());


        get ("/previous-purchases/new", (req, res) -> {
            List<Customer> customers = DBHelper.getAll(Customer.class);
            List<Shop> shops = DBHelper.getAll(Shop.class);
            Map<String, Object> model = new HashMap<>();
            String loggedInUser = LoginController.getLoggedInUsername(req, res);
            model.put("user", loggedInUser);
            model.put("customers", customers);
            model.put("shops", shops);
            model.put("template", "templates/previousPurchases/create.vtl");
            if(loggedInUser.equals("admin")){
                return new ModelAndView(model, "templates/adminLayout.vtl");
            }
            else{
                return new ModelAndView(model, "templates/layout.vtl");
            }
        }, new VelocityTemplateEngine());


        get("/previous-purchases/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            PreviousPurchase previousPurchase = DBHelper.find(PreviousPurchase.class, id);
            Map<String, Object> model = new HashMap<>();
            String loggedInUser = LoginController.getLoggedInUsername(req, res);
            model.put("user", loggedInUser);
            model.put("previousPurchase", previousPurchase);
            model.put("template", "templates/previousPurchases/show.vtl");
            if(loggedInUser.equals("admin")){
                return new ModelAndView(model, "templates/adminLayout.vtl");
            }
            else{
                return new ModelAndView(model, "templates/layout.vtl");
            }
        }, new VelocityTemplateEngine());


        post ("/previous-purchases", (req, res) -> {
            double total = Double.parseDouble(req.queryParams("total"));
            int customerId = Integer.parseInt(req.queryParams("customer"));
            Customer customer = DBHelper.find(Customer.class, customerId);
            String strPurchaseDate = req.queryParams("purchaseDate");
            GregorianCalendar purchaseDate = DBHelper.formatStringToDate(strPurchaseDate);
            String strDeliveryDate = req.queryParams("deliveryDate");
            GregorianCalendar deliveryDate = DBHelper.formatStringToDate(strDeliveryDate);
            int shopId = Integer.parseInt(req.queryParams("shop"));
            Shop shop = DBHelper.find(Shop.class, shopId);

            PreviousPurchase previousPurchase = new PreviousPurchase(total, customer, purchaseDate, deliveryDate, shop);
            DBHelper.saveOrUpdate(previousPurchase);

            res.redirect("/previous-purchases");
            return null;
        }, new VelocityTemplateEngine());


        post ("/previous-purchases/:id/delete", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            PreviousPurchase previousPurchase = DBHelper.find(PreviousPurchase.class, id);
            DBHelper.delete(previousPurchase);
            res.redirect("/previous-purchases");
            return null;
        }, new VelocityTemplateEngine());


        post ("/previous-purchases/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            PreviousPurchase previousPurchase = DBHelper.find(PreviousPurchase.class, id);
            double total = Double.parseDouble(req.queryParams("total"));
            int  customerId = Integer.parseInt(req.queryParams("customer"));
            Customer customer = DBHelper.find(Customer.class, customerId);
            String strPurchaseDate = req.queryParams("purchaseDate");
            GregorianCalendar purchaseDate = DBHelper.formatStringToDate(strPurchaseDate);
            String strDeliveryDate = req.queryParams("deliveryDate");
            GregorianCalendar deliveryDate = DBHelper.formatStringToDate(strDeliveryDate);
            Shop shop = DBHelper.findShopByName("PPS Groceries");
            previousPurchase.setTotal(total);
            previousPurchase.setCustomer(customer);
            previousPurchase.setPurchaseDate(purchaseDate);
            previousPurchase.setDeliveryDate(deliveryDate);
            previousPurchase.setShop(shop);
            DBHelper.saveOrUpdate(previousPurchase);
            res.redirect("/previous-purchases");
            return null;
        }, new VelocityTemplateEngine());
    }

}
