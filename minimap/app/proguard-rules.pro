# 代码混淆压缩比，在0~7之间，默认为5,一般不下需要修改
-optimizationpasses 7
# 混淆时不使用大小写混合，混淆后的类名为小写
# windows下的同学还是加入这个选项吧(windows大小写不敏感)
-dontusemixedcaseclassnames
# 指定不去忽略非公共的库的类
# 默认跳过，有些情况下编写的代码与类库中的类在同一个包下，并且持有包中内容的引用，此时就需要加入此条声明
-dontskipnonpubliclibraryclasses
# 指定不去忽略非公共的库的类的成员
-dontskipnonpubliclibraryclassmembers
-optimizations !code/simplification/artithmetic,!field/*,!class/merging/*
-dontwarn com.autonavi.minimap.**
-keep class com.autonavi.minimap.ThirdNavigationUtil