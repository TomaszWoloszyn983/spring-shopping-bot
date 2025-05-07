# The Spring Shopping Bot

![Am_I_Responsive](/docs/images/am_i_responsive.png)

The **Spring Shopping Bot** is an automated web application built to simplify online grocery price 
comparisons across popular Irish supermarkets. Developed with Java and the Spring Framework, 
it integrates with UiPath's Robotic Process Automation (RPA) technology to deliver accurate and 
timely results. Users can create a personalized shopping list within the application, and upon submission, 
the system's RPA component initiates to check each item’s price on major supermarket websites such as 
Tesco and Dunnes. 

The system consists of two primary modules: a Java Spring web application for user interaction and an 
RPA layer powered by UiPath for the automation workflow. This automation workflow is divided into the 
**Dispatcher** and **Performer** processes. The Dispatcher collects shopping list data from the submitted 
form and populates the Orchestrator Queue. The Performer then retrieves each item from the queue, 
searches for prices online, compiles the information, and generates a comprehensive report. 

Upon completion, the report is sent via email to the user, detailing the best available offers. 
Future features include secure user accounts where users can log in to view past reports and access 
real-time savings insights. The Spring Shopping Bot showcases the potential of combining traditional 
web applications with RPA to streamline and automate complex, time-consuming tasks for users.

