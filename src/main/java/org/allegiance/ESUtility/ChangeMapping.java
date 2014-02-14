package org.allegiance.ESUtility;

import org.elasticsearch.search.SearchHit;
import org.omg.stub.java.rmi._Remote_Stub;

import java.io.IOException;

/**
 * User:    Abhishek
 * Date:    1/7/14
 * Time:    3:04 PM
 * Project: ESUtility
 */
public class ChangeMapping {

    public static void ChangeMapping(SearchHit hit, Settings settings) throws IOException {

        if(settings.removeField != "" && hit.getSource().keySet().contains(settings.removeField))
            hit.getSource().remove(settings.removeField);

           if (BufferedClient.settings.newFieldType.equals("string")) {
               hit.getSource().put(settings.field, null);

           } else if (BufferedClient.settings.newFieldType.equals("integer")) {
               hit.getSource().put(settings.field, null);

           } else if (BufferedClient.settings.newFieldType.equals("long")) {
               hit.getSource().put(settings.field, null);

           } else if (BufferedClient.settings.newFieldType.equals("float")) {
               hit.getSource().put(settings.field, null);

           } else if (BufferedClient.settings.newFieldType.equals("double")) {
               hit.getSource().put(settings.field,null);

           } else if (BufferedClient.settings.newFieldType.equals("boolean")) {
               hit.getSource().put(settings.field, null);

           } else {
               throw new IOException("Unknown Type");
           }
       //hit.getSource().put("shared", new ArrayList<Integer>().toString());
       BufferedClient.executeESBulk(hit.getSource());
     }
 }


