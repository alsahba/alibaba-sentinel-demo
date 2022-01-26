# Simple project for learning Alibaba-Sentinel

For sentinel dashboard:
- Download the sentinel-dashboard jar file from https://github.com/alibaba/Sentinel/releases (for english, 1.3.0 version is the latest, newer versions are chinese)
- In this demo project, sentinel-dashboard is bound to localhost:8081. Because of that, downloaded jar file must be run on port 8081 or change the port number in application.properties file (check below command for version 1.3.0)

```
java -Dserver.port=8081 -Dcsp.sentinel.dashboard.server=localhost:8081 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard-1.3.0-en.jar

```

<br />
<br />
One flow rule and one degrade rule are applied default in the demo project. But these rules can be changed or other rules can be applied with the sentinel-dashboard dynamically. 
