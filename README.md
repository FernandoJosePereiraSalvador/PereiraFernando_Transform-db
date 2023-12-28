# Relational Database to ObjectDB Transformer

This program provides converts a relational database in MySQL to an object-relational database (ObjectDB).
## Previous requirements

Before using this tool, make sure you have:

1. **Mapped Classes:** You need to have mapped entity classes that represent the tables in your relational database. Each class must have Java Persistence API (JPA) annotations to specify the mapping to the database.

2. **Constructor from a List of Values:** Each entity class must have a constructor that accepts a list of values in the same order as the columns of the corresponding table.

## Example
As an example, a model of the Astronaut table in ObjectDB is included, where a constructor has been implemented that facilitates seamless instantiation from a list of values, thus simplifying the transformation process.