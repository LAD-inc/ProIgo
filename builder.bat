
@echo off

:start
cls
ECHO ============MAIN MENU============
ECHO -------------------------------------
echo 1. Build Desktop Application
echo 2. Run Desktop Application
echo 3. Maven Clean
ECHO -------------------------------------
set INPUT=
set /P INPUT=Please select an option:

IF /I '%INPUT%'=='1' GOTO BuildDesktop
IF /I '%INPUT%'=='2' GOTO runDesktop
IF /I '%INPUT%'=='3' GOTO MavenClean

ECHO ============INVALID INPUT============
ECHO -------------------------------------
ECHO Please select a number from the Main
echo Menu [1-9] or select 'Q' to quit.
ECHO -------------------------------------
ECHO ======PRESS ANY KEY TO CONTINUE======

PAUSE > NUL
GOTO start

:BuildDesktop

call %M2%/mvn -Pdesktop package

ECHO ============POST DESKTOP INSTALL MENU============
ECHO -------------------------------------
echo 1. Run Desktop Application
echo 2. Re-Build Desktop Application
echo Or Select Any Other Option To Return To Main Menu
ECHO -------------------------------------
set INPUT=
set /P INPUT=Please select an option:
IF /I '%INPUT%'=='1' GOTO runDesktop
IF /I '%INPUT%'=='2' GOTO BuildDesktop
GOTO start

:runDesktop

call "%JAVA_HOME%/bin/java" -jar desktop/target/proigo-desktop-1.0-SNAPSHOT-jar-with-dependencies.jar
pause
GOTO start

:MavenClean
call %M2%/mvn clean
pause
GOTO start
