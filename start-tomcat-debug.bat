@echo off
setlocal

set "PROJECT_DIR=%~dp0"
set "TOMCAT_HOME=D:\java-projekts\apache-tomcat"

echo Killing old Tomcat/Java processes...
for /f "tokens=5" %%a in ('netstat -ano ^| findstr :8080') do taskkill /F /PID %%a >nul 2>nul
for /f "tokens=5" %%a in ('netstat -ano ^| findstr :8000') do taskkill /F /PID %%a >nul 2>nul

ping 127.0.0.1 -n 2 >nul

echo Forcing JDK17...
set "JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-17.0.17.10-hotspot"
set "JRE_HOME=C:\Program Files\Eclipse Adoptium\jdk-17.0.17.10-hotspot"
set "PATH=%JAVA_HOME%\bin;%PATH%"

echo Building Maven exploded deployment...
call mvn clean compile war:exploded -DskipTests

IF %ERRORLEVEL% NEQ 0 (
   echo BUILD FAILED - TOMCAT NOT STARTED
   echo If this is the first run, verify Maven can access https://repo.maven.apache.org
   exit /b %ERRORLEVEL%
)

echo Deploying ROOT app to Tomcat...
if exist "%TOMCAT_HOME%\webapps\ROOT" rmdir /s /q "%TOMCAT_HOME%\webapps\ROOT"
mkdir "%TOMCAT_HOME%\webapps\ROOT" >nul 2>nul
xcopy "%PROJECT_DIR%target\ROOT\*" "%TOMCAT_HOME%\webapps\ROOT\" /e /i /y >nul

IF %ERRORLEVEL% GEQ 4 (
   echo DEPLOY FAILED - FILE COPY ERROR
   exit /b %ERRORLEVEL%
)

echo Starting Tomcat JPDA...
cd /d "%TOMCAT_HOME%\bin"
call catalina.bat jpda run
