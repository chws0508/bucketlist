# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions

# With R8 full mode generic signatures are stripped for classes that are not
# kept. Suspend functions are wrapped in continuations where the type argument
# is used.
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation
-dontwarn org.apiguardian.api.**

-keep class androidx.datastore.*.** {*;}
-keepclassmembers class * extends com.google.protobuf.GeneratedMessageLite* {
   <fields>;
}
