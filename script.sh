#!/bin/bash
JAVA=$(which java)
JAVAFX="/home/se2062020/javafx-sdk-11.0.2/lib"
case "$1" in
	"")
	"${JAVA}" -jar ./Jeporday.jar
	;;
	[bB])
		"${JAVA}" --module-path "${JAVAFX}" --add-modules javafx.controls,javafx.media,javafx.fxml -Dfile.encoding=UTF-8 -classpath ./bin:"${JAVAFX}/javafx.base.jar":"${JAVAFX}/javafx.controls.jar":"${JAVAFX}/javafx.fxml.jar":"${JAVAFX}/javafx.graphics.jar":"${JAVAFX}/javafx.media.jar":"${JAVAFX}/javafx.swing.jar":"${JAVAFX}/javafx.web.jar":"${JAVAFX}/javafx-swt.jar" application.Main
	;;
	*)
		echo "Invalid script arguments, to run using Jeporday.jar type './script'. If you want to build and run type './script buildrun'."
	;;
esac
