package org.onap.sdnc.northbound.gra.springboot.controllers.swagger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.Futures;
import org.onap.sdnc.northbound.gra.model.GenericResourceApiPreloadNetworkTopologyOperation;
import org.onap.sdnc.northbound.gra.model.GenericResourceApiPreloadTopologyResponseBody;
import org.onap.sdnc.northbound.gra.model.GenericResourceApiPreloadnetworktopologyoperationInputBodyparam;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-04-21T16:43:20.549-04:00")

@Controller
@ComponentScan(basePackages = {"org.onap.sdnc.northbound.gra.springboot.*"})
@EntityScan("org.onap.sdnc.northbound.gra.springboot.*")
public class OperationsApiController implements OperationsApi {

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public OperationsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @Override
    public Optional<ObjectMapper> getObjectMapper() {
        return Optional.ofNullable(objectMapper);
    }

    @Override
    public Optional<HttpServletRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<GenericResourceApiPreloadNetworkTopologyOperation> operationsPreloadNetworkTopologyOperationPost(@Valid GenericResourceApiPreloadnetworktopologyoperationInputBodyparam graInput) {
        final String svcOperation = "preload-network-topology-operation";

        if (hasInvalidPreloadNetwork(graInput)) {
            log.debug("exiting {} because of null or empty preload-network-topology-information", svcOperation);

            GenericResourceApiPreloadTopologyResponseBody resp = new GenericResourceApiPreloadTopologyResponseBody();

            resp.setResponseCode("403");
            resp.setResponseMessage("invalid input, null or empty preload-network-topology-information");
            resp.setAckFinalIndicator("Y");


            GenericResourceApiPreloadNetworkTopologyOperation retval = new GenericResourceApiPreloadNetworkTopologyOperation();
            retval.setOutput(resp);

            return new ResponseEntity<>(retval, HttpStatus.FORBIDDEN);
        }

        String preloadId = graInput.getInput().getPreloadNetworkTopologyInformation().getNetworkTopologyIdentifierStructure().getNetworkId();


        return null;
    }

    private boolean hasInvalidPreloadNetwork(GenericResourceApiPreloadnetworktopologyoperationInputBodyparam preloadData)
    {
        return ((preloadData == null) ||
                (preloadData.getInput() == null) ||
                (preloadData.getInput().getPreloadNetworkTopologyInformation() == null));
    }
}
