In order to successfully run SQLiteJDBC in your code, you must obtain the latest version of sqlite-jdbc-(VERSION).jar from the SQLite JDBC repository here:

https://bitbucket.org/xerial/sqlite-jdbc/downloads

Download it into the same folder as your code, and once your code is ready, perform the following CMD commands:

For Windows:

$javac SQLiteJDBC.java
$javac <your main class>.java
$java -classpath ".;sqlite-jdbc-<VERSION>.jar" <your main file>

For other systems(?):

$javac SQLiteJDBC.java
$javac <your main class>.java
$java -classpath ".:sqlite-jdbc-<VERSION>.jar" <your main file>
