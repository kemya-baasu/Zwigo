package zwigo.api;

import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
public class AsyncCheckOutListener implements AsyncListener {
    public void onComplete(AsyncEvent asyncEvent) throws IOException {
        System.out.println("Async Task Completed!");
    }

    public void onTimeout(AsyncEvent asyncEvent) throws IOException {
        System.out.println("Async Timeout Error!"+asyncEvent.getSuppliedResponse());
    }

    public void onError(AsyncEvent asyncEvent) throws IOException {
        System.out.println("Async Throws Error!"+asyncEvent.getSuppliedResponse());
    }

    public void onStartAsync(AsyncEvent asyncEvent) throws IOException {
        System.out.println("Async Started!");
    }
}
