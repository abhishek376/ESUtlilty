# ESUtlilty
We have a reporting platform based on Elasticsearch. And I often get requests to add fields or modify a field of an existing index. 
But once the data is indexed in to ES it cannot be modified. So I developed a tool which can modify an existing Elasticsearch index.

More info http://www.elasticsearch.org/blog/changing-mapping-with-zero-downtime/

## How it works
1) The utility uses the Search (Scan) API to get 100 documents at once. Make sure the source is enabled.

2) Changes the mapping. Adds/Removes/Delete the fields.

3) Push the documents back to Elasticsearch using bulk API.

If you have dynamic mapping set to strict. Make sure you set the mapping on the new index before you run the tool. The tool doesn't modify the source index.

If you haven't heard about Elasticsearch you should check it out (http://www.elasticsearch.org/)

## Credits
Allegiance Inc (Company I work) for agreeing to open source this tool.

## Building the tool
Built using Maven. You can specify the Elasticsearch version in the pom file. 

```
mvn clean package
```

## Usage
```
java -jar ESUtility.jar -changeMapping -clusterName ESVM -esHost es1 -field newfield -mappingType type -newFieldType string -newIndex newindex -oldIndex oldindex
```
## Options
### Changing an existing Elasticsearch index.

1. Changes Mapping of an existing Elasticsearch index. 
2. Add new fields to existing Elasticsearch index. 
3. Removes a field from exisiting Elasticsearch index.

Help : java -jar ESUtility.jar -changeMapping will give you all the available properties
```
 -changeMapping        Change Mapping of an index
 -clusterName <arg>    Elasticsearch cluster name
 -esHost <arg>         Elasticsearch host name
 -field <arg>          Field to change the mapping for
 -mappingType <arg>    Mapping Type
 -newFieldType <arg>   New field type
 -newIndex <arg>       Index to restore to
 -oldIndex <arg>       Original Index
 -removeField <arg>    Remove a field (Optional)
```
 
### Backup a index in to a file
The utility reads the entire index in to memory and writes to a file. Beware of the memory. In the next version I will try to write this to multiple files.
Help : java -jar ESUtility.jar -backup will give you all the available properties
```
 -backup              Backup index to a file
 -clusterName <arg>   Elasticsearch cluster name
 -esHost <arg>        Elasticsearch host name
 -index <arg>         Index to back up
 -mappingType <arg>   Mapping Type
```
 
### Restore to the index from file.
Reads from a file and restores to Elasticsearch index.
Help : java -jar ESUtility.jar -restore will give you all the available properties
```
 -clusterName <arg>   Elasticsearch cluster name
 -esHost <arg>        Elasticsearch host name
 -file <arg>          File to restore backup from
 -index <arg>         Index to back up
 -mappingType <arg>   Mapping Type
 -restore             Restore index from a file
```
  
You can write to me @abhishek376 or abhishek376@gmail.com if you have any questions.