package org.opendaylight.yang.gen.v1.org.openecomp.sdnctl.capacity.rev170411;
import org.opendaylight.yangtools.yang.binding.RpcService;
import org.opendaylight.yangtools.yang.common.RpcResult;
import java.util.concurrent.Future;

/**
 * Interface for implementing the following YANG RPCs defined in module <b>CAPACITY-API</b>
 * <pre>
 * rpc service-capacity-reserve-operation {
 *     input {
 *         container capacity-reservation-information {
 *             leaf service-model {
 *                 type string;
 *             }
 *             list reservation-entity-list {
 *                 key "reservation-entity-id";
 *                 leaf reservation-entity-type {
 *                     type string;
 *                 }
 *                 leaf reservation-entity-id {
 *                     type string;
 *                 }
 *                 list reservation-entity-data {
 *                     key "name";
 *                     leaf name {
 *                         type string;
 *                     }
 *                     leaf value {
 *                         type string;
 *                     }
 *                 }
 *             }
 *             list reservation-target-list {
 *                 key "reservation-target-id";
 *                 leaf reservation-target-type {
 *                     type string;
 *                 }
 *                 leaf reservation-target-id {
 *                     type string;
 *                 }
 *                 list reservation-target-data {
 *                     key "name";
 *                     leaf name {
 *                         type string;
 *                     }
 *                     leaf value {
 *                         type string;
 *                     }
 *                 }
 *             }
 *             list reservation-resource-list {
 *                 leaf resource-name {
 *                     type string;
 *                 }
 *                 leaf endpoint-position {
 *                     type string;
 *                 }
 *                 leaf resource-share-group {
 *                     type string;
 *                 }
 *             }
 *         }
 *     }
 *     
 *     output {
 *         leaf response-code {
 *             type string;
 *         }
 *         leaf response-message {
 *             type string;
 *         }
 *         container capacity-information {
 *             leaf status {
 *                 type string;
 *             }
 *             list reservation-entity-list {
 *                 key "reservation-entity-id";
 *                 leaf reservation-entity-id {
 *                     type string;
 *                 }
 *                 leaf reservation-entity-type {
 *                     type string;
 *                 }
 *                 leaf status {
 *                     type string;
 *                 }
 *                 list reservation-target-list {
 *                     key "reservation-target-id";
 *                     leaf reservation-target-id {
 *                         type string;
 *                     }
 *                     leaf status {
 *                         type string;
 *                     }
 *                     list resource-list {
 *                         leaf resource-name {
 *                             type string;
 *                         }
 *                         leaf endpoint-position {
 *                             type string;
 *                         }
 *                         leaf status {
 *                             type string;
 *                         }
 *                         leaf allocated {
 *                             type string;
 *                         }
 *                         leaf available {
 *                             type string;
 *                         }
 *                         leaf used {
 *                             type string;
 *                         }
 *                         leaf limit {
 *                             type string;
 *                         }
 *                     }
 *                 }
 *             }
 *         }
 *     }
 * }
 * rpc service-capacity-check-operation {
 *     input {
 *         container capacity-reservation-information {
 *             leaf service-model {
 *                 type string;
 *             }
 *             list reservation-entity-list {
 *                 key "reservation-entity-id";
 *                 leaf reservation-entity-type {
 *                     type string;
 *                 }
 *                 leaf reservation-entity-id {
 *                     type string;
 *                 }
 *                 list reservation-entity-data {
 *                     key "name";
 *                     leaf name {
 *                         type string;
 *                     }
 *                     leaf value {
 *                         type string;
 *                     }
 *                 }
 *             }
 *             list reservation-target-list {
 *                 key "reservation-target-id";
 *                 leaf reservation-target-type {
 *                     type string;
 *                 }
 *                 leaf reservation-target-id {
 *                     type string;
 *                 }
 *                 list reservation-target-data {
 *                     key "name";
 *                     leaf name {
 *                         type string;
 *                     }
 *                     leaf value {
 *                         type string;
 *                     }
 *                 }
 *             }
 *             list reservation-resource-list {
 *                 leaf resource-name {
 *                     type string;
 *                 }
 *                 leaf endpoint-position {
 *                     type string;
 *                 }
 *                 leaf resource-share-group {
 *                     type string;
 *                 }
 *             }
 *         }
 *     }
 *     
 *     output {
 *         leaf response-code {
 *             type string;
 *         }
 *         leaf response-message {
 *             type string;
 *         }
 *         container capacity-information {
 *             leaf status {
 *                 type string;
 *             }
 *             list reservation-entity-list {
 *                 key "reservation-entity-id";
 *                 leaf reservation-entity-id {
 *                     type string;
 *                 }
 *                 leaf reservation-entity-type {
 *                     type string;
 *                 }
 *                 leaf status {
 *                     type string;
 *                 }
 *                 list reservation-target-list {
 *                     key "reservation-target-id";
 *                     leaf reservation-target-id {
 *                         type string;
 *                     }
 *                     leaf status {
 *                         type string;
 *                     }
 *                     list resource-list {
 *                         leaf resource-name {
 *                             type string;
 *                         }
 *                         leaf endpoint-position {
 *                             type string;
 *                         }
 *                         leaf status {
 *                             type string;
 *                         }
 *                         leaf allocated {
 *                             type string;
 *                         }
 *                         leaf available {
 *                             type string;
 *                         }
 *                         leaf used {
 *                             type string;
 *                         }
 *                         leaf limit {
 *                             type string;
 *                         }
 *                     }
 *                 }
 *             }
 *         }
 *     }
 * }
 * rpc service-capacity-release-operation {
 *     input {
 *         leaf service-model {
 *             type string;
 *         }
 *         leaf action {
 *             type enumeration;
 *         }
 *         list reservation-entity-list {
 *             key "reservation-entity-id";
 *             leaf reservation-entity-id {
 *                 type string;
 *             }
 *             leaf reservation-entity-type {
 *                 type string;
 *             }
 *         }
 *         list reservation-resource-list {
 *             leaf endpoint-position {
 *                 type string;
 *             }
 *         }
 *     }
 *     
 *     output {
 *         leaf response-code {
 *             type string;
 *         }
 *         leaf response-message {
 *             type string;
 *         }
 *     }
 * }
 * rpc service-capacity-get-operation {
 *     input {
 *         container capacity-reservation-information {
 *             leaf service-model {
 *                 type string;
 *             }
 *             list reservation-entity-list {
 *                 key "reservation-entity-id";
 *                 leaf reservation-entity-type {
 *                     type string;
 *                 }
 *                 leaf reservation-entity-id {
 *                     type string;
 *                 }
 *                 list reservation-entity-data {
 *                     key "name";
 *                     leaf name {
 *                         type string;
 *                     }
 *                     leaf value {
 *                         type string;
 *                     }
 *                 }
 *             }
 *             list reservation-target-list {
 *                 key "reservation-target-id";
 *                 leaf reservation-target-type {
 *                     type string;
 *                 }
 *                 leaf reservation-target-id {
 *                     type string;
 *                 }
 *                 list reservation-target-data {
 *                     key "name";
 *                     leaf name {
 *                         type string;
 *                     }
 *                     leaf value {
 *                         type string;
 *                     }
 *                 }
 *             }
 *             list reservation-resource-list {
 *                 leaf resource-name {
 *                     type string;
 *                 }
 *                 leaf endpoint-position {
 *                     type string;
 *                 }
 *                 leaf resource-share-group {
 *                     type string;
 *                 }
 *             }
 *         }
 *     }
 *     
 *     output {
 *         leaf response-code {
 *             type string;
 *         }
 *         leaf response-message {
 *             type string;
 *         }
 *         container capacity-information {
 *             leaf status {
 *                 type string;
 *             }
 *             list reservation-entity-list {
 *                 key "reservation-entity-id";
 *                 leaf reservation-entity-id {
 *                     type string;
 *                 }
 *                 leaf reservation-entity-type {
 *                     type string;
 *                 }
 *                 leaf status {
 *                     type string;
 *                 }
 *                 list reservation-target-list {
 *                     key "reservation-target-id";
 *                     leaf reservation-target-id {
 *                         type string;
 *                     }
 *                     leaf status {
 *                         type string;
 *                     }
 *                     list resource-list {
 *                         leaf resource-name {
 *                             type string;
 *                         }
 *                         leaf endpoint-position {
 *                             type string;
 *                         }
 *                         leaf status {
 *                             type string;
 *                         }
 *                         leaf allocated {
 *                             type string;
 *                         }
 *                         leaf available {
 *                             type string;
 *                         }
 *                         leaf used {
 *                             type string;
 *                         }
 *                         leaf limit {
 *                             type string;
 *                         }
 *                     }
 *                 }
 *             }
 *         }
 *     }
 * }
 * </pre>
 *
 */
public interface CAPACITYAPIService
    extends
    RpcService
{




    Future<RpcResult<ServiceCapacityReserveOperationOutput>> serviceCapacityReserveOperation(ServiceCapacityReserveOperationInput input);
    
    Future<RpcResult<ServiceCapacityCheckOperationOutput>> serviceCapacityCheckOperation(ServiceCapacityCheckOperationInput input);
    
    Future<RpcResult<ServiceCapacityReleaseOperationOutput>> serviceCapacityReleaseOperation(ServiceCapacityReleaseOperationInput input);
    
    Future<RpcResult<ServiceCapacityGetOperationOutput>> serviceCapacityGetOperation(ServiceCapacityGetOperationInput input);

}

