# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.

# Keep QRCode model classes
-keep class com.qrmaster.domain.model.** { *; }
-keep class com.qrmaster.data.local.** { *; }

# ZXing
-keep class com.google.zxing.** { *; }
-dontwarn com.google.zxing.**

# ML Kit
-keep class com.google.mlkit.** { *; }
-dontwarn com.google.mlkit.**