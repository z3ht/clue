echo This project has been tested using OpenJDK\'s Java 13 and JavaFX SDK 14
echo You can install a JavaFX SDK here: https://gluonhq.com/products/javafx/
echo WARNING: I have had AWFUL experience with JavaFX\'s \'lts\' version 11. I highly recommend version 14 instead
echo

if [ -f target/clue-1.0-SNAPSHOT.jar ]; then
  fxPath=$(head -n1 ./fxPath.conf)
else
  echo Please enter the path to your JavaFX SDK installation \(eg. path/to/javafx-sdk-14.0.1\):
  read -r fxPath
  fxPath="$fxPath/lib"
fi

if ! java -jar --module-path "$fxPath" --add-modules=javafx.base,javafx.controls,javafx.fxml target/clue-1.0-SNAPSHOT.jar; then

  if mvn clean install; then
    until [ -f target/clue-1.0-SNAPSHOT.jar ]; do
     sleep 3
    done
  else
    echo The maven build failed. Is maven installed?
    exit
  fi

  if ! java -jar --module-path "$fxPath" --add-modules=javafx.base,javafx.controls,javafx.fxml target/clue-1.0-SNAPSHOT.jar; then
    echo fxPath: "$fxPath"
    echo The JavaFX path you entered was incorrect
    exit
  fi

fi

echo "$fxPath" > fxPath.conf