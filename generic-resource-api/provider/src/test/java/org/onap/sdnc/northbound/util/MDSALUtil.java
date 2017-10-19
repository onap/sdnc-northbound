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

import com.google.common.base.Optional;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.ServiceTopologyOperationInputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.ServiceTopologyOperationOutputBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.Services;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.request.information.RequestInformationBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.sdnc.request.header.SdncRequestHeaderBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.service.data.ServiceDataBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.service.information.ServiceInformationBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.service.model.infrastructure.Service;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.service.model.infrastructure.ServiceBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.service.model.infrastructure.ServiceKey;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.service.response.information.ServiceResponseInformationBuilder;
import org.opendaylight.yang.gen.v1.org.onap.sdnc.northbound.generic.resource.rev170824.service.status.ServiceStatusBuilder;
import org.opendaylight.yangtools.concepts.Builder;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.opendaylight.yangtools.yang.common.RpcResult;

import java.util.concurrent.Future;
import java.util.function.Function;


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

    public static ServiceTopologyOperationInputBuilder serviceTopologyOperationInput() {
        return new ServiceTopologyOperationInputBuilder();
    }


    public static ServiceTopologyOperationOutputBuilder serviceTopologyOperationOutput(){
        return new ServiceTopologyOperationOutputBuilder();
    }


    public static SdncRequestHeaderBuilder sdncRequestHeader() {
        return new SdncRequestHeaderBuilder();
    }


    public static RequestInformationBuilder requestInformation() {
        return new RequestInformationBuilder();
    }

    public static ServiceResponseInformationBuilder serviceResponseInformation(){
        return  new ServiceResponseInformationBuilder();
    }

    public static ServiceInformationBuilder serviceInformationBuilder() {
        return  new ServiceInformationBuilder();
    }


    public static ServiceBuilder service(){return new ServiceBuilder();}


    public static ServiceDataBuilder serviceData(){return new ServiceDataBuilder();}


    public static ServiceStatusBuilder serviceStatus(){return new ServiceStatusBuilder();}

    public static <P> P build(Builder<P> b) {
        return b == null? null :b.build();
    }

    public static <O> O result(Future<RpcResult<O>> future, Function<RpcResult<O>,O> function)  throws Exception {
        return function.apply(future.get());
    }

    public static <I,O> O rpc(Function<I,Future<RpcResult<O>>> rpc,Function<RpcResult<O>,O> function,I input)  throws Exception {
        Future<RpcResult<O>> future = rpc.apply(input);
        return function.apply(future.get());
    }



    /** @return Service - the Service object read from the DataBroker or null if none was found */
    public static Service read(DataBroker dataBroker,String serviceKey, LogicalDatastoreType logicalDatastoreType) throws Exception {
        InstanceIdentifier serviceInstanceIdentifier = InstanceIdentifier.<Services>builder(Services.class)
                .child(Service.class, new ServiceKey(serviceKey)).build();
        ReadOnlyTransaction readTx = dataBroker.newReadOnlyTransaction();
        Optional<Service> data = (Optional<Service>) readTx.read(logicalDatastoreType, serviceInstanceIdentifier).get();
        if(!data.isPresent()){
            return null;
        }


        //The toString() value from a Service object returned form data.get() is different than the toString() value
        //from a Service Object constructed from a Builder. This makes it difficult to compare deltas when doing a
        // assertEquals.  That why we rebuild it her to solve that problem.
        Service service = data.get();
        return build(
                (new ServiceBuilder(service))
                        .setServiceStatus(build(
                                service.getServiceStatus() == null ? null : new ServiceStatusBuilder(service.getServiceStatus())
                        ))
                        .setServiceData(build(
                                service.getServiceData() == null ? null : new ServiceDataBuilder(service.getServiceData())
                        ))
        );
    }







}
