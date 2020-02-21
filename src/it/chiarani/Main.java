package it.chiarani;

public class Main {

    public static void main(String[] args) {
        String[] inputs  = new String[] {"a_example.txt", "b_read_on.txt", "c_incunabula.txt", "d_tough_choices.txt", "e_so_many_books.txt", "f_libraries_of_the_world.txt"};

	    FileManager.readFromFile(inputs[4]);

	    // FileManager.writeToFile(inputs[3], "test");
    }
}
