package org.influx.core;
import org.influx.core.web.InfluxServlet;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;

public class Influx implements ServiceListener {
	
	private BundleContext bundleContext;
	private ServiceReference ref = null;
	private InfluxServlet influxServlet = null;
	
	public Influx(BundleContext bc){
		this.bundleContext = bc;
	}
	
	// initialize all necessary services in influx.
	public boolean start() {
		
		startDatabase();
		
		startWeb();
				
		return true;
	}	
	
	public boolean close() {
		
		closeWeb();
		
		return true;
	}	
	
	public void startDatabase() {
	}
	
	public void startWeb(){
		
		influxServlet = new InfluxServlet(bundleContext);
		
		try {
			bundleContext.addServiceListener(this, "(objectClass=" + 
					HttpService.class.getName() + ")");
		} catch (InvalidSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (ref == null){
            ref = bundleContext.getServiceReference(HttpService.class.getName());
        }
        if (ref != null){
            try {
                HttpService http = (HttpService) bundleContext.getService(ref);
                if(http != null){
					http.registerServlet("/core", influxServlet, null, null);
                }
                
                System.out.println("Influx门户成功启动...");
            } 
			catch (Exception e) {
				e.printStackTrace();
			}
        }
	}
	
	private void closeWeb(){
		
		if (ref != null) {
			
            try {
                HttpService http = (HttpService) bundleContext.getService(ref);
                if(null != http){
	                http.unregister("/core");
                }
            }
            catch(Exception e){
            	e.printStackTrace();
            }
        }
	}
	
	public void serviceChanged(ServiceEvent event) {
		switch (event.getType()){
            case ServiceEvent.REGISTERED:
            	startWeb();
                break;

            case ServiceEvent.UNREGISTERING:
                closeWeb();
                break;
        }
	}
}
