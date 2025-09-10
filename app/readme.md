# Android Calculator

A calculator app built in **Java** with **Android Studio**.  
Implements the **Shunting Yard Algorithm** for correct operator precedence and supports extended operations like square root, powers, modulus, and π.

## Features
- Basic arithmetic: `+`, `-`, `×`, `÷`
- Advanced operations:
    - Modulus (`mod`)
    - Percentage (`%`)
    - Square (`x²`)
    - Square root (`√`)
    - Pi (`π`)
- Parentheses with **implicit multiplication** (e.g., `(2+3)(2+3)` is valid)
- Separate **input view** and **answer view**
- Dynamic text resizing for long calculations
- Simple retro-style UI with custom button colors

## How to Install
### Option 1: Android Phone
1. Go to this repository and download the **signed APK** file from a mobile browser or from your computer.
2. Transfer the file to your Android phone (if you downloaded it on your computer first).
3. Open it and allow installation from unknown sources.
4. Launch the Calculator app.

### Option 2: Android Emulator
1. Download and install [Android Studio](https://developer.android.com/studio).
2. Set up an Android Virtual Device (AVD) using the AVD Manager.
3. Drag and drop the downloaded APK into the emulator window.
4. Open the Calculator app inside the emulator.

## How to Use
- Tap numbers and operators to enter an expression.
- The **top display** (`calculateTextView`) shows your typed input.
- Press `=` to compute. The result appears in the **answer display** (`answerTextView`).
- Start typing again and both displays clear automatically.

## Future Ideas
- Add calculation history
- Add trigonometric functions (`sin`, `cos`, `tan`)
- Custom themes (dark/light modes)

