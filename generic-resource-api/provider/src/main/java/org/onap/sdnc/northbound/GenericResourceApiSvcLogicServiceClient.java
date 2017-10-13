package org.onap.sdnc.northbound;

import java.util.Properties;

import org.onap.ccsdk.sli.core.sli.SvcLogicException;
import org.onap.ccsdk.sli.core.sli.provider.SvcLogicService;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.preload.data.PreloadDataBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.service.data.ServiceDataBuilder;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenericResourceApiSvcLogicServiceClient {

	private static final Logger LOG = LoggerFactory
			.getLogger(GenericResourceApiSvcLogicServiceClient.class);

	private SvcLogicService svcLogic = null;

	public GenericResourceApiSvcLogicServiceClient(SvcLogicService svcLogic)
	{
		this.svcLogic = svcLogic;
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
