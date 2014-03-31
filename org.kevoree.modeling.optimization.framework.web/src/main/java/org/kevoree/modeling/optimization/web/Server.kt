package org.kevoree.modeling.optimization.web

import java.io.File
import org.webbitserver.WebServers
import org.kevoree.log.Log
import org.webbitserver.handler.StaticFileHandler
import org.webbitserver.HttpHandler
import org.webbitserver.HttpRequest
import org.webbitserver.HttpResponse
import org.webbitserver.HttpControl
import org.kevoree.modeling.optimization.executionmodel.ExecutionModel

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 29/10/2013
 * Time: 14:22
 */

public object Server {

    fun serveExecutionModel(model: ExecutionModel){

        var baseStaticDir : File?  = null;
        var staticDirFromRoot = File("org.kevoree.modeling.optimization.framework.web/src/main/resources/static");
        if (staticDirFromRoot.exists() && staticDirFromRoot.isDirectory()) {
            baseStaticDir = staticDirFromRoot;
        } else {
            var staticDirFromProject = File("src/main/resources/static");
            if (staticDirFromProject.exists() && staticDirFromProject.isDirectory()) {
                baseStaticDir = staticDirFromProject;
            }
        }

        var webServer = WebServers.createWebServer(8080)!!
        webServer.add("/data.csv",MetricExporterHttpHandler(model,false))
        webServer.add("/time.csv",MetricExporterHttpHandler(model,true))
        if (baseStaticDir == null) {
            Log.info("Resolve files from classloader");
            webServer.add(EmbedHandler("static"));
        } else {
            Log.info("Resolve from "+baseStaticDir!!.getAbsolutePath());
            webServer.add(StaticFileHandler(baseStaticDir));
        }
        webServer.start();
        System.out.println("Server running at " + webServer.getUri());
    }

}
