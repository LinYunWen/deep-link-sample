# Deep Link Sample

## Introduction
It is the sample of using Deep Link to open the KKBOX app in others app, and changing the next song every 10 seconds.

## Prerequirements
1. [android studio](https://developer.android.com/studio/index.html) 2+ (3+ will be best)
2. devices with android os
3. [KKBOX OPEN API client id and secret](https://developer.kkbox.com/#/signin)

> Note:
> You need to go to **[KKBOX OPEN API developer site](https://developer.kkbox.com/#/)** for sign up an account then new an app and get the client id and secret.

## Setup
1. go to MainActivity.kt
2. set your access token in companion object

```javascript
companion object {
    ...
    val accessToken = "YOUR_ACCESS_TOKEN"
    ...
}
```

## Operations
1. step 1: **make sure device is connect to wifi or network**
2. step 2: press "Start Recording" button and you will hear a beep then start to speak.
3. step 3: after speaking, press "Stop Recording" button.
4. step 4: It will open KKBOX app and auto-play the song just searching.
5. It will change next song every 10 seconds.

## Reference
1. [KKBOX developer site](https://developer.kkbox.com/#/)
2. [KKBOX OPEN API doc](https://docs.kkbox.codes/docs)
3. [Kotlin](https://kotlinlang.org/)