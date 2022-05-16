# MichellCorreia_Projeto_IDP
Technical Evaluation 1

### 1.Installation:  
Before we put all the applications into containers, we just need to create the image from the project application (quotation-management) with the Dockerfile present on the project root file, to do that, we need point our bash to the application main file (where the Dockerfile is located).
After that, type the following command:
 

The project contained in this GitHub has a “docker-compose.yml” file, it can be used to install all the necessary packages for the project to work. That .yml file will create 3 containers on docker: “mysql8”, “stock-manager” and “quotation-management-app”, they will all be inside a network called “mynetwork” and will be initialized to run properly.
Command to run the .yml file: (your bash must be pointing to the project root file)
  

To access the project endpoints, you must access the localhost through the port 8081.
  
  
  
  
### 2.StartUp:
The “quotation-management-app” application will register itself on the “stock-manager” application on its startup, to be able to receive notifications from it.

Important!
During the first time loading the containers using the .yml file, the database (mysql8) container will take about 10 seconds to load, and the main application will crash because there is no database running. The main application is setted to restart every time it stops, so it will run smoothly on the second or third time, so do not worry. 
Again, only happens on the first time the DB loads in.
