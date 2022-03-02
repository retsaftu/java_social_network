import org.apache.log4j.*;

import java.io.IOException;

public class MyLogger<T> {
    Class<T> className;

    public MyLogger(Class<T> className) {
        this.className = className;
    }

    void myLog(String message, String type) throws IOException {

        ///////// КАК СОЗДАВАТЬ ИНСТАНС ЭТОГО ЛОГЕРА//////////////
        //        ClassName=класс где ты находишься 
        //        MyLogger<ClassName> myLogger = new MyLogger<>(ClassName.class);
        //        myLogger.myLog("Tesgsfffft", "INFO");   или INFO
        //        myLogger.myLog("Tesgsfffft", "ERROR");  или ERROR
        
        PatternLayout patternLayoutObj = new PatternLayout();
        String conversionPattern = "[%p] %d %M - %m%n";
        patternLayoutObj.setConversionPattern(conversionPattern);

        RollingFileAppender rollingFileAppender= new RollingFileAppender(patternLayoutObj, "logs.log", true);

        rollingFileAppender.setMaxFileSize("10MB");
        rollingFileAppender.setMaxBackupIndex(10);
        rollingFileAppender.activateOptions();

        DailyRollingFileAppender rollingAppenderObj = new DailyRollingFileAppender();
        rollingAppenderObj.setFile("logs.log");
        rollingAppenderObj.setDatePattern("'.'yyyy-MM-dd");
        rollingAppenderObj.setLayout(patternLayoutObj);

        Logger rootLoggerObj = Logger.getRootLogger();
        rootLoggerObj.setLevel(Level.ALL);
        rootLoggerObj.addAppender(rollingFileAppender);


        if(type.equals("ERROR")){
            rootLoggerObj.error(message);
        } else {
            rootLoggerObj.info(message);
        }

    }
}
