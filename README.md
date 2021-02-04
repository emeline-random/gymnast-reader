# gymnast-reader
Java application that allows to see and edit PDF files using. It is also possible to merge multiple PDF files into one new file and to split a pdf into multiple parts. This application uses [IcePdf](http://www.icesoft.org/java/downloads/icepdf-downloads.jsf), [iTextPdf](https://github.com/itext/itextpdf) and [FlatLaf](https://github.com/JFormDesigner/FlatLaf)

###Options
When you launch the application without any argument, it will try to load the last files you have opened from a file named `gymnastReader.txt` in the same place the application is executed from. If you give the name of a file, the application will open this file.
> java -jar GymnastReader.jar [options] [file]

| Option | Result |
|---------|----------|
|-s path  |Allows to change the file where the last consulted files are loaded and saved |
|-h -\-help | Allows to show a quick explaination about the application |

###Installation
It is possible to directly use the jar to launch the application with the options defined above but it is also posible to construct an .exe file (to set this application as default on Windows). To do this you have to update the content of the file `release/gymnastPdfLauncher.py` with the appropriate paths. Then you can construct an .exe file from the python file using pyinstaller :
> pyinstaller -\-onefile -\-icon=path/to/GymnastReader.ico path/to/gymnastPdfLauncher.py

The produced .exe file can be used as default application to open PDF files on Windows.
