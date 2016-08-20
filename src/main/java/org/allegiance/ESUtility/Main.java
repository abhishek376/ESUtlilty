package org.allegiance.ESUtility;

import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/*
 * User:    Abhishek
 * Date:    1/7/14
 * Time:    2:07 PM
 * Project: ESUtility
 */
public class Main {
    public static void main(String args[]) throws IOException {

        if(args.length == 0)
        {
            System.out.println("");
            System.out.println("---------------------------------------------------------");
            System.out.println(" Available options -backup, -restore, -changeMapping");
            System.out.println("---------------------------------------------------------");
            System.out.println("");
            return;
        }

        //Change Mapping String oldIndex, String newIndex, String mappingType, String esHost, String field, String newFieldType
        Options changeMappingOptions = new Options();
        changeMappingOptions.addOption("changeMapping", false, "Change Mapping of an index");
        changeMappingOptions.addOption("esHost", true, "Elasticsearch host name");
        changeMappingOptions.addOption("clusterName", true, "Elasticsearch cluster name");
        changeMappingOptions.addOption("oldIndex", true, "Original Index");
        changeMappingOptions.addOption("newIndex", true, "Index to restore to");
        changeMappingOptions.addOption("mappingType", true, "Mapping Type");
        changeMappingOptions.addOption("field", true, "Field to change the mapping for");
        changeMappingOptions.addOption("newFieldType", true, "New field type");
        changeMappingOptions.addOption("removeField", true, "Remove a field");

        Options backupOptions = new Options();
        backupOptions.addOption("backup", false, "Backup index to a file");
        backupOptions.addOption("esHost", true, "Elasticsearch host name");
        backupOptions.addOption("clusterName", true, "Elasticsearch cluster name");
        backupOptions.addOption("index", true, "Index to back up");
        backupOptions.addOption("mappingType", true, "Mapping Type");

        Options restoreOptions = new Options();
        restoreOptions.addOption("restore", false, "Restore index from a file");
        restoreOptions.addOption("esHost", true, "Elasticsearch host name");
        restoreOptions.addOption("clusterName", true, "Elasticsearch cluster name");
        restoreOptions.addOption("index", true, "Index to back up");
        restoreOptions.addOption("mappingType", true, "Mapping Type");
        restoreOptions.addOption("file", true, "File to restore backup from");

        CommandLineParser parser = new BasicParser();
        CommandLine cmd = null;

         if(args[0].equals("-backup"))
            try {
            cmd = parser.parse(backupOptions, args);

            }catch (ParseException pe){ usage(backupOptions); return; }

        else  if(args[0].equals("-restore"))
            try {
                cmd = parser.parse(restoreOptions, args);

            }catch (ParseException pe){ usage(restoreOptions); return; }

        else  if(args[0].equals("-changeMapping"))
            try {
                cmd = parser.parse(changeMappingOptions, args);
            }catch (ParseException pe1){ usage(changeMappingOptions); return; }

        else
         {
             System.out.println("");
             System.out.println("---------------------------------------------------------");
             System.out.println(" Available options -backup, -restore, -changeMapping");
             System.out.println("---------------------------------------------------------");
             System.out.println("");
             return;
         }


        HelpFormatter formatter = new HelpFormatter();

        // ** now lets interrogate the options and execute the relevant parts
        if(cmd != null && cmd.hasOption("backup")){
            //String index, String mappingType, String esHost, String filePath
            if(cmd.getOptionValue("index") != null && cmd.getOptionValue("mappingType") != null &&  cmd.getOptionValue("esHost") != null &&  cmd.getOptionValue("clusterName") != null)
            {
                Settings settings = new Settings(cmd.getOptionValue("index"),cmd.getOptionValue("mappingType"), cmd.getOptionValue("esHost"), cmd.getOptionValue("clusterName"), "");
                settings.setOperationType(Settings.OpType.BACKUP);
                System.out.println("Trying to backup " + settings.index);
                Backup.hits.clear(); //Clear the buffer
                ScanOldIndex.Scan(settings);
                System.out.println("Finished reading the index");
                System.out.println("Trying to write to file");
                Backup.WriteFile(settings);
                String fileName = settings.index + "_" + new Date().getTime()+ ".txt";
                System.out.println("Backup finished. File : " + fileName );
            }
            else
            {
                formatter.printHelp("Backup", backupOptions);
            }
        }
        else  if(cmd != null && cmd.hasOption("restore")){
            //String index, String mappingType, String esHost, String filePath
            if(cmd.getOptionValue("index") != null && cmd.getOptionValue("mappingType") != null &&  cmd.getOptionValue("esHost") != null &&  cmd.getOptionValue("file") != null)
            {
                Settings settings = new Settings(cmd.getOptionValue("index"),cmd.getOptionValue("mappingType"), cmd.getOptionValue("esHost"), cmd.getOptionValue("clusterName"), cmd.getOptionValue("file"));
                settings.setOperationType(Settings.OpType.RESTORE);
                System.out.println("Trying to Read " + settings.file);
                Backup.hits.clear(); //Clear the buffer
                ArrayList<Map<String,Object>> hits =  Backup.ReadFile(settings);
                System.out.println("Finished reading the file. Found " + hits.size() + " documents");
                System.out.println("Trying to write to index : " + settings.index);
                Backup.RestoreIndex(settings, hits);
                System.out.println("Restore finished to index : " + settings.index );
            }
            else
            {
                formatter.printHelp("Restore", restoreOptions);
            }
        }
        else if(cmd != null && cmd.hasOption("changeMapping"))
        {
            if(cmd.getOptionValue("oldIndex") != null && cmd.getOptionValue("newIndex") != null
                    && cmd.getOptionValue("oldIndex") !=  cmd.getOptionValue("newIndex")
                    && cmd.getOptionValue("mappingType") != null &&  cmd.getOptionValue("esHost") != null
                    &&  cmd.getOptionValue("field") != null && cmd.getOptionValue("newFieldType") != null)
            {
                Settings settings = new Settings(cmd.getOptionValue("oldIndex"), cmd.getOptionValue("newIndex"), cmd.getOptionValue("mappingType"), cmd.getOptionValue("esHost"),
                        cmd.getOptionValue("clusterName"), cmd.getOptionValue("field"),  cmd.getOptionValue("newFieldType"),
                        cmd.getOptionValue("removeField") != null ? cmd.getOptionValue("removeField") : "");
                BufferedClient.setSettings(settings);
                settings.setOperationType(Settings.OpType.MODIFYINDEX);
                System.out.println("Scanning old index");
                ScanOldIndex.Scan(settings);
                BufferedClient.close();
                System.out.println(settings.documentsWithError + " total documents skipped due error");
                System.out.println("Finished restoring to the new index - " + settings.getNewIndex());
            }
            else
            {
                formatter.printHelp("Change mapping", changeMappingOptions);
            }
        }
    }

    private static void usage(Options options){
    // Use the inbuilt formatter class
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp( "ESUtility", options );
    }
 }
