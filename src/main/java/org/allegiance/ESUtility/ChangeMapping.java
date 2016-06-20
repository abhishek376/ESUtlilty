package org.allegiance.ESUtility;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.search.SearchHit;

/**
 * User:    Abhishek
 * Date:    1/7/14
 * Time:    3:04 PM
 * Project: ESUtility
 */
public class ChangeMapping {

    public static void ChangeMapping(final SearchHit hit, final Settings settings) throws IOException {

        if(StringUtils.isNotBlank(settings.getRemoveField()) && hit.getSource().keySet().contains(settings.getRemoveField()))
            hit.getSource().remove(settings.getRemoveField());

           if (BufferedClient.settings.getNewFieldType().equals("string")) {
               hit.getSource().put(settings.field, null);

           } else if (BufferedClient.settings.getNewFieldType().equals("integer")) {
               hit.getSource().put(settings.field, null);

           } else if (BufferedClient.settings.getNewFieldType().equals("long")) {
               hit.getSource().put(settings.field, null);

           } else if (BufferedClient.settings.getNewFieldType().equals("float")) {
               hit.getSource().put(settings.field, null);

           } else if (BufferedClient.settings.getNewFieldType().equals("double")) {
               hit.getSource().put(settings.field,null);

           } else if (BufferedClient.settings.getNewFieldType().equals("boolean")) {
               hit.getSource().put(settings.field, null);

           } else {
               throw new IOException("Unknown Type");
           }
       BufferedClient.executeESBulk(hit.getSource());
     }
 }


