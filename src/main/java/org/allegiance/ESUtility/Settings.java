package org.allegiance.ESUtility;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

/**
 * User:    Abhishek
 * Date:    1/8/14
 * Time:    3:57 PM
 * Project: ESUtility
 */

public class Settings {
    public static enum OpType {
        MODIFYINDEX, BACKUP, RESTORE
    }

	private String oldIndex = "";

	private String newIndex = "";

	public String index = "";

	public String file = "";

	private String mappingType = "";

	public String field = "";

	private String newFieldType = "";

	public Client client;

	public int bufferThreshold;

	public int scrollSize;

	public long documentsCount;

	public long documentsWithError;

	public String esHost;

	public OpType operationType;

	private String removeField;

	public Settings(String args[])
    {
        this.oldIndex = args[0];
        this.newIndex = args[1];
        this.mappingType = args[2];
      //  this.client = new TransportClient().addTransportAddress(new InetSocketTransportAddress(args[3], 9300));
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

	public int getBufferThreshold() {
		return bufferThreshold;
	}

	public Client getClient() {
		return client;
	}

	public long getDocumentsCount() {
		return documentsCount;
	}

	public long getDocumentsWithError() {
		return documentsWithError;
	}

	public String getEsHost() {
		return esHost;
	}

	public String getField() {
		return field;
	}

	public String getFile() {
		return file;
	}

	public String getIndex() {
		return index;
	}

	public String getMappingType() {
		return mappingType;
	}

	public String getNewFieldType() {
		return newFieldType;
	}
    public String getNewIndex() {
		return newIndex;
	}
    public String getOldIndex() {
		return oldIndex;
	}
    public OpType getOperationType() {
		return operationType;
	}
    public String getRemoveField() {
		return removeField;
	}
    public int getScrollSize() {
		return scrollSize;
	}
    private Client InitTransport(String clusterName, String esHost)
    {
    	Client client = null;
    	try {
    		org.elasticsearch.common.settings.Settings settings = org.elasticsearch.common.settings.Settings.settingsBuilder()
    		        .put("cluster.name", clusterName).build();
			 client = TransportClient.builder().settings(settings).build()
			        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(esHost), 9300));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return client;
    }
    public void setBufferThreshold(int bufferThreshold) {
		this.bufferThreshold = bufferThreshold;
	}
    public void setClient(Client client) {
		this.client = client;
	}
    public void setDocumentsCount(long documentsCount) {
		this.documentsCount = documentsCount;
	}
    public void setDocumentsWithError(long documentsWithError) {
		this.documentsWithError = documentsWithError;
	}
    public void setEsHost(String esHost) {
		this.esHost = esHost;
	}
    public void setField(String field) {
		this.field = field;
	}
    public void setFile(String file) {
		this.file = file;
	}
    public void setIndex(String index) {
		this.index = index;
	}

    public void setMappingType(String mappingType) {
		this.mappingType = mappingType;
	}


    public void setNewFieldType(String newFieldType) {
		this.newFieldType = newFieldType;
	}

    public void setNewIndex(String newIndex) {
		this.newIndex = newIndex;
	}

    public void setOldIndex(String oldIndex) {
		this.oldIndex = oldIndex;
	}


    public void setOperationType(OpType op)
    {
        this.operationType = op;
    }

    public void setRemoveField(String removeField) {
		this.removeField = removeField;
	}

    public void setScrollSize(int scrollSize) {
		this.scrollSize = scrollSize;
	}
}
