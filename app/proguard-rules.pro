# Add project specific ProGuard rules here.

# Keep line numbers for debugging stack traces
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Keep annotations
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes Exceptions

#---------------------------------
# Kotlin
#---------------------------------
-dontwarn kotlin.**
-keep class kotlin.Metadata { *; }
-keepclassmembers class kotlin.Metadata {
    public <methods>;
}

#---------------------------------
# Jetpack Compose
#---------------------------------
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**

# Keep Compose runtime classes
-keep class androidx.compose.runtime.** { *; }

#---------------------------------
# Retrofit & OkHttp
#---------------------------------
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
-keep class retrofit2.** { *; }
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# Keep Retrofit Response/Call types
-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

#---------------------------------
# Gson
#---------------------------------
-keepattributes Signature
-keepattributes *Annotation*
-dontwarn sun.misc.**
-keep class com.google.gson.** { *; }
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

# Keep API model classes (for Gson serialization)
-keep class com.sjani.usnationalparkguide.data.model.** { *; }

#---------------------------------
# Room Database
#---------------------------------
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-dontwarn androidx.room.paging.**

#---------------------------------
# Firebase
#---------------------------------
-keep class com.google.firebase.** { *; }
-keep class com.google.android.gms.** { *; }
-dontwarn com.google.firebase.**
-dontwarn com.google.android.gms.**

#---------------------------------
# Firebase UI Auth
#---------------------------------
-keep class com.firebase.ui.auth.** { *; }
-dontwarn com.firebase.ui.auth.**

#---------------------------------
# Coil
#---------------------------------
-dontwarn coil.**
-keep class coil.** { *; }

#---------------------------------
# Glance (App Widgets)
#---------------------------------
-keep class androidx.glance.** { *; }

#---------------------------------
# Coroutines
#---------------------------------
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembers class kotlinx.coroutines.** {
    volatile <fields>;
}

#---------------------------------
# DataStore
#---------------------------------
-keep class androidx.datastore.** { *; }
-keepclassmembers class * extends com.google.protobuf.GeneratedMessageLite {
    <fields>;
}

#---------------------------------
# Keep Entity classes
#---------------------------------
-keep class com.sjani.usnationalparkguide.data.entity.** { *; }

#---------------------------------
# R8 full mode compatibility
#---------------------------------
-allowaccessmodification
-repackageclasses
