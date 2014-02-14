ESUtlilty
===========
We have a reporting platform based on Elasticsearch. And I often get requests to add fields or modify a field in a existing document. 
But once the data is indexed in to ES it cannot be modified. So I developed a tool which can modify an existing Elasticsearch index. 

Building the tool
===================
mvn clean package

Usage
=======

java -jar ESUtility.jar 