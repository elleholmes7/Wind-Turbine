import java.util.Scanner; //for reading keyboard input and Files
import java.io.File;
import java.io.PrintWriter; //for printing to a file
import java.io.IOException; //Exception that can be thrown by File and Scanner
import java.util.ArrayList;

public class WindTurbine{
public static ArrayList<String[]> readDataFromFile(String filename) throws IOException
{
    ArrayList<String[]> csvData = new ArrayList<String[]>();

    File file = new File(filename);
    Scanner scan = new Scanner(file);

    while (scan.hasNextLine())
    {
        String line = scan.nextLine();
        String[] row = line.split(",");
        csvData.add(row);
    }

    scan.close();
    return csvData;
}
public static void writeDataToFile(ArrayList<String[]> csvData, String filename)
        throws IOException
{
    File file = new File(filename);
    PrintWriter out = new PrintWriter(file);

    for (String[] row : csvData)
    {
        for (int i = 0; i < row.length; i++)
        {
            out.print(row[i]);

            if (i < row.length - 1)
            {
                out.print(",");
            }
        }
        out.println();   // move to next line
    }

    out.close();
}
public static int countWindTurbines(ArrayList<String[]> windData) {
int sum = 0;
//go through all but the first row (which is the headings)
for (String[] row : windData.subList(1, windData.size())) {
   String numTurbines = row[9];
   numTurbines = numTurbines.replace("\"", "");
   int value = Integer.parseInt(numTurbines);
   sum += value;
}
return sum;
}


public static ArrayList<String> stateList(ArrayList<String[]> windData){
    ArrayList<String> stateList = new ArrayList<>();
    for(String[] row : windData.subList(1, windData.size())) {
        String state = row[0];
        state = state.replace("\"", "");
        if(!stateList.contains(state)){
            stateList.add(state);
        }
    }
    return stateList;
}
public static int countWindTurbinesInState(ArrayList<String[]> windData, String stateInput){
    int sum = 0;
    for (String[] row : windData.subList(1, windData.size())) {
    String state = row[0];
    state = state.replace("\"", "");
        if (stateInput.equals(state)) {
            String numTurbines = row[9];
            numTurbines = numTurbines.replace("\"", "");
            int value = Integer.parseInt(numTurbines);
            sum += value;
        }
    }
    return sum;
}
public static void main(String[] args) {
        ArrayList<String[]> windData = new ArrayList<String[]>();
        Scanner keyboard = new Scanner(System.in);
        String input = "";
 while (!input.equals("Q")) {
    System.out.println("1. Read Data From File");
    System.out.println("2. Add a wind turbine location");
    System.out.println("3. Total Number of Turbines");
    System.out.println("4. State with the most turbines + count turbines in that state");
    System.out.println("5. Save Data");
    System.out.println("Q. Quit");

    input = keyboard.nextLine();
    input = input.toUpperCase();

    if (input.equals("1")) {
        System.out.print("filename: ");
        String filename = keyboard.nextLine();
        try {
            windData = readDataFromFile(filename);
        } catch (IOException e) {
            System.out.println("An error occurred: " + e);
        }
        }
        else if (input.equals("2")) {
            String[] row = new String[12];
            System.out.print("State: ");
            row[0] = "\"" + keyboard.nextLine() + "\"";
            System.out.print("County: ");
            row[1] = "\"" + keyboard.nextLine() + "\"";
            System.out.print("Year: ");
            row[2] = "\"" + keyboard.nextLine() + "\"";
            System.out.print("Turbine Capacity: ");
            row[3] = "\"" + keyboard.nextLine() + "\"";
            System.out.print("Hub Height: ");
            row[4] = "\"" + keyboard.nextLine() + "\"";
            System.out.print("Rotor Diameter: ");
            row[5] = "\"" + keyboard.nextLine() + "\"";
            System.out.print("Swept_Area: ");
            row[6] = "\"" + keyboard.nextLine() + "\"";
            System.out.print("Total Height: ");
            row[7] = "\"" + keyboard.nextLine() + "\"";
            System.out.print("Project Capacity: ");
            row[8] = "\"" + keyboard.nextLine() + "\"";
            System.out.print("Number Turbines: ");
            row[9] = "\"" + keyboard.nextLine() + "\"";
            System.out.print("Latitude: ");
            row[10] = "\"" + keyboard.nextLine() + "\"";
            System.out.print("Longitude: ");
            row[11] = "\"" + keyboard.nextLine() + "\"";
            windData.add(row);
        }
        else if (input.equals("3")) {
            System.out.println("This data set contains " + countWindTurbines(windData) + " wind turbines.");
        }
        else if (input.equals("4")){
            ArrayList<String> stateList = stateList(windData);
            int max = 0;
            String maxState = "IA";
            for(String state:stateList){
                int total = countWindTurbinesInState(windData,state);
                if(total>max){
                    max = total;
                    maxState = state;
                }
            }
            System.out.println(maxState + " has the most wind turbines: " + max + " wind turbines.");
        }
        else if (input.equals("5")) {
            System.out.print("filename: ");
            String filename = keyboard.nextLine();
            try {
                writeDataToFile(windData,filename);
                System.out.println("Saved.");
            } catch (IOException e) {
                System.out.println("An error occurred: " + e);
            }
        }
    }
}
}