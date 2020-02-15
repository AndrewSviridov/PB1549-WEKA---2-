@echo off
java -Xmx256m -classpath ../db_drivers/mysql-connector-java-5.1.6.jar;remoteEngine.jar;C:/Weka-3-7/weka.jar -Djava.security.policy=remote.policy weka.experiment.RemoteEngine -p 5050 