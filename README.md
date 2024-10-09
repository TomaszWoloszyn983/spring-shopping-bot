# spring-shopping-bot
A project integrating a Java Spring web application with a Robotic Process Automation bot

The live version of the application is available at [this link](https://spring-shopping-bot-8a17cd3a24b1.herokuapp.com/home) (For more information about the deployment, see the relevant section in the documentation below).

# Flowchart

![Projects FLowchart](/docs/images/ssb_flowchart_high.png)

## **Contents:**

1. [Features](#features)
    * [Home Page](#home-page)
    * [Navigation Bar](#navigation-bar)
    <!-- * [Footer](#footer) -->
    <!-- * [Newsletter](#newsletter) -->
    * [Shoping List Page](#shopping-list-page)
2. [Color Scheme](#-colour-scheme)
<!-- 5. [Future Features](#) -->
3. [Technologies Used](#technologies-used)
<!-- 7. [Database Design](#7-database-design) -->
4. [Deployment](#deployment)
    * [Heroku Deployment](#heroku-deployment)
    <!-- * [Local Deployment](#local-deployment) -->
<!-- 10. [Agile Development Process](#10-agile-development-process) -->
<!-- 13. [Newsletter Marketing](#13-newsletter-marketing) -->
5. [References and Credits](#links-and-references)

# Features

The bot is going to have the following features. Displaying a web application when the user is going to create a shopping list. The application is going to send the shopping list to the UiPath Orchestrator to triggers a UiPath process. Then an UiPath bots is going to run. The bot is going to search the lowest prices of products from the users shopping list scanning the websites of the most popular supermarkets such as Tesco or Lidl.

## Java web application:

### Home Page

### Shopping List page. 

### Navigation Bar



### Results Page

## UiPath Robot

## **. Colour Scheme**

Colors used in this project:

- `#000000` used for primary text.
- `#2986ff` user for header text, buttons and navbar hover effect.
- `#80888a4d` used for navbar active buttons.
- `#DC4C64` danger elements such as removing or deleting items and error messages.
- `#14A44D` add item button, success messages.

# **. ShoppingList class **

This class stores Products to be shopped. It is of type List<Product>

# Product class

Contains the following fields:

 - Id (consider if it is needed for a objects such as products) -  a unique integer value
 - Name - the name of the product. Consider if more than one product by the same name can be added to the list.
 - Size - this value is probably going to be of String type as it can define the size in liters, grams or number of unts in the package.
 - Quantity - an integer value that describes the number of products we want to add the list.

```java
    public class Product {

        private int id;
        private String name;
        private String type;
        private String sizeOfUnit;
        private int numOfUnits;
```

# Database Structure

# Technologies Used
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

# Deployment

## Heroku Deployment

This project uses [Heroku](https://www.heroku.com), a platform as a service (PaaS) that enables developers to build, run, and operate applications entirely in the cloud.

Deployment steps are as follows, after account setup:

- Select *New* in the top-right corner of your Heroku Dashboard, and select *Create new app* from the dropdown menu.
- Your app name must be unique, and then choose a region closest to you (EU or USA), and finally, select *Create App*.
- From the new app *Settings*, click *Reveal Config Vars*, and set the following key/value pairs:

    | Key | Value |
    | --- | --- |
    <!-- | `AWS_ACCESS_KEY_ID` | insert your own AWS Access Key ID key here | -->
    <!-- | `AWS_SECRET_ACCESS_KEY` | insert your own AWS Secret Access key here | -->
    | `DATABASE_URL` | insert your own ElephantSQL database URL here |
    <!-- | `DISABLE_COLLECTSTATIC` | 1 (*this is temporary, and can be removed for the final deployment*) | -->
    <!-- | `EMAIL_HOST_PASS` | insert your own Gmail API key here | -->
    <!-- | `EMAIL_HOST_USER` | insert your own Gmail email address here | -->
    <!-- | `SECRET_KEY` | this can be any random secret key | -->
    <!-- | `STRIPE_PUBLIC_KEY` | insert your own Stripe Public API key here | -->
    <!-- | `STRIPE_SECRET_KEY` | insert your own Stripe Secret API key here | -->
    <!-- | `STRIPE_WH_SECRET` | insert your own Stripe Webhook API key here | -->
    <!-- | `USE_AWS` | True | -->

Heroku needs two additional files in order to deploy properly.
<!-- - requirements.txt -->
- Procfile

 # Links and references:

* Draw.io - 
* YouTube tutorial on how to deploy a Spring Boot application to Heroku - https://www.youtube.com/watch?v=lGtTOLKuvqs&ab_channel=DanVega
