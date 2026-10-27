Splitting up the work :)

here for convenience
compile and run commands that complain the least
  
  javac --module-path lib/ --add-modules javafx.controls,javafx.web,javafx.media Browser.java

  java --module-path lib/ --add-modules javafx.controls,javafx.web,javafx.media --enable-native-access=javafx.graphics,javafx.web,javafx.media Browser
