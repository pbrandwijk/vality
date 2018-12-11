logger.info("Welcome " + person.getName() + "!!");

// Run the checkAge function from the library on a newly created Person object
checkAge(new com.pbrandwijk.vality.model.Person("Jane", 40));

// Run the checkAge function from the library on the Person object that was bound at run time
checkAge(person);

