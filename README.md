
# Custom Image Loading Library

This project is a custom Android image loading library that demonstrates lazy loading, disk and memory caching, and efficient image pre-fetching using Retrofit for network requests. It includes a 3-column grid layout with smooth scrolling and optimized performance for handling large numbers of images. The images used in this project are fetched using Pexels API.

## Features
- **Lazy Loading:** Images are loaded lazily, ensuring that only the visible images are fetched and displayed. As you scroll, images that are out of view are unloaded, and new images are fetched as needed.
- **Disk Caching:** Images are cached on disk to reduce network requests and improve loading times. This ensures that previously loaded images are quickly available when revisited.
- **Memory Caching:** Recently loaded images are stored in memory for immediate access, further improving performance.
- **Paging:** Efficient paging mechanism to load a large set of images in chunks, with pre-fetching of the next set of images to avoid jitter during scrolling.
- **3-Column Grid Layout:** The RecyclerView is set up with a 3-column grid layout for displaying images in an organized manner.
## Architecture
- **Retrofit:** Used for making network requests to fetch image data from the Pexels API.
- **OkHttp:** Handles disk caching for network responses.
- **LruCache:** Manages memory caching of images.
- **Paging:** Uses Android’s Paging library for efficient loading of large data sets.

## Installation
You can install the application by downloading the apk file uploaded in the repository **OR** by downloading it through google drive (https://drive.google.com/file/d/1MeLtLJzn91zasIddqoAB1BwOVoXBe9-a/view?usp=drive_link)
## How to Use

1) Clone the repository to your local machine using git:
```bash
  git clone(https://github.com/yashsharma0558/Wareline-PhotoLibrary)
```
2) Open Android Studio and select "Open an existing Android Studio project". Navigate to the cloned repository directory and select it to open the project.

3) Wait for the project to build and sync all dependencies.

4) Connect your Android device to your computer and enable USB debugging on the device.

5) In Android Studio, click on the "Run" button to run the app on your device.
## Demo
https://github.com/user-attachments/assets/6a363357-b7ac-4eb1-9883-b564329c9842
## Screenshots
![wareline_1](https://github.com/user-attachments/assets/9046af5c-480a-4902-ad7c-04e5ac128c04)![wareline_2](https://github.com/user-attachments/assets/0744c469-f222-43d9-807f-88eed67ea200)

