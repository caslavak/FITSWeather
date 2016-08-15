FITS Weather
Copyright (C) <2016>  <Jakub Cech>
License: GNU General Public License

INSTALLATION
-----------------------------------------------------------------------------
To run this program you need to have JRE version 1.8 or newer installed.
You can use file dependency/jre-8u101-windows-i586-iftw.exe to install JRE.

RUN
-----------------------------------------------------------------------------
If you have 64bit version of Windows, run FITS_Weather64.exe,
otherwise (32bit version) run FITS_Weather.exe.  
The program requires internet connection

To make the program run at startup:
1. Run (Win+R) command: shell:startup
2. Create a shortcut to FITS_Weather.exe (or FITS_Weather64.exe in 64bit Windows)
   in the just opened folder. 

CONFIGURATION
-----------------------------------------------------------------------------
Configuration information is stored in "config.properties". This file
has to stay in the same folder as "FITS Weather.jar"
Fields:
ROOT_DIR ......... path to the root folder that cointains subfolders with images
SUBDIR_FORMAT .... date format for a subfolders (default yyyyMMdd), for more info
                   see https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
WEATHER_STATION .. weather station code (http://www.findu.com/)
LOG_DIR .......... folder for logs
OUTPUT_DIR ....... output directory [NOT USED]
OVERWRITE ........ 1=overwrite file, 0=preserve files [NOT USED]

TROUBLESHOOTING
-----------------------------------------------------------------------------
Problem:  Failed to load JVM
Solution: Check if you run correct exe file for your system version (32/64bit).
          Check if JRE 1.8+ is installed. 

NOTE
-----------------------------------------------------------------------------
Application was developed at Union College during a summer research                   