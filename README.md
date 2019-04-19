# public
my public projects repo
## smallfiles
小文件存储服务。利用hbase特性，存储/读取大量小文件。spring boot框架，启动类ServiceStarter.java。mvn package打包的jar是可执行文件。属性文件在外部目录（/opt/config/smallfiles/config.properties），遵循分离式配置原则。  
上传：/basic/{table}/upload，建表columnFamily有cf，返回[data.path]
下载：/basic/{table}/download/{key}，key为上传时返回的path
