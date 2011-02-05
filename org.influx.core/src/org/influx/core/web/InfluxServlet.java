package org.influx.core.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

public class InfluxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BundleContext context;

	public InfluxServlet(BundleContext context) {
		this.context = context;
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		ServletOutputStream output = response.getOutputStream();
		String core_page = null;
		core_page = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \n" +
		            "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">" + "\n";
		core_page = core_page + "<html>\n" + "<body>\n";
		core_page = core_page + "<title>Influx Core Homepage</title>\n";
		core_page = core_page + "<b>Influx Core Homepage</b>\n";
		core_page = core_page + "<div>The following is the list of bundles running in the influx core:</div>\n";
		core_page = core_page + getAllInfluxBundlesTable();
		core_page = core_page + "</body>\n" + "</html>";
		output.println(core_page);
		output.close();
	}

	protected String getAllInfluxBundlesTable() {

		// get all bundles
		Bundle[] bundles = context.getBundles();

		String bundle_names = "\n<table border=\"1\">";
		int i = 0;

		// for each bundle except this bundle
		for (Bundle bundle : bundles) {
			// for each bundle, add a new row in the table
			bundle_names = bundle_names + 
			  "<tr><td>" + ++i + "</td>\n" +
			  "<td>"+ bundle.getSymbolicName()+ "</td></tr>\n";
		}
		bundle_names = bundle_names + "</table>\n";

		return bundle_names;
	}

}
