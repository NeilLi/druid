package com.alibaba.druid.support.http;

import com.alibaba.druid.util.IpUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.alibaba.druid.stat.DruidStatManagerFacade;
import com.alibaba.druid.support.JSONDruidStatService;
import com.alibaba.druid.util.IOUtils;
import com.alibaba.druid.util.StringUtils;

/**
 * 注意：避免直接调用Druid相关对象例如DruidDataSource等，相关调用要到DruidStatManagerFacade里用反射实现
 * 
 * @author sandzhang<sandzhangtoo@gmail.com>
 */
public class StatViewServlet extends HttpServlet {

    /**
     * 
     */
    private static final long             serialVersionUID            = 1L;

    private final static String           RESOURCE_PATH               = "support/http/resources";
    private final static String           TEMPLATE_PAGE_RESOURCE_PATH = RESOURCE_PATH + "/template.html";

    private static DruidStatManagerFacade druidStatManager            = DruidStatManagerFacade.getInstance();
    private static JSONDruidStatService   jsonDruidStatService        = JSONDruidStatService.getInstance();

    public String                         templatePage;
   

    private String                        permittedIp;
    private Pattern                       ipCheckPattern;

    public void init() throws ServletException {
        try {
            templatePage = IOUtils.readFromResource(TEMPLATE_PAGE_RESOURCE_PATH);
            
        } catch (IOException e) {
            throw new ServletException("error read templatePage:" + TEMPLATE_PAGE_RESOURCE_PATH, e);
        }
        permittedIp = getServletConfig().getInitParameter("permittedIp");
        if (permittedIp != null ) {
            this.ipCheckPattern = IpUtils.buildIpCheckPattern(permittedIp);
        }
    }

    private boolean isPermittedRequest(HttpServletRequest request) {
        //if nothing is configured, every host can access the stat result(default)
        if (permittedIp == null || permittedIp.trim().equals("")) {
            return true;
        }
        String remoteIp = request.getRemoteAddr();
        Matcher matcher =  ipCheckPattern.matcher(remoteIp);
        return matcher.matches();
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();
        String requestURI = request.getRequestURI();

        response.setCharacterEncoding("utf-8");

        

        if (contextPath == null) { // root context
            contextPath = "";
        }
        String uri = contextPath + servletPath;
        String path = requestURI.substring(contextPath.length() + servletPath.length());

        if (!isPermittedRequest(request)) {
            path = "/nopermit.html";
            returnResourceFile(path, uri, response);
            return;
        }
        
        if ("".equals(path)) {
            if (contextPath == null || contextPath.equals("") || contextPath.equals("/")) {
                response.sendRedirect("/druid/index.html");
            } else {
                response.sendRedirect("druid/index.html");
            }
            return;
        }

        if ("/".equals(path)) {
            response.sendRedirect("index.html");
            return;
        }

        if (path.indexOf(".json") >= 0) {
            String fullUrl = path;
            if (request.getQueryString() != null && request.getQueryString().length() > 0) {
                fullUrl += "?" + request.getQueryString();
            }
            response.getWriter().print(jsonDruidStatService.service(fullUrl));
            return;
        }

        if (path.startsWith("/connectionInfo-") && path.endsWith(".html")) {
            Integer id = StringUtils.subStringToInteger(path, "connectionInfo-", ".");
            returnViewConnectionInfo(id, request, response);
            return;
        }

        if (path.startsWith("/activeConnectionStackTrace-") && path.endsWith(".html")) {
            Integer id = StringUtils.subStringToInteger(path, "activeConnectionStackTrace-", ".");
            returnViewActiveConnectionStackTrace(id, request, response);
            return;
        }

        if (path.startsWith("/sql-") && path.endsWith(".html")) {
            Integer id = StringUtils.subStringToInteger(path, "sql-", ".");
            returnViewSqlStat(id, request,response);
            return;
        }

        // find file in resources path
        returnResourceFile(path, uri, response);
    }

    private void returnViewActiveConnectionStackTrace(Integer id, HttpServletRequest request,
                                                      HttpServletResponse response) throws IOException {

        String text = IOUtils.readFromResource(RESOURCE_PATH + "/activeConnectionStackTrace.html");
        text = text.replaceAll("\\{datasourceId\\}", id.toString());
        response.getWriter().print(text);
    }

    private void returnViewConnectionInfo(Integer id, HttpServletRequest request, HttpServletResponse response)
                                                                                                               throws IOException {
        String text = IOUtils.readFromResource(RESOURCE_PATH + "/connectionInfo.html");
        text = text.replaceAll("\\{datasourceId\\}", id.toString());
        response.getWriter().print(text);
    }

    private void returnViewSqlStat(Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String text = IOUtils.readFromResource(RESOURCE_PATH + "/sqlDetail.html");
        text = text.replaceAll("\\{sqlId\\}", id.toString());
        response.getWriter().print(text);
    }

    

    private void returnResourceFile(String fileName, String uri, HttpServletResponse response) throws ServletException, IOException {
        String text = IOUtils.readFromResource(RESOURCE_PATH + fileName);
        if(text == null) {
            response.sendRedirect(uri + "/index.html");
            return;
        }
        if (fileName.endsWith(".css")) {
            response.setContentType("text/css;charset=utf-8");
        } else if (fileName.endsWith(".js")) {
            response.setContentType("text/javascript;charset=utf-8");
        }
        response.getWriter().write(text);
    }

}
