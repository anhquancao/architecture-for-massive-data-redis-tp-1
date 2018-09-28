package context;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AppContext {
    private BufferedReader in;
    private static AppContext appContext;

    private AppContext() {

    }

    public static AppContext getAppContext() {
        if (appContext == null) {
            appContext = new AppContext();
        }
        return appContext;
    }

    public BufferedReader getReaderInstance() {
        if (in == null) {
            in = new BufferedReader(new InputStreamReader(System.in));
        }
        return in;
    }
}
