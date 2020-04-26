echo This project has been tested using OpenJDK\'s Java 13 and JavaFX SDK 14
echo You can install a JavaFX SDK here: https://gluonhq.com/products/javafx/
echo WARNING: I have had AWFUL experience with JavaFX\'s \'lts\' version 11. I highly recommend version 14 instead
echo
echo Please enter the path to your JavaFX SDK installation \(eg. path/to/javafx-sdk-14.0.1\):
read -r fxPath

fxPath="$fxPath/lib"

if ! java -jar --module-path "$fxPath" --add-modules=javafx.base,javafx.controls,javafx.fxml target/clue-1.0-SNAPSHOT.jar; then
  mvn clean install
  mvnStatus=$?
  sleep 3
  if ! java -jar --module-path "$fxPath" --add-modules=javafx.base,javafx.controls,javafx.fxml target/clue-1.0-SNAPSHOT.jar; then
    if [ $mvnStatus -eq 0 ]; then
      echo The JavaFX path you entered was incorrect
    else
      echo The maven installation failed. Have you imported all Maven dependencies?
    fi
  fi
fi

