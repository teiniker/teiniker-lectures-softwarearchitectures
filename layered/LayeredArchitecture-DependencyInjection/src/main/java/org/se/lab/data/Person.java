package org.se.lab.data;

public class Person
    implements Comparable<Person>
{
    final private long id;
    final private String firstName;
    final private String lastName;
    
    
    public Person(final long id, final String firstName, final String lastName)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    
    public long getId()
    {
        return id;
    }

    public String getFirstName()
    {
        return firstName;
    }
    
    public String getLastName()
    {
        return lastName;
    }

    
    /*
     * Methods inherited from java.lang.Object
     */    
    
    public String toString()
    {
        return getId() + "," + getFirstName() + "," + getLastName();
    }
    
    public boolean equals(Object obj)
    {
        System.out.println("Person.equals()");
        
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Person other = (Person) obj;
        if (id != other.id)
            return false;
        return true;
    }
    
    public int hashCode()
    {
        System.out.println("Person.hashCode()");

        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + (int) (id ^ (id >>> 32));
        return result;
    }

        
    /*
     * Methods defined in java.lang.Comparable
     */    
    
    public int compareTo(Person that)
    {
        System.out.println("Person.compareTo()"); 
        
        // Note that this code may give the wrong answer when there is overflow
        // return (int)(this.getId() - that.getId());
        
        return this.getId() < that.getId() ? -1 : 
            this.getId() == that.getId() ? 0 : 1;
    }    
}
