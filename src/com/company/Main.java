package com.company;

import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    static User user;
    static ArrayList<User> pastUsers = new ArrayList<>();

    public static void main(String[] args) {
        Spark.get(
                "/",
                (request, response) -> {
                    HashMap m = new HashMap<>();
                    if (user != null) {
                        m.put("name",user.name);
                    }
                    m.put("pastUsers", pastUsers);
                    return new ModelAndView(m,"home.html");
                },
                new MustacheTemplateEngine()
        );

        Spark.get(
                "/login",
                (request, response) -> {
                    return new ModelAndView(null,"login.html");
                },
                new MustacheTemplateEngine()
        );

        Spark.post(
                "/login",
                (request,respone) -> {
                    String name = request.queryParams("loginName");
                    user = new User(name);
                    pastUsers.add(user);
                    respone.redirect("/");
                    return null;
                }
        );

        Spark.post(
                "/logout",
                (request,response) -> {
                    user = null;
                    response.redirect("/");
                    return null;
                }
        );
    }
}
