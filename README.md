# 📱 Mobile Image Classifier App (EISapp)

This is a lightweight Android app that uses the **MobileNetV2 deep learning model** to classify images. The app allows users to either load an image from assets or pick an image from the gallery, then runs inference to predict the image's class using a TFLite model.

---

## 🧠 Features

- ✅ Load image from **app assets**
- ✅ Pick image from **phone gallery**
- ✅ Run image through **MobileNetV2** TFLite model
- ✅ View classification result instantly
- ✅ Clean and modern UI

---

## 🤖 Model Info

The app uses **MobileNetV2**, a lightweight CNN architecture developed by Google for mobile and embedded vision applications.

- **Framework**: TensorFlow Lite
- **Input Shape**: 224x224 RGB images
- **Output**: Class probabilities for 1000 ImageNet classes (or custom classes if trained differently)
- **Advantages**:
  - Low latency
  - Low memory footprint
  - Great for on-device inference

---

## 📂 Project Structure

```
EISapp/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/yourpackage/MainActivity.java (or .kt)
│   │   │   ├── res/
│   │   │   │   ├── layout/activity_main.xml
│   │   │   │   ├── drawable/
│   │   │   │   └── values/
│   │   │   ├── assets/
│   │   │   │   ├── mobilenet_v2.tflite
│   │   │   │   └── labelmap.txt
│   ├── build.gradle
├── build.gradle (project level)
├── README.md
```

---

## 🛠️ How to Build & Run

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/your-repo-name.git
   cd your-repo-name
   ```

2. **Open in Android Studio**:
   - `File` → `Open` → Select the project folder

3. **Build the project**:
   - Wait for Gradle sync to complete  
   - `Build` → `Rebuild Project`

4. **Run on a device**:
   - Connect your Android device via USB or use an emulator  
   - Click the green **Run ▶️** button

---

## 🧪 Sample Assets

The `assets/` folder includes:

- Sample `.jpg` images  
- Pretrained `mobilenet_v2.tflite` model  
- `labelmap.txt` file with class labels  

---

## 👨‍💻 Developers

- **Aditya Koli** [@adityaskoli06](mailto:adityaskoli06@gmail.com)

---

## 📃 License

This project is licensed under the **MIT License**.  
Feel free to use it for educational or research purposes.

---

## 🚀 Future Improvements

- [ ] Add camera-based live classification  
- [ ] Support for custom-trained TFLite models  
- [ ] Display confidence scores for predictions
📃 License
This project is licensed under the MIT License. Feel free to use it for educational or research purposes.

🚀 Future Improvements
- [ ] Add camera-based live classification  
- [ ] Support for custom-trained TFLite models  
- [ ] Display confidence scores for predictionss
