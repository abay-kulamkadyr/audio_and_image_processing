<h1 align="center">
  <br>
  <img src="/images/wav_visualization.png" alt="Audio and Image Processing" width="100" ">
  <img src="/images/processed_images/parrotsBlackAndWhite.png" alt="Audio and Image Processing" width="100" >
  <img src="/images/processed_images/parrotsDithered.png" alt="Audio and Image Processing" width="100" >
  <img src="/images/processed_images/parrots_auto_leveled.png" alt="Audio and Image Processing" width="100" >
  <br>
  Audio and Image Processing
  <br>
</h1>
Audio and Image Compression<h4 align="center">A project demonstrating lossless audio and lossy image compression techniques.</h4>

<p align="center">
  <a href="#description">Description</a> •
  <a href="#key-features">Key features</a> •
  <a href="#techologies">Technologies</a> •
  <a href="#prerequisites">Prerequisites</a> •
  <a href="#building-and-running">Build and Run</a> •
</p>

### Description
* Audio Processing: Visualizes a single or dual channel wav file and gradually increases and decreases the levels of an audio signal (fade) while plotting the waveforms.
* Image Processing: Takes a BMP file and performs grayscale conversion, dithering, and auto-level transformations.

### Key Features
* Audio Processing:
  - Load and visualize a single or dual channel wav file.
  - Perform fade in and fade out effects on the audio signal.
  - Save the modified audio signal as a new wav file.

* Image Processing:
  - Load and display a BMP image.
  - Convert the image to grayscale.
  - Apply dithering to the grayscale image.
  - Perform auto-level transformations on the image.
  - Save the modified image as a new BMP file.

## Technologies
* Java
* Maven
* JavaFX GUI library
* Java Sound API

## Prerequisites
- Have Maven installed on your machine.
- If Maven is not installed, please refer to [Maven Installation](https://maven.apache.org/install.html).

## Building and Running

### To Build the Project:
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

### To Run the Project:

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
