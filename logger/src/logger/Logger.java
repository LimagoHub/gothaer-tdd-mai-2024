package logger;


import gothaer.Writer;

public class Logger {

    private final Writer writer;

    public Logger(Writer writer) {
        this.writer = writer;
    }

    public void log(String message){
        final String prefix = "Komplizierter Prefix ";
        writer.write(prefix + message);
    }
}
