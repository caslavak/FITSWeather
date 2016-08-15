FITS Weather
Copyright (C) <2016>  <Jakub Cech>
License: GNU General Public License

INSTALLATION
-----------------------------------------------------------------------------
To run this program you need to have JRE version 1.8 or newer installed.
You can use file jre-8u101-windows-i586-iftw.exe to install JRE.

RUN
-----------------------------------------------------------------------------
In desktop environment, double-click the "FITS Weather.jar".
To run the program from a command line use "javaw -jar FITS Weather.jar"
The program requires internet connection

CONFIGURATION
-----------------------------------------------------------------------------
Configuration information is stored in "config.properties". This file
has to stay in the same folder as "FITS Weather.jar"
Fields:
ROOT_DIR ......... path to the root folder that cointains subfolders with images
SUBDIR_FORMAT .... date format for a subfolders (default yyyyMMdd), for more info
                   see https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
WEATHER_STATION .. weather station code (http://www.findu.com/)
OUTPUT_DIR ....... output directory [NOT USED]
OVERWRITE ........ 1=overwrite file, 0=preserve files [NOT USED]

NOTE
-----------------------------------------------------------------------------
Application was developed at Union College during a summer research                   