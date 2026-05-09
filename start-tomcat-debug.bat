@echo off
echo Killing old Tomcat/Java processes...
for /f "tokens=5" %%a in ('netstat -ano ^| findstr :8080') do taskkill /F /PID %%a >nul 2>nul
for /f "tokens=5" %%a in ('netstat -ano ^| findstr :8000') do taskkill /F /PID %%a >nul 2>nul

timeout /t 1 >nul

echo Forcing JDK17...
set "JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-17.0.17.10-hotspot"
set "JRE_HOME=C:\Program Files\Eclipse Adoptium\jdk-17.0.17.10-hotspot"
set "PATH=%JAVA_HOME%\bin;%PATH%"

echo Building Maven exploded deployment...
call mvn clean compile war:exploded -DskipTests

IF %ERRORLEVEL% NEQ 0 (
   echo BUILD FAILED - TOMCAT NOT STARTED
   exit /b %ERRORLEVEL%
)

echo Starting Tomcat JPDA...
cd /d D:\java-projekts\apache-tomcat\bin
call catalina.bat jpda run