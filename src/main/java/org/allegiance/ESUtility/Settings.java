package org.allegiance.ESUtility;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

/**
 * User:    Abhishek
 * Date:    1/8/14
 * Time:    3:57 PM
 * Project: ESUtility
 */

public class Settings {
    public String oldIndex = "";
    public String newIndex = "";
    public String index = "";
    public String file = "";
    public String mappingType = "";
    public String field = "";
    public String newFieldType = "";
    public Client client;
    public int bufferThreshold;
    public int scrollSize;
    public long documentsCount;
    public long documentsWithError;
    public String esHost;
    public OpType operationType;
    public String removeField;

    public static enum OpType {
        MODIFYINDEX, BACKUP, RESTORE
    }


    public Settings(String args[])
    {
        this.oldIndex = args[0];
        this.newIndex = args[1];
        this.mappingType = args[2];
        this.client = new TransportClient().addTransportAddress(new InetSocketTransportAddress(args[3], 9300));
        this.field = args[4];
        this.newFieldType = args[5];
        this.bufferThreshold = 100;
        this.scrollSize =  100;
    }

    public Settings(String index, String mappingType, String esHost, String clusterName, String filePath)
    {
        this.index = index;
        this.mappingType = mappingType;
        this.esHost = esHost;
        this.file = filePath;
        this.scrollSize =  100;
        this.bufferThreshold = 100;
        this.client = InitTransport(clusterName,esHost);
    }

    public void setOperationType(OpType op)
    {
        this.operationType = op;
    }


    public Settings(String oldIndex, String newIndex, String mappingType, String esHost, String clusterName, String field, String newFieldType, String removeField)
    {
        this.oldIndex = oldIndex;
        this.newIndex = newIndex;
        this.mappingType = mappingType;
        this.field = field;
        this.newFieldType = newFieldType;
        this.client = InitTransport(clusterName,esHost);
        this.bufferThreshold = 100;
        this.scrollSize =  100;
        this.removeField = removeField;
    }

    public Settings(String oldIndex, String newIndex, String mappingType, String esHost, String clusterName, int esPort, String field, String newFieldType, int bufferThreshold, int scrollSize)
    {
        this.oldIndex = oldIndex;
        this.newIndex = newIndex;
        this.mappingType = mappingType;
        this.field = field;
        this.newFieldType = newFieldType;
        this.client = InitTransport(clusterName,esHost);
        this.bufferThreshold = bufferThreshold;
        this.scrollSize =  scrollSize;
    }

    private Client InitTransport(String clusterName, String esHost)
    {
       org.elasticsearch.common.settings.Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", clusterName).build();
       return new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress(esHost, 9300));
    }
}
