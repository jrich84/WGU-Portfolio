### Mobile Application Development

[Degree Outline](https://www.wgu.edu/online-it-degrees/software-development-bachelors-program.html) "Java Path"

Objective:  
This course introduces students to programming for mobile devices using a software development kit (SDK). Students with previous knowledge of programming will learn how to install and utilize a SDK, build a basic mobile application, build a mobile application using a graphical user interface (GUI), adapt applications to different mobile devices, save data, execute and debug mobile applications using emulators, and deploy a mobile application.  

Skills:  
* Scripting and Programming - Applications
* Software Engineering
* Software I
* User Experience Design
* Software II - Advanced Java Concepts
* Software Quality Assurance


**Requirements**  
* Android Studio
* Android SDK 7.1.1(Nougat) API LVL 27
* AVD(Android Virtual Device) Pixel two
* Java v8

**Dependencies**  
def room_version = "2.2.5"  
implementation fileTree(dir: 'libs', include: ['*.jar'])  

implementation 'androidx.appcompat:appcompat:1.1.0'  
implementation 'androidx.legacy:legacy-support-v4:1.0.0'  
implementation 'com.google.android.material:material:1.1.0'  
implementation 'androidx.constraintlayout:constraintlayout:1.1.3'  
implementation 'androidx.navigation:navigation-fragment:2.3.0'  
implementation 'androidx.navigation:navigation-ui:2.3.0'  
implementation 'androidx.lifecycle:lifecycle-extensions:2.2.0'  
testImplementation 'junit:junit:4.12'  
androidTestImplementation 'androidx.test.ext:junit:1.1.1'  
androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'  

// Room  
implementation "android.arch.persistence.room:runtime:$room_version"  
annotationProcessor "android.arch.persistence.room:compiler:$room_version"  
implementation "androidx.room:room-guava:$room_version"  
