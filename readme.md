Splitting up the work :)

here for convenience
compile and run commands that complain the least

people added things and I don't know what so we're loading all modules now.
  
  javac --module-path lib/ --add-modules ALL-MODULE-PATH Browser.java

  java --module-path lib/ --add-modules ALL-MODULE-PATH --enable-native-access=javafx.graphics,javafx.web,javafx.media Browser
