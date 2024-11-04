# The Spring Shopping Bot

![Am_I_Responsive](/docs/images/am_i_responsive.png)

The **Spring Shopping Bot** is an automated web application built to simplify online grocery price comparisons across popular Irish supermarkets. Developed with Java and the Spring Framework, it integrates with UiPath's Robotic Process Automation (RPA) technology to deliver accurate and timely results. Users can create a personalized shopping list within the application, and upon submission, the system's RPA component initiates to check each item’s price on major supermarket websites such as Tesco and Dunnes. 

The system consists of two primary modules: a Java Spring web application for user interaction and an RPA layer powered by UiPath for the automation workflow. This automation workflow is divided into the **Dispatcher** and **Performer** processes. The Dispatcher collects shopping list data from the submitted form and populates the Orchestrator Queue. The Performer then retrieves each item from the queue, searches for prices online, compiles the information, and generates a comprehensive report. 

Upon completion, the report is sent via email to the user, detailing the best available offers. Future features include secure user accounts where users can log in to view past reports and access real-time savings insights. The Spring Shopping Bot showcases the potential of combining traditional web applications with RPA to streamline and automate complex, time-consuming tasks for users.

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
2. [Future Features](#future-features)
3. [Color Scheme](#-colour-scheme)
<!-- 5. [Future Features](#) -->
4. [UiPath Automations](#uipath-automations)
    * [SSB Dispatcher](#spring-shopping-bot-dispatcher)
    * [SSB Dispatcher](#spring-shopping-bot-performer)
5. [Technologies Used](#technologies-used)
<!-- 7. [Database Design](#7-database-design) -->
6. [Deployment](#deployment)
    * [Heroku Deployment](#heroku-deployment)
    <!-- * [Local Deployment](#local-deployment) -->
<!-- 10. [Agile Development Process](#10-agile-development-process) -->
<!-- 13. [Newsletter Marketing](#13-newsletter-marketing) -->
7. [References and Credits](#links-and-references)

# Features

The bot will include the following features: it will provide a web application that allows users to create their shopping lists. Once the shopping list is submitted, the application will send it to the UiPath Orchestrator, triggering a UiPath process. Subsequently, the UiPath bots will execute and search for the lowest prices of the products on the user's shopping list by scanning the websites of popular supermarkets, such as Tesco and Dunnes.

## Java web application:

### Home Page

The Home Page provides an overview of the Spring Shopping Bot’s functionality, guiding users through how the app automates grocery price comparisons across popular Irish supermarkets.

### Shopping List page. 

On the Shopping List Page, users can add items to their grocery list by entering product details such as name, type, size, and quantity. This list is then processed by the bot to find the best prices.

### Summary Page

The Summary Page displays a comprehensive report of product prices gathered from various supermarkets, helping users compare offers and make informed purchasing decisions. The report is also sent to the user’s email for convenience.

### Navigation Bar

The Navigation Bar enables easy access across key pages of the application, including the Home, Shopping List, and Summary pages, and offers a user-friendly, consistent interface.

## UiPath Robot

### Dispatcher Bot

* Email Retrieval: Scans incoming emails for new shopping lists submitted by users.

* Data Extraction: Extracts product information from the submitted shopping list.

* Queue Management: Transforms the extracted data into QueueItems and adds them to the Orchestrator queue, ready for price-checking automation.

* User Identification: Associates each product list with a specific user email, supporting accurate processing and reporting.

### Performer Bot

* Queue Item Processing: Retrieves individual products from the Orchestrator queue, handling them sequentially.

* Price Comparison: Searches for each product on popular supermarket websites (e.g., Tesco, Dunnes) and retrieves the current pricing information.

* Data Aggregation: Adds each product’s pricing details to a DataTable, generating an organized report.

* User-Specific Order Completion: Monitors for user email changes in the queue to segment product orders, completing one user's order before moving to the next.

* Automated Email Reporting: Compiles a pricing report for each user’s order and sends it via email, ensuring that users receive timely and accurate information on the best product deals.



## **ShoppingList class**

This class stores Products to be shopped. It is of type List<Product>

## **Product class**

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


## **Order class**

This class defines the order placed by the user. It contains values ​​such as: 
 - Id.
 - Date of placing the order.
 - List of products added to the order.
 - User's email address to which order confirmation and results will be sent.

```java
    public class Order {

    //    private int id;
        @NotBlank(message = "Email is required")
        @Email(message = "Please provide a valid email address")
        private String userEmail;
        private List<Product> listOfProducts = new ArrayList<Product>();
        private LocalDateTime orderDate;
    }
```

# **Future Features**

## **Shopper class**

This class will store data of the user who created an account in the application, such as name, email, and order history.

## **More Sellers**

There are plans to add more sellers to get more offers to compare. 
The application may be expanded in the future to include retailers such as SuperValu and Hollands & Barrets.


# **Colour Scheme**

Colors used in this project:

- `#000000` used for primary text.
- `#2986ff` user for header text, buttons and navbar hover effect.
- `#80888a4d` used for navbar active buttons.
- `#DC4C64` danger elements such as removing or deleting items and error messages.
- `#14A44D` add item button, success messages.

# UiPath Automations

## Spring Shopping Bot Dispatcher


The dispatcher takes the product list emailed by the user via the web application 
and extracts the details required for processing.

Once the product list is obtained, the Dispatcher converts each product entry 
into the `QueueItem` format. It then sends each item to the designated Orchestrator 
Queue, where it will be picked up by the Performer process for further processing.


![Dispatcher FLowchart](/diagrams_and_flowcharts/Dispatcher/SSB_dispatcher.png)

## Spring Shopping Bot Performer


The Performer retrieves product data stored in the `QueueItem` format in the Orchestrator Queue.

It checks the price of each product, first on the Tesco website and then on the Dunnes website. After retrieving the information, it adds the data to a `DataRow` and then adds the `DataRow` to the `Report` `DataTable`.

If there are more products stored in the Orchestrator, it retrieves the next product from the Orchestrator Queue and repeats this sequence until all products stored in the Queue are processed.


To distinguish products belonging to different users, the process checks each item’s associated user email. If the user email in the currently processed product is different from the email in the previous product, it indicates the start of a new user’s order. In this case, the previous user’s order is completed and sent to them via email.

To make the distinction process easier, an auxiliary variable (`tempEmail`) was created. For more details, see the diagram below.


![Performer FLowchart](/diagrams_and_flowcharts/Performer/performer_flowchart_diagram.png)


<!-- # Database Structure -->

# Technologies Used


* **Java** - A powerful, versatile programming language widely used for building enterprise-grade applications, known for its reliability, portability, and strong community support.

* **Spring** - A comprehensive Java framework used for building robust, high-performance applications, offering features like dependency injection, security, and support for web applications.

* **H2 Database** - A lightweight, in-memory relational database often used in Java applications for development, testing, and rapid prototyping.

* **PostgreSQL Database** - A powerful, open-source relational database management system known for its advanced features, data integrity, and extensibility.

* **Hibernate** - An object-relational mapping (ORM) library for Java that simplifies database interactions by mapping Java classes to database tables.

* **Flyway** - A database migration tool that manages and version-controls database changes, supporting continuous integration and delivery workflows.

* **HTML5** - The latest version of the HTML standard, providing improved support for multimedia, graphics, and web APIs, and is a foundation for building modern web applications.

* **CSS** - A style sheet language used for describing the look and formatting of a website, allowing separation of content and design.

* **JavaScript** - A versatile scripting language for web development, used to create dynamic, interactive elements on websites.

* **Bootstrap** - A popular CSS framework that provides pre-designed components and a responsive grid system for fast, mobile-first web development.

* **Thymeleaf** - A Java-based template engine for server-side rendering in web applications, integrating seamlessly with Spring for dynamic HTML generation.

* **UiPath** - A leading Robotic Process Automation (RPA) platform that automates repetitive tasks, enhancing efficiency and reducing errors.

* **ReFramework** - A robust, modular RPA framework within UiPath designed to handle complex processes, including error handling and transaction management.

* **GitHub** - A platform for version control and collaboration, allowing developers to store, manage, and track changes in their code.

* **Heroku** - A cloud platform that supports deploying, managing, and scaling applications, making it easy to host applications without managing server infrastructure.
Certainly!

* **ChatGPT** - An advanced AI language model developed by OpenAI, designed to understand and generate human-like text based on context. It assists users with information retrieval, task automation, and provides support across various fields, enhancing productivity and engagement through natural language conversations.


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
    | `DATABASE_URL` | insert your own database URL here |
    | `DATABASE_PASSWORD` | insert your own database URL here |
    <!-- | `DISABLE_COLLECTSTATIC` | 1 (*this is temporary, and can be removed for the final deployment*) | -->
    | `ADMIN_MAIL_PASSWORD` | insert your own Gmail API key here |
    | `ADMIN_EMAIL` | insert your own Gmail email address here |
    | `profile` | insert your own Gmail email address here |
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
* YouTube tutorial by Rakesh on GMail automation with UiPath - https://www.youtube.com/watch?v=O9dVTI1n3qM&ab_channel=AutomatewithRakesh
YouTube tutorial on how to automate GMail: https://www.youtube.com/watch?v=Y5gNBIZWEDs&list=TLPQMTgxMDIwMjSOM2_2JBnSRQ&index=3&ab_channel=UiPathVideoTutorialsmadebyCristianNegulescu
* Robot Icon taken from the the following website: https://iconscout.com/free-icon/robot-130
