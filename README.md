# Chimney Provides Vector Compose

[![](https://jitpack.io/v/januprasad/Chimney.svg)](https://jitpack.io/#januprasad/Chimney)

# How can we convert SVG to shape in compose

![screen](https://user-images.githubusercontent.com/1284454/233897178-3365773f-3d62-4656-953f-76f1654e26b3.png)

Here I'm taking a custom shape in SVG, which is flat (single-level path)
```svg
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:aapt="http://schemas.android.com/aapt"
    android:width="500dp"
    android:height="356dp"
    android:viewportWidth="500"
    android:viewportHeight="356">
  <path
      android:pathData="m250.2,9c0,0 22.5,33.5 67.6,40.2 25.8,3.9 73.4,5.2 109.3,5.6 25.9,0.3 46.8,21.4 46.8,47.3 0,17.8 0,35.2 0,41 0,11 8.4,26.5 22.5,34.9 -14.1,8.4 -22.5,23.9 -22.5,34.9 0,5.8 0,23.2 0,41 0,25.9 -20.9,47 -46.8,47.3 -35.9,0.4 -83.5,1.7 -109.3,5.6 -45.1,6.7 -67.6,40.2 -67.6,40.2 0,0 -22.6,-33.5 -67.6,-40.2 -25.8,-3.9 -73.5,-5.2 -109.3,-5.6 -25.9,-0.3 -46.8,-21.4 -46.8,-47.3 0,-17.8 0,-35.2 0,-41 0,-11 -8.4,-26.5 -22.5,-34.9 14.1,-8.4 22.5,-23.9 22.5,-34.9 0,-5.8 0,-23.2 0,-41 0,-25.9 20.9,-47 46.8,-47.3 35.8,-0.4 83.5,-1.7 109.3,-5.6 45,-6.7 67.6,-40.2 67.6,-40.2z">
    <aapt:attr name="android:fillColor">
      <gradient 
          android:centerX="250.2"
          android:centerY="178"
          android:gradientRadius="234"
          android:type="radial">
        <item android:offset="0.26" android:color="#FF002E43"/>
        <item android:offset="1" android:color="#FF000F1B"/>
      </gradient>
    </aapt:attr>
  </path>
</vector>
```
![Screenshot 2023-04-24 at 8 44 36 AM](https://user-images.githubusercontent.com/1284454/233892223-2e2ffb72-bf24-4792-bfdb-4c8263df94fb.png)

Available features in beta-1.0.1 : 

* TexView background
* Clip for ImageView
* Border for ImageView
* Use background as Column, Box etc.. 


## Show an ImageView using a SVG shape clip
```kotlin
 VectorComposeView(
                VectorViewType.ImageVector(
                    vectorResource =  R.drawable.svg_shape,
                    imageSrc = R.drawable.flowers,
                ),
            )
```

## Show TextView as background SVG shape
```kotlin
 VectorComposeView(
                VectorViewType.TextVector(
                    vectorResource =  R.drawable.svg_shape,
                    text = "Hello world!",
                    backgroundColor = Color.Magenta,
                    fontSize = 22.sp,
                    fontFamily = RostiCreamy,
                ),
            )
```
## Show Plain SVG Shape
```kotlin
 VectorComposeView(
                VectorViewType.PlainVector(
                    vectorResource =  R.drawable.svg_shape,
                    backgroundColor = Color.Magenta,
                ),
            )
```

## Installation
- Add jitpack.io to your project
```
repositories {
    ...
    maven { url "https://jitpack.io" }
}
```
- Add Chimney library dependency
``` 
dependencies { 
  ...
  implementation 'com.github.januprasad:Chimney:v1.0.0'
}
```
