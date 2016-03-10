import sql

# Read sample data into the profiles table
sql.add_User("Paul")
sql.add_User("Fred")
sql.add_User("Derek")
# Test removing a user
sql.remove_User("Derek")

# Print all stored users in the table
print "Users:"
for r in sql.get_Users():
	print r

# Test of reading and updating data in the profiles table
print "\nPaul's current data:"
print sql.get_User_QData("Paul")
sql.update_User_QData("Paul", "00110AAFA01110051421")
print "\nPaul's updated data:"
print sql.get_User_QData("Paul")

# Filling QA pool and testing remove function
sql.add_QA_Pair(1, "What do lions eat?", "Whatever they want.")
sql.add_QA_Pair(2, "What is this database?", "Your best friend.")
sql.add_QA_Pair(2, "Why did the chicken cross the road?", "I don't know.")
sql.add_QA_Pair(1, "Why are lions cool?", "Those gnarly teeth, yo.")
sql.remove_QA_Pair(2)

# Print all stored question/answer pairs
print "\nQuestions and Answers:"
sql.print_QA_Pairs()

# Test fetch of a particular q/a pair
print "\nQuestion #3:"
print sql.get_QA_Pair(3)

# Test fetch of all q/a pairs for a particular ecology
print "\nQuery for all lion questions:"
for qa in sql.get_QAs(1):
	print "%d: %s\n\t%s" % (qa[0], qa[1], qa[2])

# Fill ecology table
sql.add_ecology(1, "Lion", 3)
sql.add_ecology(2, "Vulture", 4)
sql.add_ecology(3, "Elephant", 2)
sql.add_ecology(4, "Giraffe", 2)
sql.add_ecology(5, "Acacia", 1)
sql.add_ecology(6, "Wildfruit", 1)

# Print all stored ecologies
print "\nQuery of all ecologies:"
sql.print_ecology()

sql.commit_Changes()
# sql.close_Database()