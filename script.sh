#!/bin/bash
# old image javafx
#JAVAFX="/home/se2062020/javafx-sdk-11.0.2/lib"

# new javafx for new image
JAVAFX="/usr/share/java/lib"
java --module-path "${JAVAFX}" --add-modules javafx.controls,javafx.media,javafx.fxml -jar ./Quinzical.jar
