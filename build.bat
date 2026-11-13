@echo off
setlocal
cd /d "%~dp0"
title DineEase AI - Compilation and Build

echo ========================================================
echo          DineEase AI - Java 17 Project Build
echo ========================================================
echo.

where javac >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] 'javac' was not found in your PATH.
    echo Please make sure JDK 17 is installed and added to PATH.
    echo.
    pause
    exit /b 1
)

echo [1/3] Creating output directory target\classes...
if not exist "target\classes" mkdir "target\classes"

echo [2/3] Compiling Java source code with UTF-8 encoding...
javac -encoding UTF-8 -cp "lib\*" -d target\classes src\main\java\com\dineease\Main.java src\main\java\com\dineease\*.java src\main\java\com\dineease\**\*.java 2>nul
if %ERRORLEVEL% NEQ 0 (
    powershell -Command "javac -encoding UTF-8 -cp 'lib\*' -d target\classes (Get-ChildItem -Path src\main\java -Filter '*.java' -Recurse | ForEach-Object { $_.FullName })"
)

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo [ERROR] Compilation failed!
    echo.
    pause
    exit /b 1
)

echo [3/3] Copying application resources...
if exist "src\main\resources" (
    xcopy /E /I /Y "src\main\resources\*" "target\classes\" >nul
)

echo.
echo ========================================================
echo         BUILD SUCCESSFUL - Ready to Run (run.bat)
echo ========================================================
echo.
if "%1" neq "--nopause" pause

