java -Xverify:none -Djava.locale.providers=JRE -Dfile.encoding=UTF-8 -classpath /docker-java-home/jre/lib/charsets.jar:/docker-java-home/jre/lib/deploy.jar:/docker-java-home/jre/lib/ext/cldrdata.jar:/docker-java-home/jre/lib/ext/dnsns.jar:/docker-java-home/jre/lib/ext/jaccess.jar:/docker-java-home/jre/lib/ext/jfxrt.jar:/docker-java-home/jre/lib/ext/localedata.jar:/docker-java-home/jre/lib/ext/nashorn.jar:/docker-java-home/jre/lib/ext/sunec.jar:/docker-java-home/jre/lib/ext/sunjce_provider.jar:/docker-java-home/jre/lib/ext/sunpkcs11.jar:/docker-java-home/jre/lib/ext/zipfs.jar:/docker-java-home/jre/lib/javaws.jar:/docker-java-home/jre/lib/jce.jar:/docker-java-home/jre/lib/jfr.jar:/docker-java-home/jre/lib/jfxswt.jar:/docker-java-home/jre/lib/jsse.jar:/docker-java-home/jre/lib/management-agent.jar:/docker-java-home/jre/lib/plugin.jar:/docker-java-home/jre/lib/resources.jar:/docker-java-home/jre/lib/rt.jar:/docker-java-home/lib/ant-javafx.jar:/docker-java-home/lib/dt.jar:/docker-java-home/lib/javafx-mx.jar:/docker-java-home/lib/jconsole.jar:/docker-java-home/lib/packager.jar:/docker-java-home/lib/sa-jdi.jar:/docker-java-home/lib/tools.jar:/pilot_rest/target/classes:/home/vscode/.m2/repository/org/springframework/boot/spring-boot-starter-web/2.6.1/spring-boot-starter-web-2.6.1.jar:/home/vscode/.m2/repository/org/springframework/boot/spring-boot-starter/2.6.1/spring-boot-starter-2.6.1.jar:/home/vscode/.m2/repository/org/springframework/boot/spring-boot/2.6.1/spring-boot-2.6.1.jar:/home/vscode/.m2/repository/org/springframework/boot/spring-boot-autoconfigure/2.6.1/spring-boot-autoconfigure-2.6.1.jar:/home/vscode/.m2/repository/org/springframework/boot/spring-boot-starter-logging/2.6.1/spring-boot-starter-logging-2.6.1.jar:/home/vscode/.m2/repository/ch/qos/logback/logback-classic/1.2.7/logback-classic-1.2.7.jar:/home/vscode/.m2/repository/ch/qos/logback/logback-core/1.2.7/logback-core-1.2.7.jar:/home/vscode/.m2/repository/org/apache/logging/log4j/log4j-to-slf4j/2.14.1/log4j-to-slf4j-2.14.1.jar:/home/vscode/.m2/repository/org/apache/logging/log4j/log4j-api/2.14.1/log4j-api-2.14.1.jar:/home/vscode/.m2/repository/org/slf4j/jul-to-slf4j/1.7.32/jul-to-slf4j-1.7.32.jar:/home/vscode/.m2/repository/jakarta/annotation/jakarta.annotation-api/1.3.5/jakarta.annotation-api-1.3.5.jar:/home/vscode/.m2/repository/org/yaml/snakeyaml/1.29/snakeyaml-1.29.jar:/home/vscode/.m2/repository/org/springframework/boot/spring-boot-starter-json/2.6.1/spring-boot-starter-json-2.6.1.jar:/home/vscode/.m2/repository/com/fasterxml/jackson/core/jackson-databind/2.13.0/jackson-databind-2.13.0.jar:/home/vscode/.m2/repository/com/fasterxml/jackson/core/jackson-annotations/2.13.0/jackson-annotations-2.13.0.jar:/home/vscode/.m2/repository/com/fasterxml/jackson/core/jackson-core/2.13.0/jackson-core-2.13.0.jar:/home/vscode/.m2/repository/com/fasterxml/jackson/datatype/jackson-datatype-jdk8/2.13.0/jackson-datatype-jdk8-2.13.0.jar:/home/vscode/.m2/repository/com/fasterxml/jackson/datatype/jackson-datatype-jsr310/2.13.0/jackson-datatype-jsr310-2.13.0.jar:/home/vscode/.m2/repository/com/fasterxml/jackson/module/jackson-module-parameter-names/2.13.0/jackson-module-parameter-names-2.13.0.jar:/home/vscode/.m2/repository/org/springframework/boot/spring-boot-starter-tomcat/2.6.1/spring-boot-starter-tomcat-2.6.1.jar:/home/vscode/.m2/repository/org/apache/tomcat/embed/tomcat-embed-core/9.0.55/tomcat-embed-core-9.0.55.jar:/home/vscode/.m2/repository/org/apache/tomcat/embed/tomcat-embed-el/9.0.55/tomcat-embed-el-9.0.55.jar:/home/vscode/.m2/repository/org/apache/tomcat/embed/tomcat-embed-websocket/9.0.55/tomcat-embed-websocket-9.0.55.jar:/home/vscode/.m2/repository/org/springframework/spring-web/5.3.13/spring-web-5.3.13.jar:/home/vscode/.m2/repository/org/springframework/spring-beans/5.3.13/spring-beans-5.3.13.jar:/home/vscode/.m2/repository/org/springframework/spring-webmvc/5.3.13/spring-webmvc-5.3.13.jar:/home/vscode/.m2/repository/org/springframework/spring-aop/5.3.13/spring-aop-5.3.13.jar:/home/vscode/.m2/repository/org/springframework/spring-context/5.3.13/spring-context-5.3.13.jar:/home/vscode/.m2/repository/org/springframework/spring-expression/5.3.13/spring-expression-5.3.13.jar:/home/vscode/.m2/repository/org/slf4j/slf4j-api/1.7.32/slf4j-api-1.7.32.jar:/home/vscode/.m2/repository/org/springframework/spring-core/5.3.13/spring-core-5.3.13.jar:/home/vscode/.m2/repository/org/springframework/spring-jcl/5.3.13/spring-jcl-5.3.13.jar:/workspaces/ActionTest/odt-headless/target/classes ActionTestMain