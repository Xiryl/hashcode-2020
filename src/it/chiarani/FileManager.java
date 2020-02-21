package it.chiarani;

import java.io.*;
import java.net.URL;
import java.util.*;

public class FileManager {


    public static void readFromFile(String fileName) {
        try {
            String path  = "input/" + fileName;
            // read file
            URL url = Main.class.getResource(path);

            System.out.println(String.format("Carico %s", path));

            File myObj = new File(url.getPath());
            Scanner myReader = new Scanner(myObj);

            String firstLine = myReader.nextLine();
            int qtaBook = Integer.parseInt(firstLine.split(" ")[0]);
            int qtaLibraries = Integer.parseInt(firstLine.split(" ")[1]);
            int qtaDaysForScan = Integer.parseInt(firstLine.split(" ")[2]);

            System.out.println(String.format("qtaBook:%s, qtaLibraries:%s, qtaDaysForScan:%s", qtaBook, qtaLibraries, qtaDaysForScan));

            String secondLine = myReader.nextLine();

            String[] scores = secondLine.split(" ");
            int[] bookScores = new int[qtaBook];

            for(int i = 0; i < scores.length; i++) {
                bookScores[i] = Integer.parseInt(scores[i]);
                // System.out.print(String.format("%s ", bookScores[i]));
            }
            System.out.println("");
            List<Library> librerie = new ArrayList<>();
            int libraryIndex = 0;

            HashSet<Integer> idOfBooksFinded = new HashSet<>();

            while (myReader.hasNextLine()) {
                List<Book> booksList = new ArrayList<>();
                String libraryLine = myReader.nextLine();
                // System.out.println(libraryLine);
                int qtabookslibrary = 0;

                try {
                    qtabookslibrary = Integer.parseInt(libraryLine.split(" ")[0]);
                }
                catch (Exception ex) {
                    break;
                }

                int signupdays = Integer.parseInt(libraryLine.split(" ")[1]);
                int booksperdays = Integer.parseInt(libraryLine.split(" ")[2]);

                String booksLine = myReader.nextLine();

            /*    if(qtaDaysForScan < booksperdays) {
                    continue;
                }*/

                String[] books = booksLine.split(" ");
                int bookScore = 0;


                // lista di libri
                for(String x : books) {
                    int bookId = Integer.parseInt(x);

                 /*   if(!idOfBooksFinded.contains(bookId)) {
                        booksList.add(new Book(bookId, bookScores[bookId]));
                        bookScore += bookScores[bookId];
                        idOfBooksFinded.add(bookId);
                    }*/
                    booksList.add(new Book(bookId, bookScores[bookId]));
                    bookScore += bookScores[bookId];
                    idOfBooksFinded.add(bookId);

                }

                // ordino i libri per score
                Collections.sort(booksList, new Comparator<Book>() {
                    @Override
                    public int compare(Book book, Book t1) {
                        return t1.getScore().compareTo(book.getScore());
                    }
                });

                // creo libreria
                Library tmpLib = new Library(libraryIndex, qtabookslibrary, signupdays, booksperdays, bookScore, null, null);

                // aggiungo libri alla libreria
                tmpLib.setBookList(booksList);

                // aggiungo la lib alle librerie
                librerie.add(tmpLib);

                System.out.println(String.format("Ho trovato lib.id:%s, ha %s libri, bookScoreTot: %s", tmpLib.getId(), tmpLib.getBookList().size(), tmpLib.getScoreofbookforlibrary()));
                libraryIndex++;
            }

            myReader.close();

            // finito di leggere il file

            for(Library l : librerie) {
                // System.out.println(String.format("La lib:%s ha sign.up:%s", l.getId(), l.getSignupdays()));
                // int score = ((l.getScoreofbookforlibrary()) / l.getSignupdays() ) / (qtaDaysForScan - ( l.getBookList().size() / l.getBooksperday() ));
                int score = (l.getScoreofbookforlibrary() * (l.getBooksperday())) / l.getSignupdays();
                //int score = l.getScoreofbookforlibrary();
                l.setPeso(score);
               //  System.out.println(String.format("La lib:%s ha score:%s", l.getId(), score));
            }

            //ordino lib per pesi
            librerie.sort(new Comparator<Library>() {
                @Override
                public int compare(Library library, Library t1) {
                    return library.getPeso().compareTo(t1.getPeso()) *-1;
                }
            });

            for(Library l : librerie) {
                List<Book> boktmp = new ArrayList<>();
                //  public Library(int id, int qtabooks, int signupdays, int booksperday, int scoreofbookforlibrary,  List<Book> bookList, Integer peso) {
                for(Book b : l.getBookList()) {
                    if(idOfBooksFinded.contains(b.getId())) {
                        boktmp.add(b);
                    }
                }

                l.setBookList(boktmp);
            }

            for(Library l : librerie) {
                System.out.println(String.format("La lib:%s ha score:%s", l.getId(), l.getPeso()));
            }


            List<Integer> giorniInAvanzo = new ArrayList<>();
            List<Library> librerieOut = new ArrayList<>();
            List<Book> libriOut = new ArrayList<>();

            String output = "";
            String firstLineOutput = "";


            int libindex = 0;
            int offsetgiornirimasti = 0;
            int giorniNextSignUp = 0;
            for(Library l : librerie) {

                int giorniRimasti = qtaDaysForScan;
                giorniRimasti -= giorniNextSignUp;

                if(giorniNextSignUp >= giorniRimasti) {
                    break;
                }

                librerieOut.add(l);
                giorniRimasti -= l.getSignupdays();
                giorniNextSignUp += l.getSignupdays();


                int maxGiorno = l.getBooksperday();

                int start = 0;
                int stop = maxGiorno;
                while (giorniRimasti > 0) {
                    for(int i = start; i < stop; i++) {
                        if( i >= l.getBookList().size()) {
                            break;
                        }
                        libriOut.add(l.getBookList().get(i));
                    }
                    start = stop;

                    if( start >= l.getBookList().size()) {
                        break;
                    }
                    stop = stop + maxGiorno;
                    giorniRimasti = giorniRimasti - 1 ;
                }

               output += l.getId() + " " + libriOut.size() + "\n";

                for(Book b : libriOut) {
                    output += b.getId() + " ";
                }

                output += "\n";

                libriOut.clear();

                giorniInAvanzo.add(giorniRimasti);
                offsetgiornirimasti = giorniRimasti;

                // quanti giorni rimasti?
                System.out.println("Giorni rimasti: " + giorniRimasti);
                libindex++;
            }

            giorniNextSignUp = 0;
            qtaDaysForScan = offsetgiornirimasti;
            for(int i = libindex; i < librerie.size(); i++) {
                int giorniRimasti = qtaDaysForScan;
                giorniRimasti -= giorniNextSignUp;

                if(giorniNextSignUp >= giorniRimasti) {
                    break;
                }

                librerieOut.add(librerie.get(i));
                giorniRimasti -= librerie.get(i).getSignupdays();
                giorniNextSignUp += librerie.get(i).getSignupdays();


                int maxGiorno = librerie.get(i).getBooksperday();

                int start = 0;
                int stop = maxGiorno;
                while (giorniRimasti > 0) {
                    for(int j = start; j < stop; j++) {
                        if( j >= librerie.get(i).getBookList().size()) {
                            break;
                        }
                        libriOut.add(librerie.get(i).getBookList().get(j));
                    }
                    start = stop;

                    if( start >= librerie.get(i).getBookList().size()) {
                        break;
                    }
                    stop = stop + maxGiorno;
                    giorniRimasti = giorniRimasti - 1 ;
                }

                output += librerie.get(i).getId() + " " + libriOut.size() + "\n";

                for(Book b : libriOut) {
                    output += b.getId() + " ";
                }

                output += "\n";

                libriOut.clear();

                giorniInAvanzo.add(giorniRimasti);

                // quanti giorni rimasti?
                System.out.println("Giorni rimasti: " + giorniRimasti);
                libindex++;
            }




            firstLineOutput += librerieOut.size() + "\n";
            firstLineOutput += output;

            //System.out.println(firstLineOutput);

            writeToFile("outputxx.txt", firstLineOutput);


        } catch (FileNotFoundException e) {
            System.out.println(String.format("An error occurred: %s", e.getMessage()));
            e.printStackTrace();
        }
    }

    public static void writeToFile(String fileName, String data) {
        try {

            PrintWriter writer = new PrintWriter("/Users/fabio/projects/internal/hashcode20/src/it/chiarani/output/"+fileName, "UTF-8");
            writer.println(data);
            writer.close();

        } catch (Exception e) {
            System.out.println(String.format("An error occurred: %s", e.getMessage()));
            e.printStackTrace();
        }
    }

}
