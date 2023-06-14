## Audio and Image Processing 

### Description of the project 
* Audio Processing: visualizes a single or dual channel wav file and gradually increases and decreases the levels of an audio signal (fade) and plots the waves

* Image Processing: takes a BMP file and performs grayscale, dithered, and auto-level transformations

* To get a complete description of the used algorithms, please refer to Report.pdf 

### Technologies used in this project
* IntelliJ IDE
* Maven build automation
* Java programming language
* JavaFX GUI library
* Java Sound API

### ***Prerequisites:***

    Have Maven installed on your machine
    If Maven is not installed, please refer to https://maven.apache.org/install.html
 ***INSTRUCTIONS TO BUILD AND RUN THE PROJECT***

### To build and run the project 
    i) While in mediaProjectRemastered directory, using command prompt, execute the following command:
        
        mvn compile javafx:jlink
   
    ii) Target directory will be created. After target folder is build, go to the following directory:
        
        cd target/image/bin
    
    iii) In the target/image/bin directory execute the following command:
        
        java -m org.qulad/org.qulad.App

