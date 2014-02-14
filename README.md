h1. ESUtlilty
===========
We have a reporting platform based on Elasticsearch. And I often get requests to add fields or modify a field in a existing document. 
But once the data is indexed in to ES it cannot be modified. So I developed a tool which can modify an existing Elasticsearch index. 

The utility uses the scan api to get 100 documents at once change the mapping in the source and push it back to elasticsearch using the bulk API.

h2. Building the tool
===================
mvn clean package

h2. Usage
=======

Example :java -jar ESUtility.jar -changeMapping -clusterName ESVM -esHost es1 -field newfield -mappingType type -newFieldType string -newIndex newindex -oldIndex oldindex

h3. -Change Mapping
 Help : java -jar ESUtility.jar -changeMapping will give you all the available properties
 It accepts the following arguments
 -changeMapping        Change Mapping of an index
 -clusterName <arg>    Elasticsearch cluster name
 -esHost <arg>         Elasticsearch host name
 -field <arg>          Field to change the mapping for
 -mappingType <arg>    Mapping Type
 -newFieldType <arg>   New field type
 -newIndex <arg>       Index to restore to
 -oldIndex <arg>       Original Index
 -removeField <arg>    Remove a field (not mandatory)
 
 h3. -backup
  Help : java -jar ESUtility.jar -backup will give you all the available properties
  usage: Backup
 -backup              Backup index to a file
 -clusterName <arg>   Elasticsearch cluster name
 -esHost <arg>        Elasticsearch host name
 -index <arg>         Index to back up
 -mappingType <arg>   Mapping Type
 
 h3. -restore
   Help : java -jar ESUtility.jar -restore will give you all the available properties
  usage: Restore
 -clusterName <arg>   Elasticsearch cluster name
 -esHost <arg>        Elasticsearch host name
 -file <arg>          File to restore backup from
 -index <arg>         Index to back up
 -mappingType <arg>   Mapping Type
 -restore             Restore index from a file