The live version of the application is available at 
[this link](https://spring-shopping-bot-8a17cd3a24b1.herokuapp.com/home) 
(For more information about the deployment, see the relevant section in the documentation below).

# Flowchart

![Projects FLowchart](/docs/images/ssb_flowchart_high.png)

## **Contents:**

1. [Features](#features)
    * [Home Page](#home-page)
    * [Navigation Bar](#navigation-bar)
    * [Shoping List Page](#shopping-list-page)
    * [Summary Page](#summary-page)
    * [Navigation Bar](#navigation-bar)
    * [Checking Login status](#checking-login-status)
2. [Future Features](#future-features)
3. [Color Scheme](#-colour-scheme)
4. [UiPath Automations](#uipath-automations)
    * [SSB Dispatcher](#spring-shopping-bot-dispatcher)
    * [SSB Dispatcher](#spring-shopping-bot-performer)
5. [Technologies Used](#technologies-used)
6. [Java Classes](#java-classes)
7. [Database Design](#database-design)
8. [Deployment](#deployment)
    * [Heroku Deployment](#heroku-deployment)
    <!-- * [Local Deployment](#local-deployment) -->
<!-- 10. [Agile Development Process](#10-agile-development-process) -->
<!-- 13. [Newsletter Marketing](#13-newsletter-marketing) -->
9. [Spring Security](#spring-security)
10. [References and Credits](#links-and-references)

# Features

The bot will include the following features: it will provide a web application that allows users 
to create their shopping lists. Once the shopping list is submitted, the application will send it 
to the UiPath Orchestrator, triggering a UiPath process. Subsequently, the UiPath bots will execute 
and search for the lowest prices of the products on the user's shopping list by scanning the websites 
of popular supermarkets, such as Tesco and Dunnes.

## Java web application:

### Home Page

The Home Page provides an overview of the Spring Shopping Bot’s functionality, guiding users through 
how the app automates grocery price comparisons across popular Irish supermarkets.

### Shopping List page. 

On the Shopping List Page, users can add items to their grocery list by entering product details such 
as name, type, size, and quantity. This list is then processed by the bot to find the best prices.

### Summary Page

The Summary Page displays a comprehensive report of product prices gathered from various supermarkets, 
helping users compare offers and make informed purchasing decisions. The report is also sent to the user’s email for convenience.

### Navigation Bar

The Navigation Bar enables easy access across key pages of the application, including the Home, 
Shopping List, and Summary pages, and offers a user-friendly, consistent interface.

### Checking Login status

At each start, the application checks whether a user is logged in or not. This allows the application function to be adjusted to the user's status.

Login status checking is included in the GlobalController class. The purpose of this functionality was to make the login status available globally in each class, which is why the isLoggedIn field is marked as static. Additionally, the name of the logged in user is also available globally, so that it can be easily passed to html templates, for example.

```java
    public class GlobalController {

        private static Boolean isLoggedIn = false;
        private static String username = "Guest";

        public static Boolean getIsLoggedIn(){
            return isLoggedIn;
        }

        public static void setIsLoggedIn(boolean value){
            isLoggedIn = value;
        }

        public static void updateIsLoggedIn(){
            Authentication authentication = SecurityContextHolder
                    .getContext()
                    .getAuthentication();

            isLoggedIn = authentication != null
                    && authentication.isAuthenticated()
                    && !(authentication instanceof AnonymousAuthenticationToken);
            username = authentication.getName();
            var role = authentication.getDetails();
        }

        public static String getUsername() {
            return username;
        }

        public static void setUsername(String username) {
            GlobalController.username = username;
        }
    }
```

Below is an example of implementing the GlobalController class in the HomeController class. This implementation allows you to pass information about the login status to the Home Page of the client.

```java
    public class HomeController {

        Boolean isLoggedIn = GlobalController.getIsLoggedIn();

        @GetMapping({"/","/home"})
        public String displayHomePage(Model model){
            GlobalController.updateIsLoggedIn();

            model.addAttribute("isLoggedIn", GlobalController.getIsLoggedIn());
            model.addAttribute("username", GlobalController.getUsername());

            return "index.html";
        }
    }
```

## UiPath Robot

### Dispatcher Bot

* Email Retrieval: Scans incoming emails for new shopping lists submitted by users.

* Data Extraction: Extracts product information from the submitted shopping list.

* Queue Management: Transforms the extracted data into QueueItems and adds them to the Orchestrator 
queue, ready for price-checking automation.

* User Identification: Associates each product list with a specific user email, supporting accurate 
processing and reporting.

### Performer Bot

* Queue Item Processing: Retrieves individual products from the Orchestrator queue, handling them 
sequentially.

* Price Comparison: Searches for each product on popular supermarket websites (e.g., Tesco, Dunnes) and 
retrieves the current pricing information.

* Data Aggregation: Adds each product’s pricing details to a DataTable, generating an organized report.

* User-Specific Order Completion: Monitors for user email changes in the queue to segment product orders, 
completing one user's order before moving to the next.

* Automated Email Reporting: Compiles a pricing report for each user’s order and sends it via email, 
ensuring that users receive timely and accurate information on the best product deals.



# **Future Features**

## **Shopper class**

This class will store data of the user who created an account in the application, 
such as name, email, and order history.

## **More Sellers**

There are plans to add more sellers to get more offers to compare. 
The application may be expanded in the future to include retailers such as SuperValu and 
Hollands & Barrets.

## **Creating users account**

Added the ability to register a user account, where after logging in the user will be 
able to see the results of their order and the history of their previous orders.


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


# Java Classes

<!-- ## **ShoppingList class**

This class stores Products to be shopped. It is of type List<Product> -->

## **Product class**

Contains the following fields:

 - Id (consider if it is needed for a objects such as products) -  a unique integer value
 - Name - the name of the product. Consider if more than one product by the same name can be 
 added to the list.
 - Size - this value is probably going to be of String type as it can define the size in liters, 
 grams or number of unts in the package.
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


# Database Design

![Database Diagram](/diagrams_and_flowcharts/Database/database_schema.png)

## Product Table
Stores details of available products, including Id, Name, Type, SizeOfUnit, and NumOfUnits, providing essential attributes for product identification and classification within the application.

## Order Table
Tracks customer orders with fields Id, CreatedOn, UserEmail, and ProductList, maintaining links to ordered products and users while supporting historical record-keeping.

## User Table
Holds user information, including Id, Name, Email, Username, Password, and OrderList, managing essential user credentials and associating each user with their orders.

## Role Table
Defines user roles such as ADMIN and USER with fields Id and Name, supporting role-based access control to enforce permissions across different parts of the application.

## OrderProduct Table: 
The OrderProduct table holds the OrderId and ProductId to establish the many-to-many relationship between orders and products. Each entry represents a product associated with an order.


### Retrieving Products for a Specific Order

The association between Order and Product entities is established through a Many-to-Many relationship, implemented via a junction table named ProductOrder. To efficiently retrieve the products assigned to a specific order, a dedicated data access and service layer is introduced.

####	1. Relationship Overview

Entities Involved: Order, Product

Join Table: ProductOrder (with fields like order_id, product_id)

This setup allows each order to contain multiple products, and each product to belong to multiple orders.


#### 2. Repository Layer

A ProductOrderRepository interface is created, extending JpaRepository.

It provides custom query methods (e.g., findByOrderId(int orderId)) to retrieve all ProductOrder records related to a given order.


#### 3. Service Layer

The ProductOrderService contains business logic for interacting with the repository.

It provides a method like getProductsByOrderId(int orderId) which:

Calls the repository to fetch all ProductOrder entries linked to the given order.

Extracts and returns the list of associated Product entities.



#### 4. Controller Integration

The service method is used within the controller (e.g., UserController) to populate the Order objects with their corresponding list of products.

This enriched order data is passed to the view layer for rendering in the UI.


#### 5. UI Display

The Thymeleaf template iterates over each order and its associated products, displaying order details and product names/types dynamically.


This modular approach ensures clear separation of concerns, improves maintainability, and allows for efficient retrieval and display of product data associated with orders.



## UserOrder Table: 
The UserOrder table holds the UserId and OrderId to link users with their orders. Each entry represents an order placed by a user.

### Associating Users with Orders

The association between User and Order is managed through a Many-to-Many relationship, implemented using an intermediate table called UserOrder.

1. Relationship Overview

Entities Involved: User, Order

Join Table: UserOrder (containing user_id, order_id)

This structure allows a user to place multiple orders, and—if ever required—an order to potentially be associated with multiple users.


2. No Direct Repository

Unlike the ProductOrder relationship, a dedicated repository for UserOrder is not created.

Instead, data retrieval is handled using existing repositories (GuestUserRepository, OrderRepository) and service methods that utilize custom queries or mapped relationships.


3. Service Layer Logic

The GuestUserService includes methods such as findUsersOrders(String userEmail).

These methods retrieve orders associated with a user by querying the OrderRepository using the user’s email or other identifying fields, assuming the Order entity holds a reference to the user (e.g., through an email field or mapped @ManyToOne relationship).


4. Efficiency and Simplicity

By leveraging entity relationships and custom queries, the UserOrder join is abstracted away from the developer.

This reduces boilerplate code and simplifies data access, especially in use cases where the join table doesn’t require direct interaction (e.g., storing extra fields).


5. Front-End Integration

The list of a user’s orders is retrieved in the controller and passed to the view.

The UI renders these orders, optionally enriched with product data, on pages like the user account or order history view.


This approach keeps the design simple and effective for typical use cases where the intermediate join does not require additional metadata or logic.


---

Would you like to include any code snippets (like the findUsersOrders() method) in the documentation for clarity?





# Technologies Used


* **Java** - A powerful, versatile programming language widely used for building enterprise-grade applications, known for its reliability, portability, and strong community support.

* **Spring Framework** - A comprehensive Java framework used for building robust, high-performance applications, offering features like dependency injection, security, and support for web applications.

* **Spring Security** - Spring Security is a framework which can be used to secure Spring applications. It focuses on both authentication and authorization. It can be configured and customized to meet own demands. 

* **H2 Database** - A lightweight, in-memory relational database often used in Java applications for development, testing, and rapid prototyping. Used locally for developement.

* **PostgreSQL Database** - A powerful, open-source relational database management system known for its advanced features, data integrity, and extensibility. Used at the Deployment stage, in Heroku.

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
    | `JWT_SECRET_KEY` | this key must meet SignatureAlgorithm.HS512 requirements, which is 512 random bits/characters (64 random bytes) |
    <!-- | `STRIPE_PUBLIC_KEY` | insert your own Stripe Public API key here | -->
    <!-- | `STRIPE_SECRET_KEY` | insert your own Stripe Secret API key here | -->
    <!-- | `STRIPE_WH_SECRET` | insert your own Stripe Webhook API key here | -->
    <!-- | `USE_AWS` | True | -->

Heroku needs two additional files in order to deploy properly.
<!-- - requirements.txt -->
- Procfile

 # Spring Security

 Spring Shopping Bot uses Spring Security for user authentication and access control. This section summarizes the security configurations and implementations used in the project.

 ## 1. Spring Security Configuration
We configured Spring Security to handle authentication and authorization using JWT (JSON Web Token).

### Security Configuration Class
- Disabled CSRF protection since JWT is used for authentication.
- Configured CORS settings to allow frontend requests.
- Defined authentication and authorization rules using HttpSecurity.
- Enabled authentication for protected routes while allowing public access to login and registration endpoints.

### SecurityConfig.java
```java
    @Configuration
    @EnableWebSecurity
    @RequiredArgsConstructor
    public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthFilter;
        private final AuthenticationProvider authenticationProvider;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            return http
                    .csrf(AbstractHttpConfigurer::disable)
                    .cors(Customizer.withDefaults())
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/", "/home", "/register", "/login").permitAll()
                            .anyRequest().authenticated()
                    )
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authenticationProvider(authenticationProvider)
                    .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                    .build();
        }
    }
```

## 2. User Authentication

### Login & Registration
- Users authenticate using JWT tokens.
- Credentials are validated against the database.
- A JWT token is generated and returned upon successful login.

### AuthController.java
```java
    @RestController
    @RequestMapping("/auth")
    @RequiredArgsConstructor
    public class AuthController {

        private final AuthenticationService authService;

        @PostMapping("/register")
        public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest request) {
            return ResponseEntity.ok(authService.register(request));
        }

        @PostMapping("/login")
        public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
            return ResponseEntity.ok(authService.authenticate(request));
        }
    }
```

## 3. User Session Management

We created a GlobalController to manage user session status globally. This helps track whether a user is logged in and retrieve the current username.

### GlobalController.java

```java
    @Controller
    public class GlobalController {

        private static Boolean isLoggedIn = false;
        private static String username = null;

        public static Boolean getIsLoggedIn() {
            return isLoggedIn;
        }

        public static String getUsername() {
            return username;
        }

        public static void updateAuthStatus() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            isLoggedIn = authentication != null
                    && authentication.isAuthenticated()
                    && !(authentication instanceof AnonymousAuthenticationToken);

            username = (isLoggedIn) ? authentication.getName() : null;
            
            System.out.println("User: " + username + " is logged in: " + isLoggedIn);
        }
    }
```

- updateAuthStatus() retrieves the authentication status whenever needed.
- This allows controllers to check if a user is logged in and update the UI accordingly.

## 4. Logout Implementation

We implemented a logout function that:

- Removes the JWT token from cookies.
- Clears the security context.
- Updates GlobalController to reflect the logged-out state.

### Updated Logout Function
```java
    @RestController
    public class AuthController {

        @GetMapping("/logout")
        public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
            // Clear Security Context
            SecurityContextHolder.clearContext();

            // Remove JWT token from cookies
            Cookie jwtCookie = new Cookie("jwt", "");
            jwtCookie.setHttpOnly(true);
            jwtCookie.setSecure(true); // Set to false if not using HTTPS in dev
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(0); // Expire immediately
            response.addCookie(jwtCookie);

            // Update authentication status in GlobalController
            GlobalController.updateAuthStatus();

            System.out.println("Logout success. User logged out.");

            return ResponseEntity.ok("You have been logged out successfully.");
        }
    }
```

This ensures that the user is completely logged out and cannot access protected routes without logging in again.

## 5. Using Authentication in Controllers

Controllers use GlobalController to check login status and pass authentication data to the views.

### Example in HomeController
```java
    @Controller
    public class HomeController {

        @GetMapping({"/", "/home"})
        public String displayHomePage(Model model) {
            System.out.println("Displaying Home Page");

            GlobalController.updateAuthStatus();
            Boolean isLoggedIn = GlobalController.getIsLoggedIn();

            model.addAttribute("isLoggedIn", isLoggedIn);
            model.addAttribute("username", isLoggedIn ? GlobalController.getUsername() : null);

            return "home";
        }
    }
```
This dynamically updates the navigation bar and UI based on login status.

## 6. Frontend Integration

The Navbar in index.html dynamically updates based on the user's login status.

```html
    <nav>
        <ul>
            <li><a href="/">Home</a></li>
            <li th:if="${isLoggedIn}"><a href="/dashboard">Dashboard</a></li>
            <li th:if="${isLoggedIn}"><a href="/logout">Logout</a></li>
            <li th:unless="${isLoggedIn}"><a href="/login">Login</a></li>
        </ul>
    </nav>
```
- If isLoggedIn == true, it shows Dashboard & Logout.
- If isLoggedIn == false, it shows Login.

 # Links and references:

* Draw.io - 
* YouTube tutorial on how to deploy a Spring Boot application to Heroku - https://www.youtube.com/watch?v=lGtTOLKuvqs&ab_channel=DanVega
* Youtube tutorial in Spring Authentication and Security - https://www.youtube.com/watch?v=GjN5IauaflY&list=PL82C6-O4XrHe3sDCodw31GjXbwRdCyyuY&index=1&ab_channel=TeddySmith
* YouTube tutorial by Rakesh on GMail automation with UiPath - https://www.youtube.com/watch?v=O9dVTI1n3qM&ab_channel=AutomatewithRakesh
YouTube tutorial on how to automate GMail: https://www.youtube.com/watch?v=Y5gNBIZWEDs&list=TLPQMTgxMDIwMjSOM2_2JBnSRQ&index=3&ab_channel=UiPathVideoTutorialsmadebyCristianNegulescu
* Robot Icon taken from the the following website: https://iconscout.com/free-icon/robot-130
