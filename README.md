# Java-Swing-Customer-Management-System
Standalone desktop application for maintaining customers information

The application provides the ability to add, modify and delete information for customers

The main screen of the app include:

A table that displays the list of clients
Panel with detailed information about the selected client of the table
Button to add a new client
Button to edit the selected client
Button to delete the selected client.
Pressing the buttons for adding / editing opens a modal dialog for adding / editing of an entry. Double click on a table row also opens the dialog for editing.

The data is persisted in an XML file ("data.xml"), located in the same directory where is the executable file of the application. When you start the application, the data from this file is loaded into memory.

When marking the client (ie row of the table), in detailed panel is displayed data for this client (read-only)
