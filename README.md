# spring-shopping-bot
A project integrating a Java Spring web application with a Robotic Process Automation bot

# Flowchart

![Projects FLowchart](/docs/images/ssb_flowchart_high.png)


# Features

The bot is going to have the following features. Displaying a web application when the user is going to create a shopping list. The application is going to send the shopping list to the UiPath Orchestrator to triggers a UiPath process. Then an UiPath bots is going to run. The bot is going to search the lowest prices of products from the users shopping list scanning the websites of the most popular supermarkets such as Tesco or Lidl.

## Java web application:

### Home Page

### Shopping List page. 



### Results Page

## UiPath Robot

# ShoppingList class

This class stores Products to be shopped. It is of type List<Product>

# Product class

Contains the following fields:

 - Id (consider if it is needed for a objects such as products) -  a unique integer value
 - Name - the name of the product. Consider if more than one product by the same name can be added to the list.
 - Size - this value is probably going to be of String type as it can define the size in liters, grams or number of unts in the package.
 - Quantity - an integer value that describes the number of products we want to add the list.


# Used technologies
 * Java - 
 * Spring - 
 * H2 database - 
 * PostgreSQL database - 
 * Hibernate - 
 * HTML5 - 
 * CSS - 
 * JavaScript - 
 * Bootstrap - 
 * Thymeleaf - 
 * UiPath - 
 * GitHub - 
 * Heroku - 

 # Links and references:

* Draw.io - 
