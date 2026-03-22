@echo off
echo Compiling Java files...
cd src\main\java
javac com\parallelprocessor\*.java com\parallelprocessor\counter\*.java com\parallelprocessor\executor\*.java com\parallelprocessor\processor\*.java com\parallelprocessor\scanner\*.java com\parallelprocessor\async\*.java com\parallelprocessor\aggregator\*.java

echo.
echo Running Parallel File Processor...
echo ----------------------------------
java com.parallelprocessor.Main
echo ----------------------------------
pause
