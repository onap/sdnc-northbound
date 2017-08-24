package com.att.sdnctl.app;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.service.data.ServiceDataBuilder;
import org.opendaylight.yang.gen.v1.com.att.sdnctl.generic.resource.rev161111.preload.data.PreloadDataBuilder;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.IpAddress;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.openecomp.sdnc.sli.SvcLogicException;
import org.openecomp.sdnc.sli.provider.SvcLogicService;

public class GenericResourceApiSvcLogicServiceClient {

	private static final Logger LOG = LoggerFactory
			.getLogger(GenericResourceApiSvcLogicServiceClient.class);

	private SvcLogicService svcLogic = null;
	
	public GenericResourceApiSvcLogicServiceClient()
	{
		BundleContext bctx = FrameworkUtil.getBundle(SvcLogicService.class).getBundleContext();

    	// Get SvcLogicService reference		
		ServiceReference sref = bctx.getServiceReference(SvcLogicService.NAME);
		if (sref  != null)
		{
			svcLogic =  (SvcLogicService) bctx.getService(sref);
			
		}
		else
		{
			LOG.warn("Cannot find service reference for "+SvcLogicService.NAME);

		}
	}
	
	public boolean hasGraph(String module, String rpc, String version, String mode) throws SvcLogicException
	{
		return(svcLogic.hasGraph(module, rpc, version, mode));
	}
	
	public Properties execute(String module, String rpc, String version, String mode, ServiceDataBuilder serviceData)
			throws SvcLogicException {

		Properties parms = new Properties();
		
		return execute(module,rpc,version, mode,serviceData,parms);
	}
		
	public Properties execute(String module, String rpc, String version, String mode, PreloadDataBuilder serviceData)
			throws SvcLogicException {

		Properties parms = new Properties();
		
		return execute(module,rpc,version, mode,serviceData,parms);
	}


	public Properties execute(String module, String rpc, String version, String mode, ServiceDataBuilder serviceData, Properties parms)
				throws SvcLogicException {

		parms = GenericResourceApiUtil.toProperties(parms, serviceData);

		if (LOG.isDebugEnabled())
		{
			LOG.debug("Parameters passed to SLI");
			
			for (Object key : parms.keySet()) {
				String parmName = (String) key;
				String parmValue = parms.getProperty(parmName);
				
				LOG.debug(parmName+" = "+parmValue);
				
			}
		}
		
		Properties respProps = svcLogic.execute(module, rpc, version, mode, parms);
		
		if (LOG.isDebugEnabled())
		{
			LOG.debug("Parameters returned by SLI");
			
			for (Object key : respProps.keySet()) {
				String parmName = (String) key;
				String parmValue = respProps.getProperty(parmName);
				
				LOG.debug(parmName+" = "+parmValue);
				
			}
		}
		if ("failure".equalsIgnoreCase(respProps.getProperty("SvcLogic.status"))) {
			return (respProps);
		}
		
		GenericResourceApiUtil.toBuilder(respProps, serviceData);

		return (respProps);
	}

	
	public Properties execute(String module, String rpc, String version, String mode, PreloadDataBuilder serviceData, Properties parms)
				throws SvcLogicException {

		parms = GenericResourceApiUtil.toProperties(parms, serviceData);

		if (LOG.isDebugEnabled())
		{
			LOG.debug("Parameters passed to SLI");
			
			for (Object key : parms.keySet()) {
				String parmName = (String) key;
				String parmValue = parms.getProperty(parmName);
				
				LOG.debug(parmName+" = "+parmValue);
				
			}
		}
		
		Properties respProps = svcLogic.execute(module, rpc, version, mode, parms);
		
		if (LOG.isDebugEnabled())
		{
			LOG.debug("Parameters returned by SLI");
			
			for (Object key : respProps.keySet()) {
				String parmName = (String) key;
				String parmValue = respProps.getProperty(parmName);
				
				LOG.debug(parmName+" = "+parmValue);
				
			}
		}
		if ("failure".equalsIgnoreCase(respProps.getProperty("SvcLogic.status"))) {
			return (respProps);
		}
		
		GenericResourceApiUtil.toBuilder(respProps, serviceData);

		return (respProps);
	}
}
