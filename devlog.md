10/07/2025 6:12PM

There will be 3 seperate files. The logger, encryption, and driver programs.

The logger program will write log messages to a log file. The log message will be recorded, with a timestamp. 
The program should accept a single line command prompt, and will accept log messages via standard input until receiving a QUIT message.

The encryption program will accept commands given as lines via standard input. The first word will be treated as a command, and all following words will be treated as the arguments for the command. 
The currently set key is remembered by the encryption program, and the program will be able to handle the following commands: PASS, ENCRYPT, DECRYPT, QUIT, RESULT, and ERROR.

The driver program will accept a single command line argument, and create two new processes which will execute the logger and encryption programs. Once set up, this program should print a menu of commands, as well as 
asking the user to choose a command. The program will loop until recieving the QUIT command. Additionally, each command and result of each command will be logged. This program will accept the following commands: 
password, encrypt, decrypt, history, and quit. 
