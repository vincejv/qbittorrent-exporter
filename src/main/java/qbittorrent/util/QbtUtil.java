package qbittorrent.util;

/**
 * Utility class for parsing data in qBittorrent api
 *
 * @author <a href="mailto:vincevillamora@gmail.com">Vince Villamora</a>
 */
public final class QbtUtil {

    /**
     * Takes a url such as <a href="http://www.stackoverflow.com">http://www.stackoverflow.com</a>
     * and return www.stackoverflow.com
     *
     * @param url Url to parse
     * @return domain name
     */
    public static String getHost(final String url){
        if(url == null || url.isEmpty())
            return "";

        int doubleslash = url.indexOf("//");
        if(doubleslash == -1)
            doubleslash = 0;
        else
            doubleslash += 2;

        int end = url.indexOf('/', doubleslash);
        end = end >= 0 ? end : url.length();

        final int port = url.indexOf(':', doubleslash);
        end = (port > 0 && port < end) ? port : end;

        return url.substring(doubleslash, end);
    }

    private QbtUtil() {}
}
