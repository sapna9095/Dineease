@echo off
setlocal
cd /d "%~dp0"
title DineEase AI - Starting...

echo ========================================================
echo               Starting DineEase AI Dashboard
echo ========================================================
echo.

where java >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] 'java' was not found in your PATH.
    echo Please make sure Java 17+ is installed.
    echo.
    pause
    exit /b 1
)

java -version
echo.
echo Launching DineEase AI...

java "-Dsun.java2d.uiScale=1" "-Dflatlaf.uiScale=1" -cp "target\classes;lib\*" com.dineease.Main

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo [ERROR] DineEase application exited with code %ERRORLEVEL%.
    echo Please check the error message above.
    echo.
    pause
)
