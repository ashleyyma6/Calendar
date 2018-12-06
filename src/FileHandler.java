import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class FileHandler {

    /**
     * Save all exciting events to events.txt
     */

    public void saveEventsToFile(Events events)
    {
        PrintWriter out = null;
        try
        {
            out = new PrintWriter("events.txt");
        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        ArrayList<String> strings = events.saveEventsToFile();
        for(String s : strings)
        {
            out.println(s);
        }
        out.close();
    }

    /**
     * Read exciting events in the events.txt file
     */
    public Events readExcitingEvents()
    {
        Events events = new Events();
        File file = new File("events.txt");
        if(!file.exists())
        {
            try
            {
                file.createNewFile();
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        Scanner inputFile = null;
        try
        {
            inputFile = new Scanner(file);
        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        ArrayList<String> readFile = new ArrayList<String>();

        while(inputFile.hasNextLine())
        {
            readFile.add(inputFile.nextLine());
        }

        for(int i = 0; i < readFile.size(); i ++)
        {
            String date = new String();
            String time = new String();
            String title = new String();
            List<String> list = new ArrayList<String>();

            list = Arrays.asList(readFile.get(i).split("\\s+"));

            List<String> list2 = new ArrayList<String>();
            date = list.get(0).substring(0, list.get(0).length()-1);

            list2 = Arrays.asList(date.split("/"));

            int month = Integer.parseInt(list2.get(0)) -1;
            int day = Integer.parseInt(list2.get(1));
            int year = Integer.parseInt(list2.get(2));
            int startingTime = (list.get(1).substring(list.get(1).length()-2).equals("am"))?  Integer.parseInt(list.get(1).substring(0,list.get(1).length()-2)):Integer.parseInt(list.get(1).substring(0,list.get(1).length()-2))+12;
            int endingTime = (list.get(3).substring(list.get(3).length()-2).equals("am"))?  Integer.parseInt(list.get(3).substring(0,list.get(3).length()-2)):Integer.parseInt(list.get(3).substring(0,list.get(3).length()-2))+12;

            for(int index = 4; index < list.size(); index++)
            {
                title += list.get(index) + " ";
            }

            if(events.addEvent(new Date(month, day, year), new SimpleEvent(title, startingTime, endingTime) ))
            {
                events.addEvent(new Date(month, day, year), new SimpleEvent(title, startingTime, endingTime) );
            }
        }
        inputFile.close();
        return events;
    }
}
