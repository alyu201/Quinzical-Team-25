#!/bin/bash
JAVA=$(which java)
JAVAFX="/home/se2062020/javafx-sdk-11.0.2/lib"
"${JAVA}" --module-path "${JAVAFX}" --add-modules javafx.controls,javafx.media,javafx.fxml -Dfile.encoding=UTF-8 -classpath "${JAVAFX}/javafx.base.jar":"${JAVAFX}/javafx.controls.jar":"${JAVAFX}/javafx.fxml.jar":"${JAVAFX}/javafx.graphics.jar":"${JAVAFX}/javafx.media.jar":"${JAVAFX}/javafx.swing.jar":"${JAVAFX}/javafx.web.jar":"${JAVAFX}/javafx-swt.jar" application.Main
