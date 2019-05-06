# public 
my public projects repo 
## smallfiles 
File storage service. Use the hbase feature to store/read large numbers of small files 
### ENV 
1.hbase [Column family 'cf' must exists] 
2.jdk >= 1.8 
3.maven3 
### config file 
/opt/config/smallfiles/config.properties [Follow the principle of separate configuration]
### Run with jar 
mvn package 
java -jar smallfiles-x.x.x.jar 
### Run in IDE 
Run As - > ServiceStarter.java 
upload api: /basic/{table}/upload, note response json [data.path]  
download: /basic/{table}/download/{key}，key is the [data.path]
