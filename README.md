## Audio and Image Processing

![Alt text](/images/wav_visualization.png "Wav Visualization")
![Alt text](/images/processed_images/parrotsBlackAndWhite.png "Black And White Example")
![Alt text](/images/processed_images/parrotsDithered.png "Dithering Example")
![Alt text](/images/processed_images/parrots_auto_leveled.png "Auto Level Example")

### Description of the Project
* Audio Processing: Visualizes a single or dual channel wav file and gradually increases and decreases the levels of an audio signal (fade) and plots the waves.
* Image Processing: Takes a BMP file and performs grayscale, dithered, and auto-level transformations.

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
1. Open a terminal or command prompt and navigate to the project directory (`mediaProjectRemastered`).
2. Execute the following command to compile the project:

```bash
mvn clean install
```

#### To Run the Project:

** Run via Maven (JavaFX Run)**
- Execute the following command from the project root:

```bash
mvn javafx:run
```
---

