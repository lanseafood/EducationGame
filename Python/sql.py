import sqlite3 as sql

# Initialize global connection variables to database
connection = sql.connect("gamedata.db")
cursor = connection.cursor()

# If tables do not exist, initialize them
sql_command = """
CREATE TABLE IF NOT EXISTS profiles (
name VARCHAR(20) NOT NULL PRIMARY KEY,
questions VARCHAR(20) NOT NULL,
interests VARCHAR(20) NOT NULL);"""

cursor.execute(sql_command)

sql_command = """
CREATE TABLE IF NOT EXISTS questions (
e_id INT NOT NULL,
question VARCHAR(256) NOT NULL,
answer VARCHAR(64) NOT NULL);"""

cursor.execute(sql_command)

sql_command = """
CREATE TABLE IF NOT EXISTS ecology (
id INT NOT NULL PRIMARY KEY,
name VARCHAR(32) NOT NULL,
tropic INT NOT NULL);"""

cursor.execute(sql_command)



### PROFILES TABLE METHODS ###
# Add a new user to the profiles
def add_User(name):
	sql_command = """
	INSERT INTO profiles
	VALUES ("%s", "00000000000000000000", "00000000000000000000");"""

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

# Read the user question data from the profiles table, given the name of the user
def get_User_QData(name):
	sql_command = """
	SELECT questions FROM profiles
	WHERE name="%s";"""

	cursor.execute(sql_command % name)
	res = cursor.fetchone()
	return res[0]
	
# Update the user question data in the profiles table, given the name of the user & new data
def update_User_QData(name, data):
	sql_command = """
	UPDATE profiles
	SET questions="%s"
	WHERE name="%s";"""

	cursor.execute(sql_command % (data, name))
	
# Read the user interest data from the profiles table, given the name of the user
def get_User_Interests(name):
	sql_command = """
	SELECT interests FROM profiles
	WHERE name="%s";"""

	cursor.execute(sql_command % name)
	res = cursor.fetchone()
	return res[0]
	
# Update the user interest data in the profiles table, given the name of the user & new data
def update_User_Interests(name, data):
	sql_command = """
	UPDATE profiles
	SET interests="%s"
	WHERE name="%s";"""

	cursor.execute(sql_command % (data, name))

# Remove a user from the database
def remove_User(name):
	sql_command = """
	DELETE FROM profiles
	WHERE name="%s";"""

	cursor.execute(sql_command % name)


### QUESTIONS TABLE METHODS ###
# Add a new QA pair into the database	
def add_QA_Pair(e_id, question, answer):
	sql_command = """
	INSERT INTO questions
	VALUES ("%d", "%s", "%s");"""
	
	cursor.execute(sql_command % (e_id, question, answer))

# Print list of all QA pairs in database
def print_QA_Pairs():
	sql_command = "SELECT ROWID, * FROM questions;"
	
	cursor.execute(sql_command)
	res = cursor.fetchall()
	for r in res:
		print "%d: %s\n\t%s" % (r[0], r[2], r[3])

# Returns a single QA pair tuple, given its row id
def get_QA_Pair(rowid):
	sql_command = """
	SELECT * FROM questions
	WHERE ROWID="%s";"""

	cursor.execute(sql_command % rowid)
	return cursor.fetchone()
	
# Retrieve a list of questions and answers for a particular species
def get_QAs(e_id):
	sql_command = """
	SELECT ROWID, question, answer FROM questions
	WHERE e_id="%d";"""
	
	cursor.execute(sql_command % e_id)
	return cursor.fetchall()

# Remove a QA pair into the database	
def remove_QA_Pair(rowid):
	sql_command = """
	DELETE FROM questions
	WHERE ROWID="%d";"""
	
	cursor.execute(sql_command % rowid)



### ECOLOGY TABLE METHODS ###
# Add an entry to the ecology table
def add_ecology(id, name, tropic):
	sql_command = """
	INSERT INTO ecology
	VALUES ("%d", "%s", "%d");"""
	
	cursor.execute(sql_command % (id, name, tropic))

# Fetch name of id'd specimen in ecology
def get_ecology_name(id):
	sql_command = """
	SELECT name FROM ecology
	WHERE id="%d";"""

	cursor.execute(sql_command % id)
	res = cursor.fetchone()
	return res[0]

# Fetch tropic level of id'd specimen in ecology
def get_ecology_tropic(id):
	sql_command = """
	SELECT tropic FROM ecology
	WHERE id="%d";"""

	cursor.execute(sql_command % id)
	res = cursor.fetchone()
	return res[0]

# Fetch id of named specimen in ecology
def get_ecology_id(name):
	sql_command = """
	SELECT id FROM ecology
	WHERE name="%s";"""

	cursor.execute(sql_command % name)
	res = cursor.fetchone()
	return res[0]

# Remove an id'd entry to the ecology table
def remove_ecology(id):
	sql_command = """
	DELETE FROM ecology
	WHERE id="%d";"""
	
	cursor.execute(sql_command % id)
	
# Print ecology list
def print_ecology():
	sql_command = "SELECT * FROM ecology;"
	
	cursor.execute(sql_command)
	res = cursor.fetchall()
	for r in res:
		print "%d: %s\n\tTropic level %s" % (r[0], r[1], r[2])

# Save changes to the tables
def commit_Changes():
	connection.commit()

# "Shut down" the database when finished
def close_Database():
	connection.close()