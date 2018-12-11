logger.info("Welcome " + person.getName() + "!!");

function checkAge(person) {
    if (person.getAge() != 33) {
        logger.warn("I'm sorry, but your age is not " + person.getAge().toString());
        logger.info("Setting age to 33");
        person.setAge(33);
    }
}

checkAge(person);

checkAge(new com.pbrandwijk.javascript.model.Person("Jane", 40));

