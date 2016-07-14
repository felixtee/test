package felix;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Felix Tee Yong Thye
 */
public class SessionListener implements HttpSessionListener {

    private static final Logger LOG = LoggerFactory.getLogger(SessionListener.class);
    public static final String THIS = SessionListener.class.getSimpleName();
    public static final int V_MAX_INACTIVE_INTERVAL = 15 * 60;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        String sessionId = se.getSession().getId();
        if (V_MAX_INACTIVE_INTERVAL >= 0) {
            se.getSession().setMaxInactiveInterval(V_MAX_INACTIVE_INTERVAL);
        }
        LOG.debug("{}#sessionCreated() id={} maxInactiveInterval={}", THIS, sessionId, se.getSession().getMaxInactiveInterval());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        String sessionId = se.getSession().getId();
        long now = System.currentTimeMillis();
        long delta = now - se.getSession().getCreationTime();
        LOG.debug("{}#sessionDestroyed() id={} delta={}", THIS, sessionId, delta);
    }

}
