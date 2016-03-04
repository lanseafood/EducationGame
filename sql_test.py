import sql
  
sql.add_User("Paul")
sql.add_User("Fred")
sql.add_User("Derek")
sql.remove_User("Derek")

print "Users:"
for r in sql.get_Users():
	print r

print "\nPaul's current data:"
print sql.get_User_Data("Paul")
sql.update_User_Data("Paul", "00110AAFA01110051421")
print "\nPaul's updated data:"
print sql.get_User_Data("Paul")

sql.add_QA_Pair("What do lions eat?", "Whatever they want.")
sql.add_QA_Pair("What is this database?", "Your best friend.")
sql.add_QA_Pair("Why did the chicken cross the road?", "I don't know.")
sql.remove_QA_Pair(2)

print "\nQuestions and Answers:"
sql.print_QA_Pairs()

print "\nQuestion #3:"
print sql.get_QA_Pair(3)

# sql.commit_Changes()
# sql.close_Database()