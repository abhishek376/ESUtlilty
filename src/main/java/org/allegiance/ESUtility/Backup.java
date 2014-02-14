package org.allegiance.ESUtility;

import org.elasticsearch.search.SearchHit;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * User:    Abhishek
 * Date:    1/7/14
 * Time:    2:07 PM
 * Project: ESUtility
 */
public class Backup {

    static ArrayList<SearchHit> hits = new ArrayList<SearchHit>();

    public static void BackupIndex(SearchHit hit)
    {
        hits.add(hit);
    }

    public static void RestoreIndex(Settings settings, ArrayList<Map<String,Object>> hits) throws IOException {
      for(Map<String,Object> hit : hits)
      {
          BufferedClient.setSettings(settings);
          BufferedClient.executeESBulk(hit);
      }
    }

    public static void WriteFile(Settings settings)
    {
        // The name of the file to create.
        String fileName = settings.index + "_" + new Date().getTime()+ ".txt";
        try {

            FileOutputStream fileOut =
                    new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            for (SearchHit hit : hits)
            {
                out.writeObject(hit.getSource());
            }
            out.close();
            fileOut.close();

            System.out.println("Wrote " + hits.size() + " documents to file.");
            hits.clear(); //Clear the buffer once done.
        }
        catch(IOException ex) {
            System.out.println("Error writing file '" + fileName + "'");
        }
    }

    public static ArrayList<Map<String,Object>> ReadFile(Settings settings)
    {
        Map<String,Object> map;
        ArrayList<Map<String,Object>> hits = new ArrayList<Map<String,Object>>();
         try
         {
            try
            {
                FileInputStream fileIn = new FileInputStream(settings.file);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                while(true)
                {
                    try
                    {
                         map = (Map<String,Object>) in.readObject();
                         hits.add(map);
                    }
                    catch(Exception ex)
                    {
                        break;
                    }
                }
                in.close();
                fileIn.close();
                return hits;
            }catch(IOException i)
            {
                i.printStackTrace();
                return null;
            }
        }
        catch(Exception ex) {
            System.out.println("Error writing file '" + settings.file + "'");
        }
        return null;
    }
}


