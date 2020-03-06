package org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.optical.service.rev191206;
import com.google.common.util.concurrent.ListenableFuture;
import org.opendaylight.yangtools.yang.binding.RpcService;
import org.opendaylight.yangtools.yang.common.RpcResult;

/**
 * Interface for implementing the following YANG RPCs defined in module <b>opticalservice</b>
 * <pre>
 * rpc optical-service-delete {
 *   input {
 *     uses optical-service-delete-request;
 *   }
 *   output {
 *     uses optical-service-response;
 *   }
 * }
 * rpc optical-service-create {
 *   input {
 *     uses optical-service-create-request;
 *   }
 *   output {
 *     uses optical-service-response;
 *   }
 * }
 * </pre>
 *
 */
public interface OpticalserviceService
    extends
    RpcService
{




    ListenableFuture<RpcResult<OpticalServiceDeleteOutput>> opticalServiceDelete(OpticalServiceDeleteInput input);
    
    ListenableFuture<RpcResult<OpticalServiceCreateOutput>> opticalServiceCreate(OpticalServiceCreateInput input);

}

