# smallfiles 
File storage service. Use the hbase feature to store/read large numbers of small files 
### ENV 
1.hbase [Specified table's Column family 'cf' must exists]  
2.jdk >= 1.8  
3.maven3  
### config file 
/opt/config/smallfiles/config.properties [Follow the principle of separate configuration]
### Run with jar 
mvn package 
java -jar smallfiles-x.x.x.jar 
### Run in IDE 
Run As - > ServiceStarter.java  
### API
upload: /basic/{table}/upload, note response json [data.path]   
download: /basic/{table}/download/{key}，key is the [data.path]
