import sqlite3 as sql

# Initialize global connection variables to database
connection = sql.connect("gamedata.db")
cursor = connection.cursor()

# If tables do not exist, initialize them
sql_command = """
CREATE TABLE IF NOT EXISTS profiles (
name VARCHAR(20) NOT NULL PRIMARY KEY,
data VARCHAR(20) NOT NULL);"""

cursor.execute(sql_command)

sql_command = """
CREATE TABLE IF NOT EXISTS questions (
question VARCHAR(256) NOT NULL,
answer VARCHAR(64) NOT NULL);"""

cursor.execute(sql_command)


## METHODS ##
# Add a new user to the profiles
def add_User(name):
	sql_command = """
	INSERT INTO profiles
	VALUES ("%s", "00000000000000000000");"""

	cursor.execute(sql_command % name)

# Return list of all users in table
def get_Users():
	sql_command = "SELECT name FROM profiles;"
	
	cursor.execute(sql_command)
	list = cursor.fetchall()
	out = []
	for r in list:
		out.append(r[0])
	
	return out

# Read the user data from the profiles table, given the name of the user
def get_User_Data(name):
	sql_command = """
	SELECT data FROM profiles
	WHERE name="%s";"""

	cursor.execute(sql_command % name)
	res = cursor.fetchone()
	return res[0]
	
# Update the user data in the profiles table, given the name of the user & new data
def update_User_Data(name, data):
	sql_command = """
	UPDATE profiles
	SET data="%s"
	WHERE name="%s";"""

	cursor.execute(sql_command % (data, name))

# Remove a user from the database
def remove_User(name):
	sql_command = """
	DELETE FROM profiles
	WHERE name="%s";"""

	cursor.execute(sql_command % name)

# Add a new QA pair into the database	
def add_QA_Pair(question, answer):
	sql_command = """
	INSERT INTO questions
	VALUES ("%s", "%s");"""
	
	cursor.execute(sql_command % (question, answer))

# Print list of all QA pairs in database
def print_QA_Pairs():
	sql_command = "SELECT ROWID, * FROM questions;"
	
	cursor.execute(sql_command)
	res = cursor.fetchall()
	for r in res:
		print "%d: %s\n\t%s" % (r[0], r[1], r[2])

# Returns a single QA pair tuple, given its row id
def get_QA_Pair(rowid):
	sql_command = """
	SELECT * FROM questions
	WHERE ROWID="%s";"""

	cursor.execute(sql_command % rowid)
	return cursor.fetchone()

# Remove a QA pair into the database	
def remove_QA_Pair(rowid):
	sql_command = """
	DELETE FROM questions
	WHERE ROWID="%d";"""
	
	cursor.execute(sql_command % rowid)

# Save changes to the tables
def commit_Changes():
	connection.commit()

# "Shut down" the database when finished
def close_Database():
	connection.close()