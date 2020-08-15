
#  SojoDia
![Android Studio](https://img.shields.io/badge/Android%20Studio-4.1.0%20RC01-green.svg)
![Kotlin](https://img.shields.io/badge/kotlin-1.4.0-yellow.svg)
[![Actions Status](https://github.com/NUmeroAndDev/SojoDia-android/workflows/Test/badge.svg)](https://github.com/NUmeroAndDev/SojoDia-android/actions)

## About  
関西大学高槻キャンパス用のバス時刻表アプリ  
[<img src="https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png"
alt="Get it on Google Play" height="80">](https://play.google.com/store/apps/details?id=com.numero.sojodia)

### Support  
API Level 23 ( Android 6.0 )

### Architecture  

<img src="img/module.png" width="50%" >

#### :app  
View、Presenter など UI 関連  

#### :repository  
時刻表データ、設定の読み込み  

#### :data  
時刻データのアクセス、ローカルへの保存、メモリキャッシュ  

#### :model  
アプリ全般で使用するモデルクラス  
