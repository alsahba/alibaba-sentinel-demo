# Simple project for learning Alibaba-Sentinel

For sentinel dashboard:
- Download the sentinel-dashboard jar file from https://github.com/alibaba/Sentinel/releases (for english, 1.3.0 version is the latest, newer versions are chinese)
- In this demo project, sentinel-dashboard is bound to localhost:8081. Because of that, downloaded jar file must be run on port 8081 or change the port number in application.properties file (check below command for version 1.3.0)

```
java -Dserver.port=8081 -Dcsp.sentinel.dashboard.server=localhost:8081 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard-1.3.0-en.jar

```

<br />
<br />
Try this demo with the get request on directly localhost:8080. Help of the concurrency most of the numbers throws a flow exception and these exceptions caught in the block handler method in the testservice. Even numbers also throw an unsupported exception. After some failed request (count or ratio), degrade rule will become active and degrade exception caught in the block handler. These flow and degrade exceptions are unique for the sentinel and if there is a block handler defined in the same class of the method, exceptions will be caught. Other exceptions (checked, unchecked) be caught in the fallback method if there is one defined. If there is not a block handler, unique exceptions (flow or degrade) be caught in the fallback method. 

<br />
<br />
One flow rule and one degrade rule are applied default in the demo project. But these rules can be changed or other rules can be applied with the sentinel-dashboard dynamically. 
