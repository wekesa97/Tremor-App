# Tremor-Detector-App

The mobile based detection and alert system has the following parts section for getting accelerometers values from the smartphones hardware, section to process the information obtained and identify when a threshold for a tremor to be identified. The database section to record the tremor event and get the date and time the tremor occurred and store it, section to allow the user to add or update and store the contact of a concerned party and a section to send an alert to the concerned party.   
The values to determine if a tremor has occurred or not based on the values from the hardware sensor were tested, analyzed and tested based on the severity of the shaking expected from the user who might have a tremor when having the phone. The application is also designed to keep working whether the user has the ability to send SMS, lacks a SIM card, airtime or concerned party’s mobile number, or has not given the application the permission to send and receive messages from the application.

 Relational database diagram
The database that implemented this system runs on SQLite .SQLite is a secure open source relational database management software that comes already implemented on all android devices. All the data in the system is stored in the same database.
3.6: Frontend implementation
The front end is involved in the description of the User Interface (UI) and the objects that will appear on the screen and allow the user to interact with the system. It is written in XML (Extensible Markup Language).It describes the buttons, text views, list views and layouts of the entire screen. 
 System Requirements
1. Android Mobile Device
The smartphone hosts the application file that will be installed and be able to be accessed there. The minimum smartphone specifications are:
2. 512 MB Memory (Random Access Memory)
3. Processor
4. 1 GB External Storage
5 Operating System (Android 4 and above or API 21 and above
6. GSM SIM card with Airtime

 Database Helper 
This separate file allow the application to create, access, upgrade, insert and access data. The class DataBaseHelper is instantiated as a subclass of the parent class SQLiteOpenHelper .The class describes string variables to store the database name, table name and for ID and Tremor event columns.
It contains a method onCreate that is called by a database object db. The method uses the object to run a SQL statement that creates a table with two columns.
It contains a method OnUpgrade that is called by database object and integers representing the old database version and the new database version. The method uses the object to run a SQL statement that deletes a table it exists when called.
It contains a method insertData  that is called by a string to insert the event data to the database..the method uses a database object to open the database and uses an object contentValues to put the string contents to the table.
An if condition is then checked whether the data was inserted successfully or it failed then the database is closed.
A function getAllData that returns a cursor data type is used to read the data that is stored in the database. An object of the database is used to open the database and run an SQL statement to select all values from the table in the database.
![Screenshot_20200922-181724](https://user-images.githubusercontent.com/30049042/109650481-53f33980-7b6e-11eb-8788-5602e7b41331.png)
![Screenshot_20200922-181735](https://user-images.githubusercontent.com/30049042/109650482-55bcfd00-7b6e-11eb-9aca-09f29cc5f3af.png)
![Screenshot_20200922-181823](https://user-images.githubusercontent.com/30049042/109650485-56559380-7b6e-11eb-88a7-771219f0ec97.png)
![Screenshot_20200922-181832](https://user-images.githubusercontent.com/30049042/109650488-5786c080-7b6e-11eb-9998-77aca50d728b.png)
![Screenshot_20200922-181849](https://user-images.githubusercontent.com/30049042/109650493-59e91a80-7b6e-11eb-8a4d-25a0bc5afab7.png)
![Screenshot_20200922-181905](https://user-images.githubusercontent.com/30049042/109650495-5a81b100-7b6e-11eb-8b50-a3df72fee47f.png)
![Screenshot_20200922-181909](https://user-images.githubusercontent.com/30049042/109650500-5bb2de00-7b6e-11eb-9162-7b466fc2a87d.png)
![Screenshot_20200922-182000](https://user-images.githubusercontent.com/30049042/109650506-5c4b7480-7b6e-11eb-9012-c28516c3c460.png)
![Screenshot_20200922-182005](https://user-images.githubusercontent.com/30049042/109650509-5d7ca180-7b6e-11eb-8e82-54071e26e79e.png)
![Screenshot_20200922-182036](https://user-images.githubusercontent.com/30049042/109650513-5eadce80-7b6e-11eb-824d-4bea6f9b6737.png)
![Screenshot_20200922-182138](https://user-images.githubusercontent.com/30049042/109650517-5f466500-7b6e-11eb-8f08-d9362121ae56.png)
![Screenshot_20200922-182202](https://user-images.githubusercontent.com/30049042/109650520-60779200-7b6e-11eb-8f79-a1a81f897fbc.png)
![Screenshot_20200922-192054](https://user-images.githubusercontent.com/30049042/109650521-61102880-7b6e-11eb-8f77-3f57b8309184.png)
![wp_ss_20200922_0001](https://user-images.githubusercontent.com/30049042/109650524-62415580-7b6e-11eb-9d94-b3ffb9c0b0e3.png)


Shared Preference Database
Shared Preference is a type of lightweight database that is fast when it holds less data and is mainly used to store data that is limited and changes frequently.it is offered by android and has been implemented in our system to store the value for the phone number of the concerned party.
On this project, I implement Shared Preference by creating variables for save button, read button, edit text section and file name. A method saveFile gets value from the edit text variable and converts it into a string. A shared preference object is then created that is used to enter the string to the shared Preference database and send feedback if the data is saved successfully.
A method readFile when called gets the value from the shared Preference database object and calls a method to get the string and store it in another variable for phone number .it then send feedback if the reading was successful.

 SMS Section
This section involved importing a telephony library and an SMS manager. It can only work when the user has gone to the phones settings and checked the permission to allow the application to send messages. An object of the SMS manager is created and it’s allowed to call a function  for sending Text Message which takes the phone number and the message as its parameters.

 Motion Sensor Section
An acceleration sensor measures the acceleration applied to the device, including the force of gravity. Current acceleration, last acceleration, acceleration necessary for shaking and sensor manager variable are declared.
An object of sensor event listener is created that calls the method onSensorChanged .The method is called by a sensor event that gets the acceleration force along the x, y and z-axes with estimated bias compensation in m/s2 as units of measure. A variable for storing the current acceleration assigns its value of the last acceleration that will be used to calculate the difference between the two values of acceleration obtained.
The square root of the sum of the squares of each coordinate is used to get the current acceleration, which is meant, to measure acceleration independent of the orientation and posture of the phone when the shaking is happening.
The difference between the current acceleration and the last acceleration is calculated and stored as delta. The threshold acceleration that is necessary for rapid shaking is calculated by finding the product of 0.9 and shaking acceleration and sum of the delta.
An if-statement is then used to check if the acceleration is greater than 12 and therefore be considered a rapid shaking. An integer variable is then used to count the number of times the condition is met so as to avoid recording any shake as a tremor .The user will see the number of times the If-statement is executed .If the condition is met more than 4 times the data is sent as SMS and stored in the database.
A calendar object is created from the calendar class to instantiate the event. A  date format object is created from the simple date format class to format the date so as to include date, day, hour and minutes and to store the string variable to a dateTime variable.
The code to be executed to send an SMS is surrounded by a try and catch block so as to prevent the application from crashing if the user has not allowed permission, presented a valid phone number or has no valid SIM card that can send a message.
The sensor class also has a method to handle when the Accuracy changes which is empty in this system since it is not needed. In addition, onResume function is called after the first shaking has occurred so as to call the onSensorChanged method another time after the first time there is shaking.
The last method is onPause which is called to call the sensor manager object which then calls the unregister function to unregister the sensor Listener when another event on the phone occurs.




