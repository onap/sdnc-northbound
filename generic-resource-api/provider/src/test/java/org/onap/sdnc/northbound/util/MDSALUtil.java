/*-
 * ============LICENSE_START=======================================================
 * openECOMP : SDN-C
 * ================================================================================
 * Copyright (C) 2017 AT&T Intellectual Property. All rights
 *                             reserved.
 * ================================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ============LICENSE_END=========================================================
 */

package org.onap.sdnc.northbound.util;

import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Function;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.ContrailRouteTopologyOperationInputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.ContrailRouteTopologyOperationOutputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.NetworkTopologyOperationInputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.NetworkTopologyOperationOutputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.ServiceTopologyOperationInputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.ServiceTopologyOperationOutputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.VfModuleTopologyOperationInputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.VfModuleTopologyOperationOutputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.VnfTopologyOperationInputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.VnfTopologyOperationOutputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.network.information.NetworkInformationBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.network.response.information.NetworkResponseInformationBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.request.information.RequestInformationBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.sdnc.request.header.SdncRequestHeaderBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.service.data.ServiceDataBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.service.information.ServiceInformationBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.service.level.oper.status.ServiceLevelOperStatusBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.service.model.infrastructure.ServiceBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.service.response.information.ServiceResponseInformationBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.service.status.ServiceStatusBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.vf.module.information.VfModuleInformationBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.vnf.information.VnfInformationBuilder;
import org.opendaylight.yangtools.concepts.Builder;
import org.opendaylight.yangtools.yang.common.RpcResult;


/**
 * This uill class provides utility to build yang objects using a recursive syntax that resembles the tree structure
 * when defining the same yang object in json format.
 *
 * For Example
 * <pre>
 * {@code
 * import static org.onap.sdnc.northbound.util.MDSALUtil.*;
 * ServiceTopologyOperationInput input = build(
 *         serviceTopologyOperationInput()
 *                 .setSdncRequestHeader(build(sdncRequestHeader()
 *                         .setSvcRequestId("svc-request-id: xyz")
 *                         .setSvcAction(SvcAction.Assign)
 *                 ))
 *                 .setRequestInformation(build(requestInformation()
 *                         .setRequestId("request-id: xyz")
 *                        .setRequestAction(RequestInformation.RequestAction.CreateServiceInstance)
 *                 ))
 *                .setServiceInformation(build(serviceInformationBuilder()
 *                         .setServiceInstanceId("service-instance-id: xyz")
 *                ))
 * );
 * }
 * </pre>
 */
public class MDSALUtil {

    public static ContrailRouteTopologyOperationInputBuilder contrailRouteTopologyOperationInput() {
        return new ContrailRouteTopologyOperationInputBuilder();
    }

    public static ContrailRouteTopologyOperationOutputBuilder contrailRouteTopologyOperationOutput() {
        return new ContrailRouteTopologyOperationOutputBuilder();
    }

    public static VfModuleTopologyOperationInputBuilder vfModuleTopologyOperationInput() {
        return new VfModuleTopologyOperationInputBuilder();
    }

    public static VfModuleTopologyOperationOutputBuilder vfModuleTopologyOperationOutput() {
        return new VfModuleTopologyOperationOutputBuilder();
    }

    public static VnfTopologyOperationInputBuilder vnfTopologyOperationInput() {
        return new VnfTopologyOperationInputBuilder();
    }

    public static VnfTopologyOperationOutputBuilder vnfTopologyOperationOutput() {
        return new VnfTopologyOperationOutputBuilder();
    }

    public static ServiceTopologyOperationInputBuilder serviceTopologyOperationInput() {
        return new ServiceTopologyOperationInputBuilder();
    }

    public static ServiceTopologyOperationOutputBuilder serviceTopologyOperationOutput() {
        return new ServiceTopologyOperationOutputBuilder();
    }


    public static SdncRequestHeaderBuilder sdncRequestHeader() {
        return new SdncRequestHeaderBuilder();
    }


    public static RequestInformationBuilder requestInformation() {
        return new RequestInformationBuilder();
    }

    public static ServiceResponseInformationBuilder serviceResponseInformation() {
        return new ServiceResponseInformationBuilder();
    }

    public static ServiceInformationBuilder serviceInformationBuilder() {
        return new ServiceInformationBuilder();
    }

    public static VnfInformationBuilder vnfInformationBuilder() {
        return new VnfInformationBuilder();
    }

    public static VfModuleInformationBuilder vfModuleInformationBuilder() {
        return new VfModuleInformationBuilder();
    }

    public static ServiceBuilder service() {
        return new ServiceBuilder();
    }


    public static ServiceDataBuilder serviceData() {
        return new ServiceDataBuilder();
    }


    public static ServiceStatusBuilder serviceStatus() {
        return new ServiceStatusBuilder();
    }

    public static NetworkInformationBuilder networkInformation() {
        return new NetworkInformationBuilder();
    }

    public static NetworkTopologyOperationInputBuilder networkTopologyOperationInput() {
        return new NetworkTopologyOperationInputBuilder();
    }

    public static NetworkTopologyOperationOutputBuilder networkTopologyOperationOutput() {
        return new NetworkTopologyOperationOutputBuilder();
    }

    public static NetworkResponseInformationBuilder networkResponseInformation() {
        return new NetworkResponseInformationBuilder();
    }

    public static ServiceLevelOperStatusBuilder serviceLevelOperStatus() {
        return new ServiceLevelOperStatusBuilder();
    }

    public static <P> P build(Builder<P> b) {
        return b == null ? null : b.build();
    }

    public static <P, B extends Builder<P>> P build(Function<P, B> builderConstructor, P sourceDataObject) {
        if (sourceDataObject == null) {
            return null;
        }
        B bp = builderConstructor.apply(sourceDataObject);
        return bp.build();
    }

    public static <P, B extends Builder<P>> P build(Function<P, B> builderConstructor, P sourceDataObject,
        Consumer<B> builder) {
        if (sourceDataObject == null) {
            return null;
        }
        B bp = builderConstructor.apply(sourceDataObject);
        builder.accept(bp);
        return bp.build();
    }

    public static <I, O> O exec(Function<I, Future<RpcResult<O>>> rpc, I rpcParameter,
        Function<RpcResult<O>, O> rpcResult) throws Exception {
        Future<RpcResult<O>> future = rpc.apply(rpcParameter);
        return rpcResult.apply(future.get());
    }

}
