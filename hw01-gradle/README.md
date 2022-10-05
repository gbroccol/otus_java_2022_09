## To start the application:
* ./gradlew build

## To launch fat jar 
* java -jar ./hw01-gradle/build/libs/gradleHelloWorld-0.1.jar

### To unzip the jar:
* unzip -l hw01-gradle.jar
* unzip -l gradleHelloWorld-0.1.jar

--------------------------------------

## The different names are just ways of packaging java apps.
Skinny – Contains ONLY the bits you literally type into your code editor, and NOTHING else.

Thin – Contains all of the above PLUS the app’s direct dependencies of your app (db drivers, utility libraries, etc).

Hollow – The inverse of Thin – Contains only the bits needed to run your app but does NOT contain the app itself. Basically a pre-packaged “app server” to which you can later deploy your app, in the same style as traditional Java EE app servers, but with important differences.

Fat/Uber – Contains the bit you literally write yourself PLUS the direct dependencies of your app PLUS the bits needed to run your app “on its own”.

--------------------------------------
