import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;
/**
 * Write a description of BabyBirths here.
 * 
 * @author tohver
 * @version 2.4 16.01.2022
 */
public class BabyBirths {
    public void printNames() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser(false);
        for (CSVRecord row : parser) {   
            String name = row.get(0);
            String gender = row.get(1);
            int numBorn = Integer.parseInt(row.get(2));
            if (numBorn > 100) {
            System.out.println(("Name: " + name + "\tGender: " + gender + "\tNum born: " + numBorn));
            }
        }  
    }

    public int getRank(int year, String name, String gender) {
        int rank = 0;
        int isThere = -1;
        FileResource fr = new FileResource("data/yob" + year + ".csv");
        CSVParser parser = fr.getCSVParser(false);
        for (CSVRecord row : parser) {
            if (row.get(1).equals(gender)) {
                rank +=1;
                if (row.get(0).equals(name)) {
                    isThere = 1;
                    // System.out.println("\n" + year + " " + name + " had the rank " + rank);
                    break;
                }
            }
        }
        if (isThere == -1) {
            return -1;
        }
        else {
        return rank;
        }
    }

    public String getName (int year, int rank, String gender) {
        String name = "";
        String isName = "NO NAME";
        int rankCounter = 0;
        FileResource fr = new FileResource("data/yob" + year + ".csv");
        CSVParser parser = fr.getCSVParser(false);
        for (CSVRecord row : parser) {
            if (row.get(1).equals(gender)) {
                rankCounter += 1;
                if (rankCounter == rank) {
                    name = row.get(0);
                    isName = "Is Name";
                }
            }
        }
        if (isName.equals("NO NAME")) {
            return isName;
        }
        else {
        return name;
        }
    }

    public void whatIsNameInYear (String name, int year, int newYear, String gender) {
        FileResource fr = new FileResource("data/yob" + year + ".csv");
        FileResource frNew = new FileResource("data/yob" + newYear + ".csv");
        int rank  = getRank(year, name, gender);
        String newName = getName(newYear, rank, gender);
        System.out.println("\n" + name + " born in " + year + " would be " + newName + " if she was born in " + newYear);
    }

    public void totalBirths (FileResource fr) {
        int totalBirths = 0;
        int totalBoys = 0;
        int totalGirls = 0;
        int counterGirls = 0;
        int counterBoys = 0;
        for (CSVRecord row : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(row.get(2));
            totalBirths += numBorn;
            if (row.get(1).equals("F")) {
                totalGirls += numBorn; 
                counterGirls += 1;
            }
            else {
                totalBoys += numBorn;
                counterBoys += 1;
            }
        }
        System.out.print("\nTotal births: " + totalBirths + "\nTotal Girls: " + totalGirls + "\nTotal female names: " + counterGirls + "\nTotal Boys: " + totalBoys + "\nTotal male names: " + counterBoys);
    }

    public int getYear(String pathName) {
        int yearStart = 0;
        int yearEnd = 0;
        int year = 0;
        yearStart = pathName.indexOf("yob") + 3;
        yearEnd = pathName.indexOf(".csv");
        year = Integer.parseInt(pathName.substring(yearStart, yearEnd));
        return year;
    }

    public int yearOfHighestRank (String name, String gender) {
        DirectoryResource dr = new DirectoryResource();
        int rank = 0;
        int tempRank = 100000000;
        int tempYear = 0;
        for (File f : dr.selectedFiles()) {
            String pathName = f.getAbsolutePath();
            int year = getYear(pathName);
            rank = getRank(year, name, gender);
            if (rank != -1){
                if (rank < tempRank) {
                    tempRank = rank;
                    tempYear = year;
                }
            }
        }
        if (tempRank == 100000000) {
            return -1;
        }
        else {
            return tempYear;
        }
    }

    public double getAverageRank (String name, String gender) {
    	int rank = 0;
    	int totalRank = 0;
    	int counter = 0;
    	DirectoryResource dr = new DirectoryResource();
    	for (File f : dr.selectedFiles()) {
    		String pathName = f.getAbsolutePath();
    		int year = getYear(pathName);
    		rank = getRank(year, name, gender);
    		if (rank == -1) {
    			rank = 0;
    		}
    		totalRank += rank;
    		counter += 1;
    	}
    	return (double) totalRank / counter;
    }

    public int getTotalBirthsRankedHigher (int year, String name, String gender) {
    	int rank = getRank(year, name, gender);
    	int born = 0;
    	int counter = 0;
    	FileResource fr = new FileResource("data/yob" + year + ".csv");
    	CSVParser parser = fr.getCSVParser(false);
    	for (CSVRecord row : parser) {
    		if (row.get(1).equals(gender)){
	    		counter +=1;
	    		if (counter == rank) {
	    			break;
		    	}
		    	born += Integer.parseInt(row.get(2));
	    		System.out.println(row.get(0) + ": " + born);		    	
		    }
    	}
    	return born;
    }




    public void test() {

        // FileResource fr = new FileResource("data/yob1900.csv");
        // totalBirths(fr);
        // fr = new FileResource("data/yob1905.csv");
        // totalBirths(fr);
        // int rank =  getRank(1960,"Emily", "F");
        // System.out.println("Rank of Emily 1960: " + rank);
        // System.out.println("At this rank 2012 was: " + getName (2012, rank, "M"));
        // int rank2 =  getRank(1971,"Frank", "M");
        // System.out.println("Rank Frank: " + rank2);        
        // System.out.println("At this rank 2012 was: " + getName (2012, rank2, "M"));
        // String name1 =  getName(1980,350, "F");
        // System.out.println("1980: " + name1);
        // String name2 =  getName(1982,450, "M");
        // System.out.println("1982: " + name2);
        // System.out.println("At this rank 2012 was: " + getName (2012, rank3, "F"));
        // whatIsNameInYear("Susan", 1972, 2014, "F");
        // whatIsNameInYear("Owen", 1974, 2014, "M");
        // whatIsNameInYear("Mason", 2000, 2011, "F");
        // whatIsNameInYear("Alice", 1915, 1999, "F");
        // int highestYear = yearOfHighestRank("Genevieve", "F");
        // System.out.println("Genevieve: " + highestYear);
        int highestYear2 = yearOfHighestRank("Mich", "M");
        System.out.println("Mich: " + highestYear2);
    	// double avgRank1 = getAverageRank("Susan", "F");
    	// System.out.println("The average rank for Susan was: " + avgRank1);
    	// double avgRank2 = getAverageRank("Robert", "M");
    	// System.out.println("The average rank for Robert was: " + avgRank2);
    	//  int bornRankedHigher1 = getTotalBirthsRankedHigher(1990, "Emily", "F");
    	// System.out.println("Born rank higher then Emily: "  + bornRankedHigher1);
	// int bornRankedHigher2 = getTotalBirthsRankedHigher(1990, "Drew", "M");
    	// System.out.println("Born rank higher then Ethan: "  + bornRankedHigher2);
    }
}

// ("data\example-small.csv")
// ("data\yob2014-small.csv")