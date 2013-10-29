package org.kevoree.modeling.optimization.web;

import org.kevoree.log.Log;
import org.webbitserver.HttpControl;
import org.webbitserver.HttpRequest;
import org.webbitserver.HttpResponse;
import org.webbitserver.handler.AbstractResourceHandler;
import org.webbitserver.handler.TemplateEngine;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executor;

import static java.util.concurrent.Executors.newFixedThreadPool;

// Maybe http://www.uofr.net/~greg/java/get-resource-listing.html
public class EmbedHandler extends AbstractResourceHandler {

    public EmbedHandler(Executor ioThread, TemplateEngine templateEngine) {
        super(ioThread, templateEngine);
    }

    public EmbedHandler(Executor ioThread) {
        super(ioThread);
    }

    public EmbedHandler() {
        super(newFixedThreadPool(4));
    }

    private String prefix = "/";

    public EmbedHandler(String prefix) {
        super(newFixedThreadPool(4));
        this.prefix = prefix;
        if(!this.prefix.startsWith("/")){
             this.prefix = "/"+this.prefix;
        }
        if(!this.prefix.endsWith("/")){
            this.prefix = this.prefix+"/";
        }
    }

    @Override
    protected ResourceWorker createIOWorker(HttpRequest request,
                                            HttpResponse response,
                                            HttpControl control) {
        return new ResourceWorker(request, response, control);
    }

    protected class ResourceWorker extends IOWorker {

        private final HttpResponse response;

        private final HttpRequest request;

        protected ResourceWorker(HttpRequest request, HttpResponse response, HttpControl control) {
            super(request.uri(), request, response, control);
            this.response = response;
            this.request = request;
        }

        @Override
        protected boolean exists() throws IOException {
            String path2 = prefix;
            if(path.startsWith("/")){
                path2 = path2 + path.substring(1);
            } else {
                path2 = path2 + path;
            }
            if (path2.equals(prefix)) {
                path2 = prefix+"index.html";
            }
            return this.getClass().getResource(path2) != null;
        }

        @Override
        protected boolean isDirectory() throws IOException {
            return false;
        }

        private byte[] read(InputStream content) throws IOException {
            try {
                return read(content.available(), content);
            } catch (NullPointerException happensWhenReadingDirectoryPathInJar) {
                return null;
            }
        }

        @Override
        protected byte[] fileBytes() throws IOException {
            String path2 = prefix;
            if(path.startsWith("/")){
                path2 = path2 + path.substring(1);
            } else {
                path2 = path2 + path;
            }
            if (path2.equals(prefix)) {
                path2 = prefix+"index.html";
            }
            return read(this.getClass().getResourceAsStream(path2));
        }

        @Override
        protected byte[] welcomeBytes() throws IOException {
            read(this.getClass().getResourceAsStream(prefix+"index.html"));
            return null;
        }

        @Override
        protected byte[] directoryListingBytes() throws IOException {
            return null;
        }

    }
}