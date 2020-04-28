package org.onap.sdnc.northbound.gra.springboot.controllers.swagger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.onap.sdnc.northbound.gra.model.GenericResourceApiPreloadModelInformation;
import org.onap.sdnc.northbound.gra.model.GenericResourceApiPreloaddataPreloadData;
import org.onap.sdnc.northbound.gra.model.GenericResourceApiPreloadmodelinformationPreloadList;
import org.onap.sdnc.northbound.gra.springboot.controllers.data.PreloadData;
import org.onap.sdnc.northbound.gra.springboot.controllers.data.PreloadDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Controller
@ComponentScan(basePackages = {"org.onap.sdnc.northbound.gra.springboot.*"})
@EntityScan("org.onap.sdnc.northbound.gra.springboot.*")
public class ConfigApiController implements ConfigApi {
    private static final Logger log = LoggerFactory.getLogger(ConfigApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private PreloadDataRepository preloadDataRepository;

    @Autowired
    public ConfigApiController(ObjectMapper objectMapper, HttpServletRequest request) {
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
    public ResponseEntity<Void> configPreloadInformationDelete() {
        preloadDataRepository.deleteAll();
        return (new ResponseEntity<>(HttpStatus.OK));
    }

    @Override
    public ResponseEntity<GenericResourceApiPreloadModelInformation> configPreloadInformationGet() {
        GenericResourceApiPreloadModelInformation genericResourceApiPreloadModelInformation = new GenericResourceApiPreloadModelInformation();

        preloadDataRepository.findAll().forEach(preloadData -> {
            GenericResourceApiPreloadmodelinformationPreloadList preloadListItem = new GenericResourceApiPreloadmodelinformationPreloadList();

            preloadListItem.setPreloadId(preloadData.getPreloadId());
            preloadListItem.setPreloadType(preloadData.getPreloadType());
            try {
                preloadListItem.setPreloadData(objectMapper.readValue(preloadData.getPreloadData(), GenericResourceApiPreloaddataPreloadData.class));
            } catch (JsonProcessingException e) {
                log.error("Could not convert preload data", e);
            }
            genericResourceApiPreloadModelInformation.addPreloadListItem(preloadListItem);
        });


        return new ResponseEntity<>(genericResourceApiPreloadModelInformation, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> configPreloadInformationPost(@Valid GenericResourceApiPreloadModelInformation graPreloadModelInfo) {

        List<GenericResourceApiPreloadmodelinformationPreloadList> preloadList = graPreloadModelInfo.getPreloadList();

        Iterator<GenericResourceApiPreloadmodelinformationPreloadList> iter = preloadList.iterator();
        while (iter.hasNext()) {
            GenericResourceApiPreloadmodelinformationPreloadList curItem = iter.next();

            try {
                preloadDataRepository.save(new PreloadData(curItem.getPreloadId(), curItem.getPreloadType(), objectMapper.writeValueAsString(curItem.getPreloadData())));
            } catch (JsonProcessingException e) {
                log.error("Cannot convert preload data", e);
            }
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> configPreloadInformationPreloadListPost(@Valid GenericResourceApiPreloadmodelinformationPreloadList preloadListItem) {
        try {
            preloadDataRepository.save(new PreloadData(preloadListItem.getPreloadId(), preloadListItem.getPreloadType(), objectMapper.writeValueAsString(preloadListItem.getPreloadData())));
        } catch (JsonProcessingException e) {
            log.error("Cannot convert preload data", e);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> configPreloadInformationPreloadListPreloadIdPreloadTypeDelete(String preloadId, String preloadType) {
        List<PreloadData> preloadData = preloadDataRepository.findByPreloadIdAndType(preloadId, preloadType);

        if (preloadData != null) {
            Iterator<PreloadData> iter = preloadData.iterator();
            while (iter.hasNext()) {
                preloadDataRepository.delete(iter.next());
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GenericResourceApiPreloadmodelinformationPreloadList> configPreloadInformationPreloadListPreloadIdPreloadTypeGet(String preloadId, String preloadType) {
        List<PreloadData> preloadData = preloadDataRepository.findByPreloadIdAndType(preloadId, preloadType);
        if (preloadData != null) {
            if (!preloadData.isEmpty()) {
                PreloadData preloadDataItem = preloadData.get(0);
                GenericResourceApiPreloadmodelinformationPreloadList preloadDataList = new GenericResourceApiPreloadmodelinformationPreloadList();
                preloadDataList.setPreloadId(preloadDataItem.getPreloadId());
                preloadDataList.setPreloadType(preloadDataItem.getPreloadType());
                try {
                    preloadDataList.setPreloadData(objectMapper.readValue(preloadDataItem.getPreloadData(), GenericResourceApiPreloaddataPreloadData.class));
                } catch (JsonProcessingException e) {
                    log.error("Cannot convert preload data", e);
                }
                return new ResponseEntity<>(preloadDataList, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Void> configPreloadInformationPreloadListPreloadIdPreloadTypePost(String preloadId, String preloadType, @Valid GenericResourceApiPreloadmodelinformationPreloadList preloadListItem) {
        try {
            preloadDataRepository.save(new PreloadData(preloadId, preloadType, objectMapper.writeValueAsString(preloadListItem.getPreloadData())));
        } catch (JsonProcessingException e) {
            log.error("Cannot convert preload data", e);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> configPreloadInformationPreloadListPreloadIdPreloadTypePreloadDataDelete(String preloadId, String preloadType) {
        List<PreloadData> preloadData = preloadDataRepository.findByPreloadIdAndType(preloadId, preloadType);

        if (preloadData != null) {
            Iterator<PreloadData> iter = preloadData.iterator();

            while (iter.hasNext()) {
                preloadDataRepository.delete(iter.next());
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GenericResourceApiPreloaddataPreloadData> configPreloadInformationPreloadListPreloadIdPreloadTypePreloadDataGet(String preloadId, String preloadType) {
        List<PreloadData> preloadData = preloadDataRepository.findByPreloadIdAndType(preloadId, preloadType);
        if (preloadData != null) {
            if (!preloadData.isEmpty()) {
                PreloadData preloadDataItem = preloadData.get(0);
                try {
                    return new ResponseEntity<>(objectMapper.readValue(preloadDataItem.getPreloadData(), GenericResourceApiPreloaddataPreloadData.class), HttpStatus.OK);
                } catch (JsonProcessingException e) {
                    log.error("Cannot convert preload data", e);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Void> configPreloadInformationPreloadListPreloadIdPreloadTypePreloadDataPost(String preloadId, String preloadType, @Valid GenericResourceApiPreloaddataPreloadData preloadData) {
        try {
            preloadDataRepository.save(new PreloadData(preloadId, preloadType, objectMapper.writeValueAsString(preloadData)));
        } catch (JsonProcessingException e) {
            log.error("Cannot convert preload data", e);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
