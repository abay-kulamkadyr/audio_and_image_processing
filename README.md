# Audio and Image Processing

![Alt text](/images/wav_visualization.png "Wav Visualization")
![Alt text](/images/processed_images/parrotsBlackAndWhite.png "Black And White Example")
![Alt text](/images/processed_images/parrotsDithered.png "Dithering Example")
![Alt text](/images/processed_images/parrots_auto_leveled.png "Auto Level Example")

### Description of the Project
* Audio Processing: Visualizes a single or dual channel wav file and gradually increases and decreases the levels of an audio signal (fade) while plotting the waveforms.
* Image Processing: Takes a BMP file and performs grayscale conversion, dithering, and auto-level transformations.

### Technologies Used in This Project
* Maven build automation
* Java
* JavaFX GUI library
* Java Sound API

### Prerequisites
- Have Maven installed on your machine.
- If Maven is not installed, please refer to [Maven Installation](https://maven.apache.org/install.html).

### Instructions to Build and Run the Project

#### To Build the Project:
1. Open a terminal or command prompt and navigate to the project directory (the directory containing the `src` folder).
2. Execute one of the following build commands:
   - To build and package using the assembly plugin (fat JAR):
```bash
mvn clean compile assembly:single
```
   - Alternatively, to compile and install the project, run:
```bash
mvn clean install
```

#### To Run the Project:

**Option 1: Run via Maven (JavaFX Run)**
- From the project root, execute:
```bash
mvn javafx:run
```

**Option 2: Run the Executable JAR**
- An executable JAR file named `program.jar` is generated in the `target` directory and is also included in the project root.
- To run the JAR, execute:
```bash
java -jar program.jar
```

### Packaging a Self-Contained JAR
The final JAR is created using the Maven Assembly Plugin (or alternatively the Maven Shade Plugin), which merges your project classes with all dependencies into a single executable JAR.
- To create the fat JAR, run:
```bash
mvn clean compile assembly:single
```
- The packaged JAR (named `program.jar`) will be located in the `target` directory and also copied to the project root.
- To run the packaged JAR, use:
```bash
java -jar program.jar
```
