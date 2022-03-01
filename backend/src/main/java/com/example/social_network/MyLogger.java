import org.apache.log4j.*;

import java.io.IOException;

public class MyLogger<T> {
    Class<T> className;

    public MyLogger(Class<T> className) {
        this.className = className;
    }

    void myLog() throws IOException {
        //how to create instance of this class
        //MyLogger<Main> myLogger = new MyLogger<Main>(Main.class);
        //        myLogger.myLog();

        PatternLayout patternLayoutObj = new PatternLayout();
        String conversionPattern = "[%p] %d %M - %m%n";
        patternLayoutObj.setConversionPattern(conversionPattern);
        // Create Daily Rolling Log File Appender
        RollingFileAppender rollingFileAppender= new RollingFileAppender(patternLayoutObj, "logs.log", true);
//        rollingFileAppender.setFile("logs.log");

        rollingFileAppender.setMaxFileSize("2KB");
        rollingFileAppender.setMaxBackupIndex(10);
        System.out.println(rollingFileAppender.getMaximumFileSize());
//        rollingFileAppender.rollOver();
        rollingFileAppender.activateOptions();

//        DailyRollingFileAppender rollingAppenderObj = new DailyRollingFileAppender();
//        rollingAppenderObj.setFile("logs.log");
//        rollingAppenderObj.setDatePattern("'.'yyyy-MM-dd");
//        rollingAppenderObj.setLayout(patternLayoutObj);
//        rollingAppenderObj.activateOptions();

        Logger rootLoggerObj = Logger.getRootLogger();
        rootLoggerObj.setLevel(Level.ALL);
//        rootLoggerObj.addAppender(rollingAppenderObj);
        rootLoggerObj.addAppender(rollingFileAppender);


        // Create a Customer Logger & Logs Messages
        Logger loggerObj = Logger.getLogger(this.className);
        loggerObj.debug("This is a debug log message");
        loggerObj.info("This is an information log message");
        loggerObj.warn("This is a warning log message");
    }
}
